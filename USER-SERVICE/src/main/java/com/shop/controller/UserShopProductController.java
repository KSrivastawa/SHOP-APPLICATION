package com.shop.controller;

import com.shop.config.JwtTokenValidatorFilter;
import com.shop.dto.ShopDto;
import com.shop.dto.ShopProduct;
import com.shop.entity.Users;
import com.shop.exception.UserException;
import com.shop.repository.UserRepo;
import com.shop.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class UserShopProductController {

    @Autowired
    private JwtTokenValidatorFilter jwtTokenValidatorFilter;

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private UserRepo userRepo;

    @PostMapping("/shop/product/add/{shopId}")
    public ResponseEntity<String> addProductByUserHandler(@RequestBody ShopProduct shopProductDto, @PathVariable String shopId ) throws UserException {

        shopProductDto.setProductId(UUID.randomUUID().toString());
        shopProductDto.setProductType(shopProductDto.getProductType().toUpperCase());

        Users signedUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();

        String url = "http://SHOP-SERVICE/shops/get/"+shopId;
        ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
        if(shopDto != null){
            if(signedUser.getRole().equals("ROLE_SHOP_OWNER") && signedUser.getShopGstNumber().equals(shopDto.getShopGstNumber()) && shopDto.getShopId().equals(shopId)){
                String url1 = "http://PRODUCT-SERVICE/products/addProduct/"+shopId;
                ResponseEntity<String> response = restTemplate.postForEntity(url1, shopProductDto, String.class);

                return response;
            }
        }

        return new ResponseEntity<String>("Product addition failed", HttpStatus.BAD_REQUEST);
    }

    @PutMapping("/shop/product/update/{shopId}/{productId}")
    public ResponseEntity<?> updateProductByUserHandler(@PathVariable String productId, @RequestBody ShopProduct shopProductDto, @PathVariable String shopId ) throws UserException {

        Users signedUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();

        String url = "http://SHOP-SERVICE/shops/get/" + shopId;
        ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
        if (shopDto != null) {
            if (signedUser.getRole().equals("ROLE_SHOP_OWNER") && signedUser.getShopGstNumber().equals(shopDto.getShopGstNumber()) && shopDto.getShopId().equals(shopId)) {

                String pUrl = "http://PRODUCT-SERVICE/products/getProductByShopId/" + shopId;
                List<ShopProduct> shopProductList = Arrays.asList(restTemplate.getForObject(pUrl, ShopProduct[].class));

                for (ShopProduct shopProduct : shopProductList) {
                    if (shopProduct.getProductId().equals(productId)) {
                        shopProductDto.setProductId(productId);
                        shopProductDto.setProductType(shopProductDto.getProductType().toUpperCase());
                        String url1 = "http://PRODUCT-SERVICE/products/updateProduct/" + productId + "/" + shopId;
                        restTemplate.put(url1, shopProductDto, String.class);

                        return new ResponseEntity<String>("Product updated successfully", HttpStatus.OK);
                    }
                }

            }
        }

        return new ResponseEntity<String>("Product update failed", HttpStatus.BAD_REQUEST);
    }

    @DeleteMapping("/shop/product/delete/{shopId}/{productId}")
    public ResponseEntity<?> deleteProductByUserHandler(@PathVariable String productId, @PathVariable String shopId ) throws UserException {

        Users signedUser = userRepo.findByUserEmail(jwtTokenValidatorFilter.userName()).get();

        String url = "http://SHOP-SERVICE/shops/get/" + shopId;
        ShopDto shopDto = restTemplate.getForObject(url, ShopDto.class);
        if (shopDto != null) {
            if (signedUser.getRole().equals("ROLE_SHOP_OWNER") && signedUser.getShopGstNumber().equals(shopDto.getShopGstNumber()) && shopDto.getShopId().equals(shopId)) {

                String pUrl = "http://PRODUCT-SERVICE/products/getProductByShopId/" + shopId;
                List<ShopProduct> shopProductList = Arrays.asList(restTemplate.getForObject(pUrl, ShopProduct[].class));

                for (ShopProduct shopProduct : shopProductList) {
                    if (shopProduct.getProductId().equals(productId)) {
                        String url1 = "http://PRODUCT-SERVICE/products/deleteProduct/" + productId + "/" + shopId;
                        restTemplate.delete(url1, String.class);

                        return new ResponseEntity<String>("Product deleted successfully", HttpStatus.OK);
                    }
                }

            }
        }

        return new ResponseEntity<String>("Product deletion failed", HttpStatus.BAD_REQUEST);
    }



}
