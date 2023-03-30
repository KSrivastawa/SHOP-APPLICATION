package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopRating {

    private String ratingId;
    private String ratingShopName;
    private Integer ratingNumber;
    private String shopId;
    private String UserId;
    private String review;

}
