package com.shop.controller;

import com.shop.dto.ProductDto;
import com.shop.entitiy.Products;
import com.shop.exception.ProductException;
import com.shop.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {

    private ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping("/addProduct/{shopId}")
    public ResponseEntity<String> addProductHandler(@RequestBody Products product, @PathVariable String shopId) throws ProductException {

        product.setShopId(shopId);
        String response = productService.addProduct(product, shopId);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getAllProducts")
    public ResponseEntity<List<Products>> getAllProductsHandler() throws ProductException {

        List<Products> products = productService.getAllProducts();
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getProductById/{productId}")
    public ResponseEntity<Products> getProductByIdHandler(@PathVariable String productId) throws ProductException {

        Products product = productService.getProductById(productId);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @DeleteMapping("/deleteProduct/{productId}/{shop_id}")
    public ResponseEntity<String> deleteProductHandler(@PathVariable String productId, @PathVariable String shop_id) throws ProductException {

        String response = productService.deleteProduct(productId, shop_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PutMapping("/updateProduct/{productId}/{shop_id}")
    public ResponseEntity<String> updateProductHandler(@PathVariable String productId, @RequestBody Products product, @PathVariable String shop_id) throws ProductException {

        String response = productService.updateProduct(productId, product, shop_id);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @GetMapping("/getProductByShopId/{shopId}")
    public ResponseEntity<List<Products>> getProductByShopIdHandler(@PathVariable String shopId) throws ProductException {

        List<Products> products = productService.getProductByShopId(shopId);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    /*
    @GetMapping("/getByShopId/{shopId}")
    public ResponseEntity<List<ProductDto>> getProductsByShopIdHandler(@PathVariable String shopId) throws ProductException {

        List<Products> products = productService.getProductByShopId(shopId);
        List<ProductDto> productDtos = new ArrayList<>();
        for (Products product: products){
            ProductDto productDto = new ProductDto();

            productDto.setProductId(product.getProductId());
            productDto.setProductName(product.getProductName());
            productDto.setProductPrice(product.getProductPrice());
            productDto.setProductImage(product.getProductImage());
            productDto.setProductQuantity(product.getProductQuantity());
            productDto.setProductType(product.getProductType());

            productDtos.add(productDto);
        }


        return new ResponseEntity<>(productDtos, HttpStatus.OK);
    }

/*
    @GetMapping("/getProductsByType/{productType}")
    public ResponseEntity<List<Products>> getProductsByTypeHandler(@PathVariable String productType) throws ProductException {

        List<Products> products = productService.getProductsByType(productType);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getProductsByName/{productName}")
    public ResponseEntity<List<Products>> getProductsByNameHandler(@PathVariable String productName) throws ProductException {

        List<Products> products = productService.getProductsByName(productName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getProductsByTypeAndName/{productType}/{productName}")
    public ResponseEntity<List<Products>> getProductsByTypeAndNameHandler(@PathVariable String productType, @PathVariable String productName) throws ProductException {

        List<Products> products = productService.getProductsByTypeAndName(productType, productName);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getProductsByTypeAndPrice/{productType}/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Products>> getProductsByTypeAndPriceHandler(@PathVariable String productType, @PathVariable Integer minPrice, @PathVariable Integer maxPrice) throws ProductException {

        List<Products> products = productService.getProductsByTypeAndPrice(productType, minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getProductsByNameAndPrice/{productName}/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Products>> getProductsByNameAndPriceHandler(@PathVariable String productName, @PathVariable Integer minPrice, @PathVariable Integer maxPrice) throws ProductException {

        List<Products> products = productService.getProductsByNameAndPrice(productName, minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }

    @GetMapping("/getProductsByTypeAndNameAndPrice/{productType}/{productName}/{minPrice}/{maxPrice}")
    public ResponseEntity<List<Products>> getProductsByTypeAndNameAndPriceHandler(@PathVariable String productType, @PathVariable String productName, @PathVariable Integer minPrice, @PathVariable Integer maxPrice) throws ProductException {

        List<Products> products = productService.getProductsByTypeAndNameAndPrice(productType, productName, minPrice, maxPrice);
        return new ResponseEntity<>(products, HttpStatus.OK);
    }
*/

}
