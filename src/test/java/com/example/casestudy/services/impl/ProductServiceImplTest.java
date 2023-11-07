package com.example.casestudy.services.impl;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.entities.Category;
import com.example.casestudy.entities.Product;
import com.example.casestudy.entities.User;
import com.example.casestudy.payloads.CategoryDto;
import com.example.casestudy.payloads.ProductDto;
import com.example.casestudy.payloads.UserDto;
import com.example.casestudy.payloads.UserResponseDto;
import com.example.casestudy.repositories.CategoryRepo;
import com.example.casestudy.repositories.ProductRepo;
import com.example.casestudy.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
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

    @Test
    void test_given_product_details_when_createProduct_method_is_invoked_then_create_product_success() {
        ProductDto productDto = new ProductDto();
        productDto.setProductName("Test Product");
        Integer userId = 1;
        Integer categoryId = 2;

        UserResponseDto userDto= new UserResponseDto();
        userDto.setUserId(userId);
        userDto.setEmail("test@email.com");
        userDto.setName("Test User");
        productDto.setUser(userDto);

        User user = new User();
        user.setUserId(userId);
        user.setName("Test User");
        user.setEmail("test@email.com");

        Category category = new Category();
        category.setCategoryId(categoryId);

        CategoryDto categoryDto = new CategoryDto();
        categoryDto.setCategoryId(categoryId);
        productDto.setCategory(categoryDto);

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

    @Test
    void getAllProductsByCategoryIdWhichAreUpForBidding() {
        // Mock data
        Integer categoryId = 1;

        Category category = new Category();
        category.setCategoryId(categoryId);

        List<Product> productList = new ArrayList<>();
        Product product1 = new Product();
        product1.setProductStatus(ProductStatus.ACTIVE);
        productList.add(product1);

        Product product2 = new Product();
        product2.setProductStatus(ProductStatus.DRAFT);
        productList.add(product2);

        List<ProductDto> productDtoList = new ArrayList<>();
        ProductDto productDto = new ProductDto();
        productDtoList.add(productDto);

        when(categoryRepo.findById(categoryId)).thenReturn(Optional.of(category));
        when(productRepo.findByCategory(category)).thenReturn(productList);
        when(modelMapper.map(product1, ProductDto.class)).thenReturn(productDto);

        List<ProductDto> result = productService.getAllProductsByCategoryIdWhichAreUpForBidding(categoryId);

        assertEquals(1, result.size());

        // Verify interactions
        verify(categoryRepo, times(1)).findById(categoryId);
        verify(productRepo, times(1)).findByCategory(category);
        verify(modelMapper, times(1)).map(product1, ProductDto.class);
    }
}

