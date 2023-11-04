package com.example.casestudy.entities;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "bids")
@Getter
@Setter
@NoArgsConstructor
public class Bid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Integer bidId;

    Double bidPrice;

    LocalDateTime bidTime;

    @JoinColumn(name = "product_id")
    @ManyToOne
    Product product;

    @JoinColumn(name = "user_id")
    @ManyToOne
    User user;
}
