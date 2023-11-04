package com.example.casestudy.services.impl;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.entities.Category;
import com.example.casestudy.entities.Product;
import com.example.casestudy.entities.User;
import com.example.casestudy.exceptions.ResourceNotFoundException;
import com.example.casestudy.payloads.ProductDto;
import com.example.casestudy.repositories.CategoryRepo;
import com.example.casestudy.repositories.ProductRepo;
import com.example.casestudy.repositories.UserRepo;
import com.example.casestudy.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepo productRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private CategoryRepo categoryRepo;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public ProductDto createProduct(ProductDto productDto, Integer userId, Integer categoryId) {
        User user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("User","User Id", userId));
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "Category Id", categoryId));

        Product product = modelMapper.map(productDto, Product.class);
        product.setProductStatus(ProductStatus.DRAFT);
        product.setUser(user);
        product.setCategory(category);

        Product savedProduct = productRepo.save(product);

        return modelMapper.map(savedProduct, ProductDto.class);
    }

    @Override
    public List<ProductDto> getAllProducts() {
        List<Product> productList = productRepo.findAll();
        List<ProductDto> productDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public List<ProductDto> getAllProductsByCategoryId(Integer categoryId) {
        Category category = categoryRepo.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category","Category Id", categoryId));
        List<Product> productList = productRepo.findByCategory(category);
        List<ProductDto> productDtoList = productList.stream()
                .map(product -> modelMapper.map(product, ProductDto.class)).collect(Collectors.toList());

        return productDtoList;
    }

    @Override
    public ProductDto updateProductStatus(Integer productId, ProductStatus productStatus) {
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Product Id", productId));
        product.setProductStatus(productStatus);
        Product savedProduct = productRepo.save(product);
        return modelMapper.map(savedProduct, ProductDto.class);
    }
}
