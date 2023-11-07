package com.example.casestudy.payloads;

import com.example.casestudy.constants.WinnerStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class WinningBidDto {
    private Integer winningBidId;
    private Double winningPrice;
    private ProductDto product;
    private UserResponseDto user;
    private WinnerStatus winnerStatus;
}
