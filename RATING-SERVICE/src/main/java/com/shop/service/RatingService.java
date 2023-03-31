package com.shop.service;

import com.shop.entities.Ratings;
import com.shop.exception.RatingException;

import java.util.List;

public interface RatingService {

    public String addRating(Ratings ratings, String user_id, String shop_id) throws RatingException;
    public String deleteRating(String user_id, String shop_id, String ratingId) throws RatingException;
    public List<Ratings> getAllRatings(String shopName) throws RatingException;


}
