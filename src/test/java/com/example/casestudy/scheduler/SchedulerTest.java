package com.example.casestudy.scheduler;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.constants.Topics;
import com.example.casestudy.payloads.ProductDto;
import com.example.casestudy.services.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static com.example.casestudy.constants.Constants.TOPIC_EXCHANGE;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SchedulerTest {
    @Mock
    private ProductService productService;

    @Mock
    private RabbitTemplate rabbitTemplate;

    @InjectMocks
    private BiddingScheduler biddingScheduler;


    @Test
    void checkProductBidding() {
        ProductDto productDto = new ProductDto();
        productDto.setProductId(1);
        productDto.setProductStatus(ProductStatus.DRAFT);
        productDto.setDateTimeSlot(LocalDateTime.now().minusSeconds(30));
        productDto.setDuration(100);

        ProductDto activeProduct = new ProductDto();
        activeProduct.setProductId(2);
        activeProduct.setProductStatus(ProductStatus.ACTIVE);
        activeProduct.setDateTimeSlot(LocalDateTime.now().minusMinutes(2));
        activeProduct.setDuration(60);

        List<ProductDto> productDtoList = new ArrayList<>();
        productDtoList.add(productDto);
        productDtoList.add(activeProduct);

        when(productService.getAllProducts()).thenReturn(productDtoList);

        biddingScheduler.checkBiddingProducts();

        // Verify that the expected RabbitMQ messages were sent
        verify(rabbitTemplate, times(1)).convertAndSend(TOPIC_EXCHANGE, Topics.START_BIDDING, 1);
        verify(rabbitTemplate, times(1)).convertAndSend(TOPIC_EXCHANGE, Topics.CLOSE_BIDDING, 2);
    }
}
