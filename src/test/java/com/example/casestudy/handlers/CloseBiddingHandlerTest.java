package com.example.casestudy.handlers;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.entities.Bid;
import com.example.casestudy.entities.Product;
import com.example.casestudy.entities.User;
import com.example.casestudy.entities.WinningBid;
import com.example.casestudy.repositories.BidRepo;
import com.example.casestudy.repositories.ProductRepo;
import com.example.casestudy.repositories.WinndingBidRepo;
import com.example.casestudy.services.EmailSenderService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CloseBiddingHandlerTest {
    @Mock
    private ProductRepo productRepo;

    @Mock
    private BidRepo bidRepo;

    @Mock
    private WinndingBidRepo winndingBidRepo;

    @Mock
    private EmailSenderService emailSenderService;

    @InjectMocks
    private CloseBiddingHandler closeBiddingHandler;

    @Test
    void test_when_there_are_no_bidders_for_the_product_then_send_notification_to_vendor_and_changeProductStatusToClose() {
        User user = new User();
        user.setEmail("test@email.com");
        user.setName("test suer");
        user.setUserId(1);

        Integer productId = 1;
        Product product = new Product();
        product.setProductId(productId);
        product.setUser(user);
        product.setProductStatus(ProductStatus.ACTIVE);

        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(bidRepo.findByProduct(product)).thenReturn(new ArrayList<>()); // No bidders

        closeBiddingHandler.changeProductStatusToClose(productId);
        verify(productRepo, times(1)).findById(productId);
        verify(productRepo, times(1)).save(product);

        verify(emailSenderService, times(1)).sendEmail(anyString(), anyString(), anyString());

        //Verify that no winning bid is saved
        verify(winndingBidRepo, never()).save(any(WinningBid.class));
    }

    @Test
    void test_when_there_are_multiple_bidders_evaluate_winning_bid_and_send_notification_to_winning_user() {
        Integer productId = 1;

        Product product = new Product();
        product.setProductId(productId);
        product.setProductName("Sample Product");
        User vendor = new User();
        vendor.setEmail("vendor@example.com");
        product.setUser(vendor); // Mocking a user for the product
        product.setProductStatus(ProductStatus.ACTIVE);

        User user2 = new User();
        user2.setUserId(2);
        user2.setEmail("testuser2@email.com");
        user2.setName("testuser2");

        List<Bid> bids = new ArrayList<>();
        Bid bid1 = new Bid();
        bid1.setUser(user2);
        bid1.setBidPrice(100.0);
        bid1.setProduct(product);
        bids.add(bid1);

        User user3 = new User();
        user3.setUserId(3);
        user3.setEmail("testuser3@email.com");
        user3.setName("testuser3");

        Bid bid2 = new Bid();
        bid2.setUser(user3);
        bid2.setBidPrice(120.0);
        bid2.setProduct(product);
        bids.add(bid2);

        when(productRepo.findById(productId)).thenReturn(Optional.of(product));
        when(bidRepo.findByProduct(product)).thenReturn(bids);

        closeBiddingHandler.changeProductStatusToClose(productId);

        verify(productRepo, times(1)).findById(productId);
        verify(productRepo, times(1)).save(product);

        verify(emailSenderService, times(1)).sendEmail(anyString(), anyString(), anyString());
        verify(winndingBidRepo, times(1)).save(any(WinningBid.class));
    }
}
