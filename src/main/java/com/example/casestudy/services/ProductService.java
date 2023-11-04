package com.example.casestudy.services;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.payloads.ProductDto;

import java.util.List;

public interface ProductService {
    ProductDto createProduct(ProductDto productDto, Integer userId, Integer categoryId);
    List<ProductDto> getAllProducts();

    //get all Products by Category
    List<ProductDto> getAllProductsByCategoryId(Integer categoryId);

    ProductDto updateProductStatus(Integer productId, ProductStatus productStatus);
}
