package com.shop.repository;

import com.shop.entities.Ratings;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface RatingRepository extends JpaRepository<Ratings, String> {

    public Ratings findByRatingId(String ratingId);
    public List<Ratings> findByRatingShopName(String shopName);

}
