package com.shop.dto;

import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ShopDto {

    private String shopId;
    private String shopName;
    private String shop_owner_first_name;
    private String shop_owner_last_name;
    private String shopGstNumber;
    private String addressLine1;
    private String shopCityLocation;
    private String shopType;
    private String shopMobile;
    private String shopOwnerAadharNumber;

    private List<ShopRating> shopRating = new ArrayList<>();

    private List<ShopProduct> shopProduct = new ArrayList<>();

}
