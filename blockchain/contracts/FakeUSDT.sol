// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC20/ERC20.sol";

contract FakeUSDT is ERC20 {
    constructor() ERC20("Tether USD", "USDT") {
        // 1,000,000 USDT with 6 decimals (like real USDT)
        _mint(msg.sender, 1_000_000 * 10 ** 6);
    }

    // USDT uses 6 decimals
    function decimals() public pure override returns (uint8) {
        return 6;
    }
}
