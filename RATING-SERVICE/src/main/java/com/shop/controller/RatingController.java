package com.shop.controller;

import com.shop.dto.RatingByShopDto;
import com.shop.entities.Ratings;
import com.shop.exception.RatingException;
import com.shop.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/ratings")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class RatingController {

    private RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @PostMapping("/addRating/{user_id}/{shop_id}")
    public ResponseEntity<String> addRatingHandler(@RequestBody Ratings rating, @PathVariable String user_id, @PathVariable String shop_id) throws RatingException{

        String response = ratingService.addRating(rating, user_id, shop_id);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @DeleteMapping("/deleteRating/{user_id}/{shop_id}/{ratingId}")
    public ResponseEntity<String> deleteRatingHandler(@PathVariable String user_id,@PathVariable String shop_id,@PathVariable String ratingId) throws RatingException{

        String response = ratingService.deleteRating(user_id, shop_id, ratingId);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getAllRatings/{shopName}")
    public ResponseEntity<List<RatingByShopDto>> getAllRatingsHandler(@PathVariable String shopName) throws RatingException{

        List<Ratings> ratings = ratingService.getAllRatings(shopName);
        List<RatingByShopDto> ratingByShopDtos = new ArrayList<>();

        for(Ratings rating : ratings){
            RatingByShopDto ratingByShopDto = new RatingByShopDto();

            ratingByShopDto.setRatingId(rating.getRatingId());
            ratingByShopDto.setRatingNumber(rating.getRatingNumber());
            ratingByShopDto.setUserId(rating.getUserId());
            ratingByShopDto.setShopId(rating.getShopId());
            ratingByShopDto.setReview(rating.getReview());

            ratingByShopDtos.add(ratingByShopDto);
        }

        return new ResponseEntity<>(ratingByShopDtos, HttpStatus.OK);
    }



}
