package com.shop.entity;

import com.shop.dto.ShopProduct;
import com.shop.dto.ShopRating;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Setter
@Getter
public class Shops {

    @Id
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

    @Transient
    private List<ShopRating> shopRating = new ArrayList<>();

    @Transient
    private List<ShopProduct> shopProduct = new ArrayList<>();

}
