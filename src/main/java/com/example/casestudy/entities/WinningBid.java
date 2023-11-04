package com.example.casestudy.entities;

import com.example.casestudy.constants.WinnerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "winning_bids")
@Getter
@Setter
@NoArgsConstructor
public class WinningBid {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer winningBidId;

    private Double winningPrice;

    @JoinColumn(name = "product_id")
    @OneToOne
    private Product product;

    @JoinColumn(name = "user_id")
    @ManyToOne
    private User user;

    private WinnerStatus winnerStatus;
}
