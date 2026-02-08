package com.escrow.backend.config;

import com.escrow.backend.blockchain.DatasetEscrow;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.web3j.protocol.Web3j;
import org.web3j.crypto.Credentials;
import org.web3j.tx.gas.ContractGasProvider;

@Configuration
public class ContractConfig {

    private static final String CONTRACT_ADDRESS =
            "0xe7f1725E7734CE288F8367e1Bb143E90bb3F0512";

    @Bean
    public DatasetEscrow datasetEscrow(
            Web3j web3j,
            Credentials credentials,
            ContractGasProvider gasProvider
    ) {
        return DatasetEscrow.load(
                CONTRACT_ADDRESS,
                web3j,
                credentials,
                gasProvider
        );
    }
}



