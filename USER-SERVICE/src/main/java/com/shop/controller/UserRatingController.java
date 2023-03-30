package com.shop.controller;

import com.shop.config.JwtTokenValidatorFilter;
import com.shop.dto.ShopDto;
import com.shop.dto.ShopRating;
import com.shop.entity.Users;
import com.shop.exception.UserException;
import com.shop.repository.UserRepo;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserRatingController {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private JwtTokenValidatorFilter jwtTokenValidatorFilter;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/rating/{user_id}/{shop_id}")
    public ResponseEntity<String> ratingShopHandler(@RequestBody ShopRating shopRating, @PathVariable String user_id, @PathVariable String shop_id) throws UserException {

        String sUrl = "http://SHOP-SERVICE/shops/get/"+shop_id;
        ShopDto shopDto = restTemplate.getForObject(sUrl, ShopDto.class);

        Users user = userRepo.findByUserId(user_id).orElseThrow(() -> new UserException("User not found"));
        if(user != null){
            Users signedInUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).orElseThrow(() -> new UserException("User not authenticated"));
            if(signedInUser != null && (signedInUser.getRole().equals("ROLE_CUSTOMER") || signedInUser.getRole().equals("ROLE_SHOP_OWNER"))){
                String url = "http://RATING-SERVICE/ratings/addRating/"+user_id+"/"+shop_id;
                shopRating.setRatingShopName(shopDto.getShopName());
                String response = restTemplate.postForObject(url, shopRating, String.class);
                return new ResponseEntity<>(response, HttpStatus.OK);
            }
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/rating/{user_id}/{shop_id}/{rating_id}")
    public ResponseEntity<?> deleteRatingHandler(@PathVariable String user_id, @PathVariable String shop_id, @PathVariable String rating_id) throws UserException {

        Users signedInUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).orElseThrow(() -> new UserException("User not authenticated"));
        if(signedInUser != null){
            String url = "http://SHOP-SERVICE/shops/get/"+shop_id;
            ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
            if(shopDto != null){
                String url1 = "http://RATING-SERVICE/ratings/getAllRatings/"+shopDto.getShopName();
                ShopRating[] shopRatings = restTemplate.getForObject(url1, ShopRating[].class);
                if(shopRatings != null){
                    for(ShopRating shopRating : shopRatings){
                        if(shopRating.getRatingId().equals(rating_id) && shopRating.getUserId().equals(user_id) && shopRating.getShopId().equals(shop_id)){
                            String url2 = "http://RATING-SERVICE/ratings/deleteRating/"+user_id+"/"+shop_id+"/"+rating_id;
                            restTemplate.delete(url2);
                            return new ResponseEntity<>("Rating deleted", HttpStatus.OK);
                        }
                    }
                }
            }
        }

        return new ResponseEntity<>("Rating not found", HttpStatus.NOT_FOUND);
    }

    @GetMapping("/rating/{shop_name}")
    public ResponseEntity<String> getAllRatingsHandler(@PathVariable String shop_name) throws UserException {

        Users signedInUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).orElseThrow(() -> new UserException("User not authenticated"));

        if(signedInUser != null ){
            String url = "http://RATING-SERVICE/ratings/getAllRatings/"+shop_name;
            String response = restTemplate.getForObject(url, String.class);
            return new ResponseEntity<>(response, HttpStatus.OK);
        }

        return new ResponseEntity<>("User not found", HttpStatus.NOT_FOUND);
    }


}
