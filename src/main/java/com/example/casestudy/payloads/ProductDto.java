package com.example.casestudy.payloads;

import com.example.casestudy.constants.ProductStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class ProductDto {
    private Integer productId;

    private String productName;

    private Double basePrice;

    private CategoryDto category;

    private UserDto user;

    private LocalDateTime dateTimeSlot;

    private Integer duration;

    private ProductStatus productStatus;
}
