package com.example.casestudy.services.impl;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.entities.Category;
import com.example.casestudy.entities.Product;
import com.example.casestudy.entities.User;
import com.example.casestudy.payloads.ProductDto;
import com.example.casestudy.repositories.CategoryRepo;
import com.example.casestudy.repositories.ProductRepo;
import com.example.casestudy.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {

    @InjectMocks
    private ProductServiceImpl productService;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private CategoryRepo categoryRepo;

    @Mock
    private ModelMapper modelMapper;

/*
    @Test
    void test_given_product_details_when_createProduct_method_is_invoked_then_create_product_success() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Test Product");
        Integer userId = 1;
        Integer categoryId = 2;

        User user = new User();
        user.setUserId(userId);
        user.setName("Test User");
        user.setEmail("test@email.com");

        Category category = new Category();
        category.setCategoryId(categoryId);

        Product product = new Product();
        product.setProductId(1);
        product.setProductName(productDto.getProductName());
        product.setUser(user);
        product.setCategory(category);
        product.setProductStatus(ProductStatus.DRAFT);

        when(userRepo.findById(any())).thenReturn(Optional.of(user));
        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        when(modelMapper.map(productDto, Product.class)).thenReturn(product);
        when(productRepo.save(product)).thenReturn(product);
        when(modelMapper.map(product, ProductDto.class)).thenReturn(productDto);
        ProductDto savedProductDto = productService.createProduct(productDto, userId, categoryId);

        assertNotNull(savedProductDto);
        assertEquals(productDto.getProductName(), savedProductDto.getProductName());
        assertEquals(userId, savedProductDto.getUser().getUserId());
        assertEquals(categoryId, savedProductDto.getCategory().getCategoryId());

        verify(userRepo, times(1)).findById(userId);
        verify(categoryRepo, times(1)).findById(categoryId);
        verify(modelMapper, times(1)).map(productDto, Product.class);
        verify(productRepo, times(1)).save(product);
    }
*/
}

