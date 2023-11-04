package com.example.casestudy.repositories;

import com.example.casestudy.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CategoryRepo extends JpaRepository<Category, Integer> {
}
