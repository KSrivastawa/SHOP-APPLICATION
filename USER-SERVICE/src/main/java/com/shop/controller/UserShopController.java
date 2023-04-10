package com.shop.controller;

import com.shop.config.JwtTokenValidatorFilter;
import com.shop.dto.ShopDto;
import com.shop.entity.Users;
import com.shop.exception.UserException;
import com.shop.repository.UserRepo;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.*;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserShopController {

    @Autowired
    private JwtTokenValidatorFilter jwtTokenValidatorFilter;

    @Autowired
    private RestTemplate restTemplate;

    private UserService userService;
    @Autowired
    private UserRepo userRepo;

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

    @PostMapping("/shop/reg")
    public ResponseEntity<String> registerShopByUserHandler(@RequestBody ShopDto shop) throws UserException {

        Users user = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();
        if(user.getRole().equals("ROLE_SHOP_OWNER") && shop.getShopGstNumber() != null && user.getShopGstNumber().equals(shop.getShopGstNumber())){
            String url = "http://SHOP-SERVICE/shops/registershop";
            ResponseEntity<String> response = restTemplate.postForEntity(url, shop, String.class);

            return response;
        }

        return new ResponseEntity<String>("Shop registration failed", HttpStatus.BAD_REQUEST);
    }


    @PutMapping("/shop/update/{shop_id}")
    public ResponseEntity<String> updateShopByUserHandler(@PathVariable String shop_id, @RequestBody ShopDto shop) throws UserException {

        Users user = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();
        if(user.getRole().equals("ROLE_SHOP_OWNER")){
           String url = "http://SHOP-SERVICE/shops/getByGst/"+user.getShopGstNumber();
              ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
              if(shopDto.getShopId().equals(shop_id)){
                  String url2 = "http://SHOP-SERVICE/shops/update/"+shop_id;
                  restTemplate.put(url2, shop, String.class);

                  return new ResponseEntity<String>("Shop updated successfully", HttpStatus.OK);
              }
        }

        return new ResponseEntity<String>("Shop update failed", HttpStatus.BAD_REQUEST);
    }


    @DeleteMapping("/shop/delete/{shop_id}")
    public ResponseEntity<String> deleteShopByUserHandler(@PathVariable String shop_id) throws UserException {

        Users user = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();
        if(user.getRole().equals("ROLE_SHOP_OWNER")){
            String url = "http://SHOP-SERVICE/shops/getByGst/"+user.getShopGstNumber();
            ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
            if(shopDto.getShopId().equals(shop_id)){
                String url2 = "http://SHOP-SERVICE/shops/delete/"+shop_id;
                restTemplate.delete(url2, String.class);

                return new ResponseEntity<String>("Shop deleted successfully", HttpStatus.OK);
            }
        }

        return new ResponseEntity<String>("Shop delete failed", HttpStatus.BAD_REQUEST);
    }


}
