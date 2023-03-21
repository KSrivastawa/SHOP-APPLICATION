package com.shop.controller;


import com.shop.dto.ShopDto;
import com.shop.entity.Users;
import com.shop.exception.UserException;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private RestTemplate restTemplate;

    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/welcome")
    public String welcome(){
        return "Welcome to Shop";
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerUserHandler(@RequestBody Users user) throws UserException {

        user.setRole("ROLE_"+user.getRole().toUpperCase());
        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));

        String userRegistered = userService.registerUser(user);

        return new ResponseEntity<String>(userRegistered, HttpStatus.CREATED);
    }

    @GetMapping("/get/{user_id}")
    public ResponseEntity<Users> getUserByIdHandler(@PathVariable String user_id) throws UsernameNotFoundException, UserException {

        Users getUser = userService.getUserById(user_id);

        return new ResponseEntity<Users>(getUser, HttpStatus.OK);
    }

    @GetMapping("/getall")
    public ResponseEntity<List<Users>> getAllUserHandler() throws UserException {

        List<Users> allUser = userService.getAllUser();

        return new ResponseEntity<List<Users>>(allUser, HttpStatus.OK);
    }

    @PutMapping("/update/{user_id}")
    public ResponseEntity<Users> updateUserByIdHandler(@PathVariable String user_id, @RequestBody Users user) throws UserException {

        user.setUserPassword(passwordEncoder.encode(user.getUserPassword()));
        Users updatedUser = userService.updateUserById(user_id, user);

        return new ResponseEntity<Users>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{user_id}")
    public ResponseEntity<String> deleteUserByIdHandler(@PathVariable String user_id) throws UserException {

        Users user = userService.getUserById(user_id);
        if(user.getRole().equals("ROLE_ADMIN")){
            return new ResponseEntity<String>("Admin can't be deleted", HttpStatus.BAD_REQUEST);
        }
        else if(user.getRole().equals("ROLE_SHOP_OWNER")){
            String url = "http://SHOP-SERVICE/shops/getshopg/"+user.getShopGstNumber();
            ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
            if(shopDto == null){
                String userDeleted = userService.deleteUserById(user_id);
                return new ResponseEntity<String>(userDeleted, HttpStatus.OK);
            }
            else{
                return new ResponseEntity<String>("User is associated with a shop", HttpStatus.BAD_REQUEST);
            }
        }else {
            String userDeleted = userService.deleteUserById(user_id);
            return new ResponseEntity<String>(userDeleted, HttpStatus.OK);
        }


    }


}
