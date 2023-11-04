package com.example.casestudy.exceptions;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class InvalidBidException extends RuntimeException{
    Integer bidId;
    Double basePrice;
    Double bidPrice;

    public InvalidBidException(Integer bidId, Double bidPrice, Double basePrice) {
        super(String.format("Bid with %s is Invalid bid since bid price:%s is less than base price for product:%s", bidId, bidPrice ,basePrice));
        this.bidId = bidId;
        this.basePrice = basePrice;
        this.bidPrice = bidPrice;
    }
}
