package com.example.casestudy.services;

import com.example.casestudy.entities.Bid;
import com.example.casestudy.payloads.BidDto;

import java.util.List;

public interface BiddingService {
    BidDto createBid(BidDto bidDto, Integer userId, Integer productId);

    List<BidDto> getAllBids();

    void deleteBid(Integer bidId);
}
