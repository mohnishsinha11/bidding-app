package com.example.casestudy.handlers;

import com.example.casestudy.constants.Constants;
import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.constants.WinnerStatus;
import com.example.casestudy.entities.Bid;
import com.example.casestudy.entities.Product;
import com.example.casestudy.entities.WinningBid;
import com.example.casestudy.exceptions.ResourceNotFoundException;
import com.example.casestudy.repositories.BidRepo;
import com.example.casestudy.repositories.ProductRepo;
import com.example.casestudy.repositories.UserRepo;
import com.example.casestudy.repositories.WinndingBidRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CloseBiddingHandler {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private BidRepo bidRepo;

    @Autowired
    private WinndingBidRepo winndingBidRepo;

    @RabbitListener(queues = Constants.BIDDING_CLOSE_QUEUE)
    public void changeProductStatusToActive(Integer productId){
        System.out.println("  ********* Inside close bidding handler : " + productId);
        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Product Id", productId));
        product.setProductStatus(ProductStatus.CLOSED);
        productRepo.save(product);

        List<Bid> bids = bidRepo.findByProduct(product);
        Bid highestBid = null;
        Double maxPrice = 0.0;
        for(Bid currentBid:bids){
            if(maxPrice < currentBid.getBidPrice()){
                highestBid = currentBid;
                maxPrice = currentBid.getBidPrice();
            }
        }

        WinningBid winningBid = new WinningBid();
        winningBid.setProduct(highestBid.getProduct());
        winningBid.setWinningPrice(highestBid.getBidPrice());
        winningBid.setUser(highestBid.getUser());
        winningBid.setWinnerStatus(WinnerStatus.CONFIRMED);

        winndingBidRepo.save(winningBid);
        System.out.println("  ********* Winning Bid Price: " + highestBid.getBidPrice());
        System.out.println("  ********* Winning Bid User: " + highestBid.getUser().getUserId());

        //send message to winning user
    }
}
