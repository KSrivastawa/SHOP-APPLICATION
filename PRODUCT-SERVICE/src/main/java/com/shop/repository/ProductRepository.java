package com.shop.repository;

import com.shop.entitiy.Products;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends JpaRepository<Products, Integer> {

    Optional<Products> findByProductId(String productId);
    //public Products findByProductId(String productId);
    public List<Products> findByProductType(String productType);
    public List<Products> findByProductName(String productName);
    public List<Products> findByShopId(String shopId);
    public List<Products> findByProductTypeAndProductName(String productType, String productName);
    public List<Products> findByProductTypeAndProductPriceBetween(String productType, Integer minPrice, Integer maxPrice);
    public List<Products> findByProductNameAndProductPriceBetween(String productName, Integer minPrice, Integer maxPrice);
    public List<Products> findByProductTypeAndProductNameAndProductPriceBetween(String productType, String productName, Integer minPrice, Integer maxPrice);


}
