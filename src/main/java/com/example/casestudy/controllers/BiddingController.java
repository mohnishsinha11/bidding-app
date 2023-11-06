package com.example.casestudy.controllers;

import com.example.casestudy.payloads.BidDto;
import com.example.casestudy.payloads.ProductDto;
import com.example.casestudy.services.BiddingService;
import com.example.casestudy.services.ProductService;
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
                                                    @PathVariable Integer userId, @PathVariable Integer productId){
        BidDto createdBidDto = biddingService.createBid(bidDto, userId, productId);
        return new ResponseEntity<>(createdBidDto, HttpStatus.CREATED);
    }

    //get all
    @GetMapping("/bids")
    public ResponseEntity<List<BidDto>> getAllBids(){
        List<BidDto> bidDtoList = biddingService.getAllBids();
        return new ResponseEntity<>(bidDtoList, HttpStatus.OK);
    }

//    @GetMapping("/bids/winningbids")
//    public ResponseEntity<List<Wi>> getAllProductsByCategory(@PathVariable Integer categoryId){
//        List<ProductDto> productDtoList = productService.getAllProductsByCategoryId(categoryId);
//        return new ResponseEntity<>(productDtoList, HttpStatus.OK);
//    }
}
