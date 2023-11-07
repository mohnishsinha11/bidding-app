package com.example.casestudy.controllers;

import com.example.casestudy.payloads.BidDto;
import com.example.casestudy.payloads.WinningBidDto;
import com.example.casestudy.services.BiddingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/bidding")
public class BiddingController {
    @Autowired
    BiddingService biddingService;

    //create
    @PostMapping("/user/{userId}/product/{productId}/add-bid")
    public ResponseEntity<BidDto> createProduct(@RequestBody BidDto bidDto,
                                                @PathVariable Integer userId, @PathVariable Integer productId) {
        BidDto createdBidDto = biddingService.createBid(bidDto, userId, productId);
        return new ResponseEntity<>(createdBidDto, HttpStatus.CREATED);
    }

    //get all
    @GetMapping("/bids")
    public ResponseEntity<List<BidDto>> getAllBids() {
        List<BidDto> bidDtoList = biddingService.getAllBids();
        return new ResponseEntity<>(bidDtoList, HttpStatus.OK);
    }

    //get all winning bids
    @GetMapping("/bids/winningbids")
    public ResponseEntity<List<WinningBidDto>> getAllProductsByCategory() {
        List<WinningBidDto> winningBidDtoList = biddingService.getAllWinningBids();
        return new ResponseEntity<>(winningBidDtoList, HttpStatus.OK);
    }
}
