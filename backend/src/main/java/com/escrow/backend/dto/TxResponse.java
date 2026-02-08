package com.escrow.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class TxResponse {
    private String transactionHash;
}
