package com.shop.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopRating {

    @JsonProperty("ratingId")
    private String ratingId;
    @JsonProperty("ratingNumber")
    private Integer ratingNumber;
    @JsonProperty("UserId")
    private String UserId;
    @JsonProperty("review")
    private String review;

}
