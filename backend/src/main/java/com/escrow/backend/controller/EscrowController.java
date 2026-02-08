package com.escrow.backend.controller;

import com.escrow.backend.dto.CreateDealRequest;
import com.escrow.backend.dto.TxResponse;
import com.escrow.backend.dto.DealDTO;
import com.escrow.backend.dto.Role;
import com.escrow.backend.service.EscrowService;
import com.escrow.backend.service.RoleGuard;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigInteger;
import java.util.Map;

@RestController
@RequestMapping("/api/escrow")
@RequiredArgsConstructor
public class EscrowController {

    private final EscrowService escrowService;
    private final RoleGuard roleGuard;

    // 1️⃣ CREATE DEAL — BUYER
    @PostMapping("/create")
    public ResponseEntity<TxResponse> createDeal(
            @RequestHeader("X-ROLE") Role role,
            @RequestBody CreateDealRequest request
    ) throws Exception {

        roleGuard.require(role, Role.BUYER);

        var receipt = escrowService.createDeal(
                role,
                request.getSeller(),
                request.getToken(),
                request.getAmountWei(),
                request.getExpiresAt()
        );

        return ResponseEntity.ok(new TxResponse(receipt.getTransactionHash()));
    }

    // 2️⃣ APPROVE TOKEN — BUYER
    @PostMapping("/approve")
    public ResponseEntity<TxResponse> approve(
            @RequestHeader("X-ROLE") Role role,
            @RequestBody Map<String, String> body
    ) throws Exception {

        roleGuard.require(role, Role.BUYER);

        var receipt = escrowService.approveToken(
                role,
                body.get("token"),
                new BigInteger(body.get("amountWei"))
        );

        return ResponseEntity.ok(new TxResponse(receipt.getTransactionHash()));
    }

    // 3️⃣ DEPOSIT — BUYER
    @PostMapping("/deposit")
    public ResponseEntity<TxResponse> deposit(
            @RequestHeader("X-ROLE") Role role,
            @RequestBody Map<String, BigInteger> body
    ) throws Exception {

        roleGuard.require(role, Role.BUYER);

        var receipt = escrowService.deposit(role, body.get("dealId"));
        return ResponseEntity.ok(new TxResponse(receipt.getTransactionHash()));
    }

    // 4️⃣ VALIDATE — ORACLE
    @PostMapping("/validate/{dealId}")
    public ResponseEntity<TxResponse> validate(
            @RequestHeader("X-ROLE") Role role,
            @PathVariable BigInteger dealId,
            @RequestParam boolean passed
    ) throws Exception {

        roleGuard.require(role, Role.ORACLE);

        var receipt = escrowService.validate(role, dealId, passed);
        return ResponseEntity.ok(new TxResponse(receipt.getTransactionHash()));
    }

    // 5️⃣ RELEASE — BUYER
    @PostMapping("/release")
    public ResponseEntity<TxResponse> release(
            @RequestHeader("X-ROLE") Role role,
            @RequestBody Map<String, BigInteger> body
    ) throws Exception {

        roleGuard.require(role, Role.BUYER);

        var receipt = escrowService.release(role, body.get("dealId"));
        return ResponseEntity.ok(new TxResponse(receipt.getTransactionHash()));
    }

    // 6️⃣ REFUND — BUYER
    @PostMapping("/refund")
    public ResponseEntity<TxResponse> refund(
            @RequestHeader("X-ROLE") Role role,
            @RequestBody Map<String, BigInteger> body
    ) throws Exception {

        roleGuard.require(role, Role.BUYER);

        var receipt = escrowService.refund(role, body.get("dealId"));
        return ResponseEntity.ok(new TxResponse(receipt.getTransactionHash()));
    }

    // 7️⃣ READ DEAL — ANY ROLE
    @GetMapping("/deal/{id}")
    public ResponseEntity<DealDTO> getDeal(@PathVariable BigInteger id) throws Exception {
        return ResponseEntity.ok(escrowService.getDeal(id));
    }
}
