package com.shop.controller;

import com.shop.dto.ShopProduct;
import com.shop.dto.ShopRating;
import com.shop.entity.Shops;
import com.shop.exception.ShopException;
import com.shop.service.ShopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/shops")
public class ShopController {

    @Autowired
    private RestTemplate restTemplate;

    private ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @PostMapping("/registershop")
    public ResponseEntity<String> registerShopHandler(@RequestBody Shops shop) throws ShopException{

        shop.setShopType(shop.getShopType().toUpperCase());
        String shopRegistered = shopService.registerShop(shop);

        return new ResponseEntity<>(shopRegistered, HttpStatus.CREATED);
    }

    @GetMapping("/getshops")
    public ResponseEntity<List<Shops>> getAllShopDetailsHandler() throws ShopException{

        List<Shops> shops = shopService.getAllShopDetails();
        for(Shops shop : shops){
            String url = "http://RATING-SERVICE/ratings/getAllRatings/"+shop.getShopName();
            List<ShopRating> list = restTemplate.getForObject(url, ArrayList.class);

           String url2 = "http://PRODUCT-SERVICE/products/getByShopId/"+shop.getShopId();
           List<ShopProduct> list2 = restTemplate.getForObject(url2, ArrayList.class);

            shop.setShopRating(list);
            shop.setShopProduct(list2);
        }

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping("/getshopi/{shop_Id}")
    public ResponseEntity<Shops> getShopDetailsByIdHandler(@PathVariable String shop_Id) throws ShopException{

        Shops shop = shopService.getShopDetailsById(shop_Id);

        String url = "http://RATING-SERVICE/ratings/getAllRatings/"+shop.getShopName();
        List<ShopRating> list = restTemplate.getForObject(url, ArrayList.class);

        String url2 = "http://PRODUCT-SERVICE/products/getByShopId/"+shop.getShopId();
        List<ShopProduct> list2 = restTemplate.getForObject(url2, ArrayList.class);

        shop.setShopRating(list);
        shop.setShopProduct(list2);

        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/get/{shopId}")
    public ResponseEntity<Shops> getShopDetailsByIdHandlers(@PathVariable String shopId) throws ShopException{

        Shops shop = shopService.getShopDetailsById(shopId);

        return new ResponseEntity<>(shop, HttpStatus.OK);
    }


    @PutMapping("/updateshop/{shop_Id}")
    public ResponseEntity<String> updateShopDetailsHandler(@PathVariable String shop_Id, @RequestBody Shops shop) throws ShopException{

        shop.setShopType(shop.getShopType().toUpperCase());
        String shopUpdated = shopService.updateShopDetails(shop_Id, shop);

        return new ResponseEntity<>(shopUpdated, HttpStatus.OK);
    }

    @DeleteMapping("/deleteshop/{shop_Id}")
    public ResponseEntity<String> deleteShopDetailsHandler(@PathVariable String shop_Id) throws ShopException{

        String shopDeleted = shopService.deleteShopDetails(shop_Id);

        return new ResponseEntity<>(shopDeleted, HttpStatus.OK);
    }

    @GetMapping("/getshopsc/{shopCityLocation}")
    public ResponseEntity<List<Shops>> getAllShopDetailsByCityHandler(@PathVariable String shopCityLocation) throws ShopException{

        List<Shops> shops = shopService.getAllShopDetailsByCity(shopCityLocation);

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping("/getshopst/{shopType}")
    public ResponseEntity<List<Shops>> getAllShopDetailsByTypeHandler(@PathVariable String shopType) throws ShopException{

        List<Shops> shops = shopService.getAllShopDetailsByType(shopType);

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }

    @GetMapping("/getshopg/{shopGstNumber}")
    public ResponseEntity<Shops> getShopDetailsByOwnerGstNumberHandler(@PathVariable String shopGstNumber) throws ShopException{

        Shops shop = shopService.getShopDetailsByOwnerGstNumber(shopGstNumber);
        String url = "http://RATING-SERVICE/ratings/getAllRatings/"+shop.getShopName();
        List<ShopRating> list = restTemplate.getForObject(url, ArrayList.class);

        String url2 = "http://PRODUCT-SERVICE/products/getByShopId/"+shop.getShopId();
        List<ShopProduct> list2 = restTemplate.getForObject(url2, ArrayList.class);

        shop.setShopRating(list);
        shop.setShopProduct(list2);

        return new ResponseEntity<>(shop, HttpStatus.OK);
    }

    @GetMapping("/getshopsc/{shopCityLocation}/{shopType}")
    public ResponseEntity<List<Shops>> getAllShopsDetailsByCityAndTypeHandler(@PathVariable String shopCityLocation, @PathVariable String shopType) throws ShopException{

        List<Shops> shops = shopService.getAllShopsDetailsByCityAndType(shopCityLocation, shopType);

        return new ResponseEntity<>(shops, HttpStatus.OK);
    }




}
