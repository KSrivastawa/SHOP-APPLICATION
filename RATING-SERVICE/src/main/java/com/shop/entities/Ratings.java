package com.shop.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Ratings {

    @Id
    private String ratingId;
    private String ratingShopName;
    private Integer ratingNumber;
    private String shopId;
    private String UserId;
    private String review;


}
