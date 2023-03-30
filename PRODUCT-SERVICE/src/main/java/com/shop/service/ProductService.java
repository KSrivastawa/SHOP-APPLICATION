package com.shop.service;

import com.shop.entitiy.Products;
import com.shop.exception.ProductException;

import java.util.List;

public interface ProductService {

    public String addProduct(Products product, String shopId) throws ProductException;
    public List<Products> getAllProducts() throws ProductException;
    public Products getProductById(String productId) throws ProductException;
    public String deleteProduct(String productId, String shop_id) throws ProductException;
    public String updateProduct(String productId, Products product, String shop_id) throws ProductException;
    public List<Products> getProductByShopId(String shopId) throws ProductException;


 /*
    public List<Products> getProductsByType(String productType) throws ProductException;
    public List<Products> getProductsByName(String productName) throws ProductException;
    public List<Products> getProductsByTypeAndName(String productType, String productName) throws ProductException;
    public List<Products> getProductsByTypeAndPrice(String productType, Integer minPrice, Integer maxPrice) throws ProductException;
    public List<Products> getProductsByNameAndPrice(String productName, Integer minPrice, Integer maxPrice) throws ProductException;
    public List<Products> getProductsByTypeAndNameAndPrice(String productType, String productName, Integer minPrice, Integer maxPrice) throws ProductException;
*/

}
