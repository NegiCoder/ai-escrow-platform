package com.escrow.backend.controller;

import com.escrow.backend.ai.AIValidationResult;
import com.escrow.backend.ai.AIValidationService;
import com.escrow.backend.dto.Role;
import com.escrow.backend.service.EscrowService;
import com.escrow.backend.service.RoleGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.escrow.backend.dto.DatasetPayload;

import java.math.BigInteger;

@RestController
@RequestMapping("/api/oracle")
@RequiredArgsConstructor
public class OracleController {

    private final AIValidationService aiValidationService;
    private final EscrowService escrowService;
    private final RoleGuard roleGuard;

    @PostMapping("/validate/{dealId}")
    public ResponseEntity<AIValidationResult> autoValidate(
            @RequestHeader("X-ROLE") Role role,
            @PathVariable BigInteger dealId,
            @RequestBody DatasetPayload payload
    ) throws Exception {
    
        roleGuard.require(role, Role.ORACLE);
    
        AIValidationResult result =
                aiValidationService.validate(
                        payload.getDescription(),
                        payload.getRows()
                );
    
        // Save validation on-chain
        escrowService.validate(role, dealId, result.isPassed());
    
        if (result.isPassed()) {
           
            escrowService.release(Role.BUYER, dealId);
        } else {
        
            escrowService.refundByOracle(role, dealId);
        }
    
        return ResponseEntity.ok(result);
    }
}
    