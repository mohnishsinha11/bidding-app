package com.example.casestudy.services.impl;

import com.example.casestudy.entities.Bid;
import com.example.casestudy.entities.Product;
import com.example.casestudy.entities.User;
import com.example.casestudy.exceptions.InvalidBidException;
import com.example.casestudy.exceptions.ResourceNotFoundException;
import com.example.casestudy.payloads.BidDto;
import com.example.casestudy.repositories.BidRepo;
import com.example.casestudy.repositories.ProductRepo;
import com.example.casestudy.repositories.UserRepo;
import com.example.casestudy.services.BiddingService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BiddingServiceImpl implements BiddingService {

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public BidDto createBid(BidDto bidDto, Integer userId, Integer productId) {
        Bid bid = modelMapper.map(bidDto, Bid.class);
        Product product = productRepo.findById(productId).orElseThrow(
                ()->new ResourceNotFoundException("Product", "Product Id", productId));

        User user = userRepo.findById(userId).orElseThrow(
                ()->new ResourceNotFoundException("User", "User Id", userId));

        bid.setUser(user);
        bid.setProduct(product);

        Double basePrice = product.getBasePrice();
        Double bidPrice = bid.getBidPrice();
        bid.setBidTime(LocalDateTime.now());

        if(bidPrice < basePrice){
            throw new InvalidBidException(bidDto.getBidId(), bidPrice, basePrice);
        }

        Bid savedBid = bidRepo.save(bid);
        return modelMapper.map(savedBid, BidDto.class);

    }

    @Override
    public List<BidDto> getAllBids() {
        List<Bid> bids = bidRepo.findAll();
        List<BidDto> bidDtos = bids.stream().map(
                (bid) -> modelMapper.map(bid, BidDto.class)).collect(Collectors.toList());
        return bidDtos;
    }

    @Override
    public void deleteBid(Integer bidId) {
        Bid bid = bidRepo.findById(bidId).orElseThrow(
                ()->new ResourceNotFoundException("Bid","Bid Id", bidId));
        bidRepo.delete(bid);
    }
}
