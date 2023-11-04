package com.example.casestudy.repositories;

import com.example.casestudy.entities.Bid;
import com.example.casestudy.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface BidRepo extends JpaRepository<Bid, Integer> {
    List<Bid> findByProduct(Product product);
}
