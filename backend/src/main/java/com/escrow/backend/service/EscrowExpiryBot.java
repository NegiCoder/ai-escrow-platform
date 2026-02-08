package com.escrow.backend.service;

import com.escrow.backend.dto.DealDTO;
import com.escrow.backend.dto.DealState;
import com.escrow.backend.dto.Role;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.math.BigInteger;
import java.time.Instant;

@Slf4j
@Component
@RequiredArgsConstructor
public class EscrowExpiryBot {

    private final EscrowService escrowService;

    // ⏱ runs every 60 seconds
    @Scheduled(fixedDelay = 60_000)
    public void autoRefundExpiredDeals() {

        try {
            // ⚠️ replace with DB later (for now you can loop known IDs)
            BigInteger maxDealId = escrowService.getMaxDealId();

            long now = Instant.now().getEpochSecond();

            for (BigInteger id = BigInteger.ONE;
                 id.compareTo(maxDealId) <= 0;
                 id = id.add(BigInteger.ONE)) {

                DealDTO deal = escrowService.getDeal(id);

                if (
                    DealState.DEPOSITED.name().equals(deal.getState()) &&
                    deal.getExpiresAt().longValue() <= now
                ) {
                    log.warn("⏰ Auto-refunding expired deal {}", id);
                    escrowService.refund(Role.BUYER, id);
                }
            }

        } catch (Exception e) {
            log.error("Expiry bot error", e);
        }
    }
}
