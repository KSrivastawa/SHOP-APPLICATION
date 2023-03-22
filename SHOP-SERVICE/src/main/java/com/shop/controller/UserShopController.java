package com.shop.controller;

import com.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/shops")
public class UserShopController {

    @Autowired
    private RestTemplate restTemplate;

    private ShopService shopService;

    @Autowired
    public UserShopController(ShopService shopService) {
        this.shopService = shopService;
    }





}
