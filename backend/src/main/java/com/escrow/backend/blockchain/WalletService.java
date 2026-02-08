
package com.escrow.backend.blockchain;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.web3j.crypto.Credentials;

@Service
@Getter
public class WalletService {

    @Value("${wallets.buyer.private-key}")
    private String buyerPrivateKey;

    @Value("${wallets.seller.private-key}")
    private String sellerPrivateKey;

    @Value("${wallets.oracle.private-key}")
    private String oraclePrivateKey;

    /**
     * Return Credentials for a role: "buyer", "seller", "oracle".
     * Always ensure "0x" prefix.
     */
    public Credentials getCredentials(String role) {
        String pk;
        switch (role) {
            case "seller": pk = sellerPrivateKey; break;
            case "oracle": pk = oraclePrivateKey; break;
            case "buyer":
            default: pk = buyerPrivateKey; break;
        }
        if (pk == null) {
            throw new IllegalStateException("Private key for role " + role + " is not configured");
        }
        if (!pk.startsWith("0x")) pk = "0x" + pk;
        return Credentials.create(pk);
    }

    /** helper to get address for a role (useful for logging / health checks) */
    public String getAddress(String role) {
        return getCredentials(role).getAddress();
    }
}
