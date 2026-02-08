package com.escrow.backend.controller;

import com.escrow.backend.blockchain.WalletService;
import com.escrow.backend.blockchain.DatasetEscrow;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.web3j.protocol.Web3j;

import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final WalletService walletService;
    private final Web3j web3j;

    @Value("${escrow.contract.address}")
    private String contractAddress;

    @GetMapping("/wallets")
    public Map<String, String> wallets() {
        return Map.of(
            "buyer", walletService.getAddress("buyer"),
            "seller", walletService.getAddress("seller"),
            "oracle", walletService.getAddress("oracle")
        );
    }

    @GetMapping("/contract/oracle")
    public String contractOracle() throws Exception {
        // load the contract using buyer credentials (only to call view)
        var escrow = DatasetEscrow.load(contractAddress, web3j, walletService.getCredentials("buyer"), null);
        return escrow.oracle().send(); // returns oracle address stored in contract
    }
}
