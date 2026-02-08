package com.escrow.backend.dto;

import lombok.Data;
import java.math.BigInteger;

@Data
public class DealActionRequest {
    private BigInteger dealId;
}
