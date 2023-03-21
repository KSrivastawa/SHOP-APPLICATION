package com.shop.controller;

import com.shop.dto.ShopDto;
import com.shop.entity.Users;
import com.shop.exception.UserException;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserShopController {

    @Autowired
    private RestTemplate restTemplate;

    private UserService userService;

    @Autowired
    UserShopController(UserService userService){
        this.userService = userService;
    }

    @GetMapping("/shop/get/{user_id}")
    public ResponseEntity<Users> getUserByIdHandler(@PathVariable String user_id) throws UsernameNotFoundException, UserException {

        Users getUser = userService.getUserById(user_id);

        if(getUser.getShopGstNumber() != null){
            String url = "http://SHOP-SERVICE/shops/getshopg/"+getUser.getShopGstNumber();
            ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
            getUser.setShopDto(shopDto);
        }


        return new ResponseEntity<Users>(getUser, HttpStatus.OK);
    }

    @GetMapping("/shop/getall")
    public ResponseEntity<List<Users>> getAllUserHandler() throws UserException {

        List<Users> allUser = userService.getAllUser();
        for (Users user : allUser) {
            if (user.getShopGstNumber() != null) {
                String url = "http://SHOP-SERVICE/shops/getshopg/" + user.getShopGstNumber();
                ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
                user.setShopDto(shopDto);
            }
        }

        return new ResponseEntity<List<Users>>(allUser, HttpStatus.OK);
    }



}
