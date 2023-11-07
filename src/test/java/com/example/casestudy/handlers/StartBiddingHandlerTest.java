package com.example.casestudy.handlers;

import com.example.casestudy.constants.ProductStatus;
import com.example.casestudy.entities.Product;
import com.example.casestudy.exceptions.ResourceNotFoundException;
import com.example.casestudy.repositories.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class StartBiddingHandlerTest {
    @Mock
    private ProductRepo productRepo;

    @InjectMocks
    private StartBiddingHandler startBiddingHandler;

    @Test
    void when_Message_Is_Received_from_Start_Bidding_Topic_then_change_Product_Status_To_Active() {
        Integer productId = 1;
        Product product = new Product();
        product.setProductId(productId);
        product.setProductStatus(ProductStatus.DRAFT);

        when(productRepo.findById(productId)).thenReturn(Optional.of(product));

        startBiddingHandler.changeProductStatusToActive(productId);

        verify(productRepo, times(1)).findById(productId);
        verify(productRepo, times(1)).save(product);
        assertEquals(ProductStatus.ACTIVE, product.getProductStatus());
    }

    @Test
    void when_Message_Is_Received_from_start_bidding_topic_And_Product_Id_donot_exists_Then_Throws_ResourceNotFoundException() {
        Integer productId = 1;

        when(productRepo.findById(productId)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> {
            startBiddingHandler.changeProductStatusToActive(productId);
        });

        verify(productRepo, times(1)).findById(productId);
        verify(productRepo, never()).save(any());
    }
}
