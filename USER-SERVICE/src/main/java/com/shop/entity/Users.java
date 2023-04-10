package com.shop.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.shop.dto.ShopDto;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Users {

    @Id
    private String userId;
    private String userFirstName;
    private String userLastName;
    private String userEmail;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String userPassword;
    private String role;
    private String shopGstNumber;

    @Transient
    private ShopDto shopDto;

}
