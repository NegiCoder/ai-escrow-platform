// SPDX-License-Identifier: MIT
pragma solidity ^0.8.20;

import "@openzeppelin/contracts/token/ERC20/utils/SafeERC20.sol";
import "@openzeppelin/contracts/utils/ReentrancyGuard.sol";
import "@openzeppelin/contracts/access/Ownable.sol";

contract DatasetEscrow is ReentrancyGuard, Ownable {
    using SafeERC20 for IERC20;

    enum DealState {
        CREATED,
        DEPOSITED,
        VALIDATED,
        RELEASED,
        REFUNDED
    }

    struct Deal {
        address buyer;
        address seller;
        IERC20 token;
        uint256 amount;
        uint256 expiresAt;
        DealState state;
    }

    uint256 public dealCounter;
    mapping(uint256 => Deal) public deals;

    address public oracle;

    /* ───────────── Events ───────────── */
    event DealCreated(
        uint256 indexed dealId,
        address indexed buyer,
        address indexed seller,
        uint256 amount,
        uint256 expiresAt
    );

    event Deposited(uint256 indexed dealId);
    event Validated(uint256 indexed dealId, bool passed);
    event Released(uint256 indexed dealId);
    event Refunded(uint256 indexed dealId);

    /* ───────────── Modifiers ───────────── */
    modifier onlyOracle() {
        require(msg.sender == oracle, "Not oracle");
        _;
    }

    /* ───────────── Constructor ───────────── */
    constructor(address _oracle) Ownable(msg.sender) {
        require(_oracle != address(0), "Oracle required");
        oracle = _oracle;
    }

    /* ───────────── Core Functions ───────────── */

    function createDeal(
        address seller,
        address token,
        uint256 amount,
        uint256 expiresAt
    ) external returns (uint256) {
        require(seller != address(0), "Invalid seller");
        require(expiresAt > block.timestamp, "Invalid expiry");
        require(amount > 0, "Amount zero");

        dealCounter++;

        deals[dealCounter] = Deal({
            buyer: msg.sender,
            seller: seller,
            token: IERC20(token),
            amount: amount,
            expiresAt: expiresAt,
            state: DealState.CREATED
        });

        emit DealCreated(
            dealCounter,
            msg.sender,
            seller,
            amount,
            expiresAt
        );

        return dealCounter;
    }

    function deposit(uint256 dealId) external nonReentrant {
        Deal storage d = deals[dealId];

        require(msg.sender == d.buyer, "Only buyer");
        require(d.state == DealState.CREATED, "Invalid state");

        d.token.safeTransferFrom(msg.sender, address(this), d.amount);

        d.state = DealState.DEPOSITED;
        emit Deposited(dealId);
    }

    /* ───────────── ORACLE ───────────── */

    function setValidation(uint256 dealId, bool passed)
        external
        onlyOracle
    {
        Deal storage d = deals[dealId];
        require(d.state == DealState.DEPOSITED, "Not deposited");

        if (passed) {
            d.state = DealState.VALIDATED;
        } else {
            _refund(dealId);
        }

        emit Validated(dealId, passed);
    }

    function refundByOracle(uint256 dealId)
        external
        onlyOracle
        nonReentrant
    {
        Deal storage d = deals[dealId];
        require(d.state == DealState.DEPOSITED, "Invalid state");

        _refund(dealId);
    }

    /* ───────────── Buyer Actions ───────────── */

    function release(uint256 dealId) external nonReentrant {
        Deal storage d = deals[dealId];

        require(d.state == DealState.VALIDATED, "Not validated");

        d.state = DealState.RELEASED;
        d.token.safeTransfer(d.seller, d.amount);

        emit Released(dealId);
    }

    function refund(uint256 dealId) external nonReentrant {
        Deal storage d = deals[dealId];

        require(block.timestamp >= d.expiresAt, "Not expired");
        require(d.state == DealState.DEPOSITED, "Invalid state");

        _refund(dealId);
    }

    /* ───────────── Internal ───────────── */

    function _refund(uint256 dealId) internal {
        Deal storage d = deals[dealId];

        d.state = DealState.REFUNDED;
        d.token.safeTransfer(d.buyer, d.amount);

        emit Refunded(dealId);
    }

    /* ───────────── Admin ───────────── */

    function updateOracle(address newOracle) external onlyOwner {
        require(newOracle != address(0), "Zero address");
        oracle = newOracle;
    }
}
