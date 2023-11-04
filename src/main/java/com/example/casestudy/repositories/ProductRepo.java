package com.example.casestudy.repositories;

import com.example.casestudy.entities.Category;
import com.example.casestudy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Integer> {
    List<Product> findByCategory(Category category);
}
