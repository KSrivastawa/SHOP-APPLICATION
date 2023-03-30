package com.shop.entitiy;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Products {

    @Id
    private String productId;
    private String productName;
    private String productImage;
    private Integer productQuantity;
    private String productType;
    private Integer productPrice;
    private String shopId;

}
