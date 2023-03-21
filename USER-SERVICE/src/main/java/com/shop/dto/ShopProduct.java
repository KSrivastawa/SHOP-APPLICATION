package com.shop.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ShopProduct {

    private String productId;
    private String productName;
    private String productImage;
    private Integer productQuantity;
    private String productType;
    private Integer productPrice;

}
