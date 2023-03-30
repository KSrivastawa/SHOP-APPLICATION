package com.shop.service;

import com.shop.dto.RatingByShopDto;
import com.shop.entities.Ratings;
import com.shop.exception.RatingException;
import com.shop.repository.RatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class RatingServiceImpl implements RatingService {

    private RatingRepository ratingRepository;

    @Autowired
    public RatingServiceImpl(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    @Override
    public String addRating(Ratings ratings, String user_id, String shop_id) throws RatingException {

        ratings.setRatingId(UUID.randomUUID().toString());
        ratings.setShopId(shop_id);
        ratings.setUserId(user_id);
        if(ratings.getRatingNumber() > 11 || ratings.getRatingNumber() < 1){
            throw new RatingException("Rating number should be value => 1-10");
        }
        ratingRepository.save(ratings);
        return "Rating added successfully";

    }

    @Override
    public String deleteRating(String user_id, String shop_id, String ratingId) throws RatingException {

        Ratings ratings = ratingRepository.findByRatingId(ratingId);

        if(ratings == null){
            throw new RatingException("Rating not found");
        }
        ratingRepository.delete(ratings);
        return "Rating deleted successfully";

    }

    @Override
    public List<Ratings> getAllRatings(String shopName) throws RatingException {

        List<Ratings> ratings = ratingRepository.findByRatingShopName(shopName);
        if(ratings == null){
            throw new RatingException("No ratings found");
        }
        return ratings;

    }


}
