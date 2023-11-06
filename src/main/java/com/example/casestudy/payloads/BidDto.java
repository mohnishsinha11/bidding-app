package com.example.casestudy.payloads;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class BidDto {
    private Integer bidId;
    private Double bidPrice;
    private LocalDateTime bidTime;
    private UserResponseDto user;
    private ProductDto product;
}
