package com.example.casestudy.controllers;

import com.example.casestudy.payloads.ProductDto;
import com.example.casestudy.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bidding")
public class ProductController {
    @Autowired
    ProductService productService;

    //create
    @PostMapping("/user/{userId}/category/{categoryId}/product")
    public ResponseEntity<ProductDto> createProduct(@RequestBody ProductDto productDto,
                                                    @PathVariable Integer userId, @PathVariable Integer categoryId) {
        ProductDto createdProductDto = productService.createProduct(productDto, userId, categoryId);
        return new ResponseEntity<>(createdProductDto, HttpStatus.CREATED);
    }

    //get all
    @GetMapping("/products")
    public ResponseEntity<List<ProductDto>> getAllCategories() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/products")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategory(@PathVariable Integer categoryId) {
        List<ProductDto> productDtoList = productService.getAllProductsByCategoryId(categoryId);
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }

    @GetMapping("/category/{categoryId}/products/active")
    public ResponseEntity<List<ProductDto>> getAllProductsByCategoryWhichAreUpForBidding(@PathVariable Integer categoryId) {
        List<ProductDto> productDtoList = productService.getAllProductsByCategoryIdWhichAreUpForBidding(categoryId);
        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
    }
}
