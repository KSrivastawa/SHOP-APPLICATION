package com.shop.service;

import com.shop.entitiy.Products;
import com.shop.exception.ProductException;
import com.shop.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProductServiceImpl implements ProductService{

    private ProductRepository productRepository;

    @Autowired
    public ProductServiceImpl(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String addProduct(Products product, String shopId) throws ProductException {

        Products savedProduct = productRepository.save(product);
        if(savedProduct != null) {
            return "Product added successfully";
        }
        else {
            throw new ProductException("Product not added");
        }

    }

    @Override
    public List<Products> getAllProducts() throws ProductException {

        List<Products> products = productRepository.findAll();
        if(!products.isEmpty()) {
            return products;
        }
        else {
            throw new ProductException("No products found");
        }

    }

    @Override
    public Products getProductById(String productId) throws ProductException {

        Optional<Products> product = productRepository.findByProductId(productId);
        if(product.isPresent()) {
            return product.get();
        }
        else {
            throw new ProductException("Product not found");
        }

    }

    @Override
    public String deleteProduct(String productId, String shop_id) throws ProductException {

        Optional<Products> product = productRepository.findByProductId(productId);
        if(product.isPresent()) {
            productRepository.delete(product.get());
            return "Product deleted successfully";
        }
        else {
            throw new ProductException("Product not found");
        }

    }

    @Override
    public String updateProduct(String productId, Products product, String shop_id) throws ProductException {

        Optional<Products> productToUpdate = productRepository.findByProductId(productId);

        if(productToUpdate.isPresent()) {
            Products updatedProduct = new Products();
            updatedProduct.setProductId(productId);
            updatedProduct.setProductName(product.getProductName());
            updatedProduct.setProductType(product.getProductType().toUpperCase());
            updatedProduct.setProductImage(product.getProductImage());
            updatedProduct.setProductPrice(product.getProductPrice());
            updatedProduct.setProductQuantity(product.getProductQuantity());
            updatedProduct.setShopId(shop_id);

            productRepository.save(updatedProduct);
            return "Product updated successfully";
        }

        throw new ProductException("Product not found: "+productId);


    }

    @Override
    public List<Products> getProductByShopId(String shopId) throws ProductException {

            List<Products> products = productRepository.findByShopId(shopId);
            if(products==null) {
                throw new ProductException("No products found");
            }
            return products;

    }


    /*
    @Override
    public List<Products> getProductsByType(String productType) throws ProductException {

        List<Products> products = productRepository.findByProductType(productType.toUpperCase());
        if(!products.isEmpty()) {
            return products;
        }
        else {
            throw new ProductException("No products found");
        }

    }

    @Override
    public List<Products> getProductsByName(String productName) throws ProductException {

        List<Products> products = productRepository.findByProductName(productName);
        if(!products.isEmpty()) {
            return products;
        }
        else {
            throw new ProductException("No products found");
        }

    }

    @Override
    public List<Products> getProductsByTypeAndName(String productType, String productName) throws ProductException {

        List<Products> products = productRepository.findByProductTypeAndProductName(productType.toUpperCase(), productName);
        if(!products.isEmpty()) {
            return products;
        }
        else {
            throw new ProductException("No products found");
        }

    }

    @Override
    public List<Products> getProductsByTypeAndPrice(String productType, Integer minPrice, Integer maxPrice) throws ProductException {

        List<Products> products = productRepository.findByProductTypeAndProductPriceBetween(productType.toUpperCase(), minPrice, maxPrice);
        if(!products.isEmpty()) {
            return products;
        }
        else {
            throw new ProductException("No products found");
        }

    }

    @Override
    public List<Products> getProductsByNameAndPrice(String productName, Integer minPrice, Integer maxPrice) throws ProductException {

        List<Products> products = productRepository.findByProductNameAndProductPriceBetween(productName, minPrice, maxPrice);
        if(!products.isEmpty()) {
            return products;
        }
        else {
            throw new ProductException("No products found");
        }

    }

    @Override
    public List<Products> getProductsByTypeAndNameAndPrice(String productType, String productName, Integer minPrice, Integer maxPrice) throws ProductException {

        List<Products> products = productRepository.findByProductTypeAndProductNameAndProductPriceBetween(productType.toUpperCase(), productName, minPrice, maxPrice);
        if(!products.isEmpty()) {
            return products;
        }
        else {
            throw new ProductException("No products found");
        }

    }

*/
}
