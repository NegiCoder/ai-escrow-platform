package com.escrow.backend.service;

import com.escrow.backend.blockchain.DatasetEscrow;
import com.escrow.backend.blockchain.WalletService;
import com.escrow.backend.dto.DealDTO;
import com.escrow.backend.dto.DealState;
import com.escrow.backend.dto.Role;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.contracts.eip20.generated.ERC20;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.core.methods.response.TransactionReceipt;
import org.web3j.tx.gas.ContractGasProvider;

import java.math.BigInteger;

@Service
@RequiredArgsConstructor
public class EscrowService {

    private final Web3j web3j;
    private final ContractGasProvider gasProvider;
    private final WalletService walletService;

    @Value("${escrow.contract.address}")
    private String contractAddress;

    // 🔹 Load escrow contract with role-based wallet
    private DatasetEscrow load(Role role) {
        return DatasetEscrow.load(
                contractAddress,
                web3j,
                walletService.getCredentials(role.name().toLowerCase()),
                gasProvider);
    }

    // 1️⃣ CREATE DEAL
    public TransactionReceipt createDeal(
            Role role,
            String seller,
            String tokenAddress,
            BigInteger amount,
            BigInteger expiresAt) throws Exception {

        return load(role)
                .createDeal(seller, tokenAddress, amount, expiresAt)
                .send();
    }

    // 2️⃣ APPROVE TOKEN
    public TransactionReceipt approveToken(
            Role role,
            String tokenAddress,
            BigInteger amount) throws Exception {

        ERC20 token = ERC20.load(
                tokenAddress,
                web3j,
                walletService.getCredentials(role.name().toLowerCase()),
                gasProvider);

        return token.approve(contractAddress, amount).send();
    }

    // 3️⃣ DEPOSIT
    public TransactionReceipt deposit(Role role, BigInteger dealId) throws Exception {
        return load(role).deposit(dealId).send();
    }

    // 4️⃣ VALIDATE (ORACLE)
    public TransactionReceipt validate(Role role, BigInteger dealId, boolean passed)
            throws Exception {

        if (role != Role.ORACLE) {
            throw new SecurityException("Only ORACLE can validate");
        }

        return load(role)
                .setValidation(dealId, passed)
                .send();
    }

    // 5️⃣ RELEASE
    public TransactionReceipt release(Role role, BigInteger dealId) throws Exception {
        return load(role).release(dealId).send();
    }

    // 6️⃣ REFUND
    public TransactionReceipt refund(Role role, BigInteger dealId) throws Exception {
        return load(role).refund(dealId).send();
    }

    public TransactionReceipt refundByOracle(
        Role role,
        BigInteger dealId
) throws Exception {

    return load(role)
            .refundByOracle(dealId)
            .send();
}

public BigInteger getMaxDealId() throws Exception {
    return load(Role.BUYER).dealCounter().send();
}


    

    // 7️⃣ READ DEAL (VIEW)
    public DealDTO getDeal(BigInteger dealId) throws Exception {

        var deal = load(Role.BUYER).deals(dealId).send();

        DealState stateEnum = DealState.values()[deal.component6().intValue()];

        return new DealDTO(
                deal.component1(), // buyer
                deal.component2(), // seller
                deal.component3(), // token
                deal.component4(), // amount
                deal.component5(), // expiresAt
                stateEnum.name());
    }
}
