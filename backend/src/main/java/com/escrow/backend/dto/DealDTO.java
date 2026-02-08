package com.escrow.backend.dto;

import java.math.BigInteger;



public class DealDTO {

    private final String buyer;
    private final String seller;
    private final String token;
    private final BigInteger amount;
    private final BigInteger expiresAt;
    private final String state;

    public DealDTO(
        String buyer,
        String seller,
        String token,
        BigInteger amount,
        BigInteger expiresAt,
        String state
) {
    this.buyer = buyer;
    this.seller = seller;
    this.token = token;
    this.amount = amount;
    this.expiresAt = expiresAt;
    this.state = state;
}

    public String getBuyer() { return buyer; }
    public String getSeller() { return seller; }
    public String getToken() { return token; }
    public BigInteger getAmount() { return amount; }
    public BigInteger getExpiresAt() { return expiresAt; }
    public String getState() { return state; }
}
