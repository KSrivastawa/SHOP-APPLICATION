package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RatingByShopDto {

    private String ratingId;
    private Integer ratingNumber;
    private String userId;
    private String shopId;
    private String review;


}
