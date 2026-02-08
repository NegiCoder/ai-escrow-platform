package com.escrow.backend.dto;

import lombok.Data;
import java.math.BigInteger;

@Data
public class CreateDealRequest {

    private String seller;       // Ethereum address
    private String token;        // ERC20 token address
    private BigInteger amountWei;
    private BigInteger expiresAt;
}
