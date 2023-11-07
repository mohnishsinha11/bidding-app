package com.example.casestudy.scheduler;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.constants.Topics;
import com.example.casestudy.payloads.ProductDto;
import com.example.casestudy.services.ProductService;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class BiddingScheduler {
    @Autowired
    private ProductService productService;

    @Autowired
    private RabbitTemplate rabbitTemplate;


    @Scheduled(fixedRate = 60000) // Runs every minute
    public void checkBiddingProducts() {
        List<ProductDto> productDtoList = productService.getAllProducts();
        System.out.println(" *********  In scheduler  *************");
        for (ProductDto productDto : productDtoList) {
            Duration duration = Duration.ofSeconds(productDto.getDuration());
            LocalDateTime slotEnd = productDto.getDateTimeSlot().plus(duration);
            LocalDateTime slotStart = productDto.getDateTimeSlot();
            LocalDateTime currentTime = LocalDateTime.now();
            System.out.println("****  Current status for "+ productDto.getProductId() + " is "+ productDto.getProductStatus() +" *****");
            System.out.println(" --- curr time ---  : "+ currentTime);
            System.out.println(" --- slot start ---  : "+ slotStart);
            System.out.println(" --- slot end ---  : "+ slotEnd);

            if (productDto.getProductStatus().equals(ProductStatus.DRAFT) && currentTime.isAfter(productDto.getDateTimeSlot()) && currentTime.isBefore(slotEnd)) {
                // The productDto is in its bidding slot
                // Implement logic to handle the bidding process
                // changing the status of productDto to active
                System.out.println("*************  Change status to active  ****************");
                //productService.updateProductStatus(productDto.getProductId(), ProductStatus.ACTIVE);
                Integer productId = productDto.getProductId();
                rabbitTemplate.convertAndSend("topic_exchange", Topics.START_BIDDING, productId );
            }

            if(productDto.getProductStatus().equals(ProductStatus.ACTIVE) && currentTime.isAfter(slotEnd)){
                // change the status of the product to CLOSED
                rabbitTemplate.convertAndSend("topic_exchange", Topics.CLOSE_BIDDING, productDto.getProductId());
            }
        }
    }
}
