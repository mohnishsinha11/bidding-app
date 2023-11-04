package com.example.casestudy.handlers;

import com.example.casestudy.constants.Constants;
import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.entities.Product;
import com.example.casestudy.exceptions.ResourceNotFoundException;
import com.example.casestudy.repositories.ProductRepo;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class StartBiddingHandler {

    @Autowired
    private ProductRepo productRepo;

    @RabbitListener(queues = Constants.BIDDING_START_QUEUE)
    public void changeProductStatusToActive(Integer productId){
        System.out.println("  ********* inside start bidding handler : " + productId);

        Product product = productRepo.findById(productId).orElseThrow(()->new ResourceNotFoundException("Product", "Product Id", productId));
        product.setProductStatus(ProductStatus.ACTIVE);
        productRepo.save(product);
    }
}
