package com.example.casestudy.services.impl;

import com.example.casestudy.entities.Bid;
import com.example.casestudy.entities.Product;
import com.example.casestudy.entities.User;
import com.example.casestudy.exceptions.InvalidBidException;
import com.example.casestudy.payloads.BidDto;
import com.example.casestudy.repositories.BidRepo;
import com.example.casestudy.repositories.ProductRepo;
import com.example.casestudy.repositories.UserRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BiddingServiceImplTest {
    @Mock
    private BidRepo bidRepo;

    @Mock
    private ProductRepo productRepo;

    @Mock
    private UserRepo userRepo;

    @Mock
    private ModelMapper modelMapper;

    @InjectMocks
    private BiddingServiceImpl biddingService;

    @Test
    void given_userId_and_productId_when_createBid_method_is_invoked_and_bidPrice_greater_than_base_price_then_accept_ValidBid() {
        Integer userId = 1;
        Integer productId = 2;

        BidDto bidDto = new BidDto();
        bidDto.setBidPrice(150.0);

        User user = new User();
        user.setUserId(userId);
        user.setName("TestUser1");
        user.setEmail("testuser1@email.com");

        Product product = new Product();
        product.setProductId(productId);
        product.setBasePrice(100.0);

        Bid bid = new Bid();
        bid.setBidPrice(bidDto.getBidPrice());
        bid.setUser(user);
        bid.setProduct(product);

        when(modelMapper.map(bidDto, Bid.class)).thenReturn(bid);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));
        when(bidRepo.save(bid)).thenReturn(bid);
        when(modelMapper.map(bid,BidDto.class)).thenReturn(bidDto);

        BidDto savedBidDto = biddingService.createBid(bidDto, userId, productId);

        assertNotNull(savedBidDto);
        assertEquals(bidDto.getBidPrice(), savedBidDto.getBidPrice());

        verify(bidRepo, times(1)).save(bid);
    }

    @Test
    void given_user_id_and_product_id_when_createBid_method_is_invoked_and_bid_price_is_less_than__base_price_then_throw_error() {
        Integer userId = 1;
        Integer productId = 2;

        User user = new User();
        user.setUserId(userId);
        user.setName("TestUser1");
        user.setEmail("testuser1@email.com");

        BidDto bidDto = new BidDto();
        bidDto.setBidPrice(90.0);

        Product product = new Product();
        product.setProductId(productId);
        product.setBasePrice(100.0);
        product.setProductName("Product1");

        Bid bid = new Bid();
        bid.setBidPrice(bidDto.getBidPrice());
        bid.setUser(user);
        bid.setProduct(product);

        when(modelMapper.map(bidDto, Bid.class)).thenReturn(bid);
        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(userRepo.findById(userId)).thenReturn(Optional.of(user));

        assertThrows(InvalidBidException.class, () -> {
            biddingService.createBid(bidDto, userId, productId);
        });

        verify(bidRepo, never()).save(any());
    }

}
