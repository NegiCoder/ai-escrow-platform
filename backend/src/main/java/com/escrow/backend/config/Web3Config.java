package com.escrow.backend.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;
import org.web3j.tx.gas.DefaultGasProvider;

@Configuration
public class Web3Config {

    // Hardhat local node
    private static final String RPC_URL = "http://127.0.0.1:8545";

    // ⚠️ use a test account private key (Hardhat gives these)
    private static final String PRIVATE_KEY =
            "0xac0974bec39a17e36ba4a6b4d238ff944bacb478cbed5efcae784d7bf4f2ff80";

    @Bean
    public Web3j web3j() {
        return Web3j.build(new HttpService(RPC_URL));
    }

    @Bean
    public Credentials credentials() {
        return Credentials.create(PRIVATE_KEY);
    }

    @Bean
    public ContractGasProvider gasProvider() {
        return new DefaultGasProvider();
    }
}
