const { expect } = require("chai");
const { ethers } = require("hardhat");

describe("DatasetEscrow", function () {
  async function deployFixture() {
    const [owner, oracle, buyer, seller] = await ethers.getSigners();

    // Deploy Fake USDT
    const FakeUSDT = await ethers.getContractFactory("FakeUSDT");
    const usdt = await FakeUSDT.deploy();
    await usdt.deployed();

    // Deploy Escrow
    const DatasetEscrow = await ethers.getContractFactory("DatasetEscrow");
    const escrow = await DatasetEscrow.deploy(oracle.address);
    await escrow.deployed();

    // Transfer USDT to buyer
    await usdt.transfer(buyer.address, 10_000 * 10 ** 6);

    return { owner, oracle, buyer, seller, usdt, escrow };
  }

  it("should create deal, deposit USDT, validate and release funds", async function () {
    const { buyer, seller, oracle, usdt, escrow } =
      await deployFixture();

    const amount = 1_000 * 10 ** 6;
    const block = await ethers.provider.getBlock("latest");
    const expiry = block.timestamp + 3600;

    // Buyer creates deal
    const tx = await escrow
      .connect(buyer)
      .createDeal(seller.address, usdt.address, amount, expiry);

    const receipt = await tx.wait();
    const dealId = receipt.events[0].args[0];

    // Buyer approves + deposits USDT
    await usdt.connect(buyer).approve(escrow.address, amount);
    await escrow.connect(buyer).deposit(dealId);

    // Oracle validates
    await escrow.connect(oracle).setValidation(dealId, true);

    // Release funds
    await escrow.connect(buyer).release(dealId);

    const sellerBalance = await usdt.balanceOf(seller.address);
    expect(sellerBalance).to.equal(amount);
  });

  it("should refund buyer if deal expires", async function () {
    const { buyer, seller, usdt, escrow } =
      await deployFixture();

    const amount = 500 * 10 ** 6;
    const block = await ethers.provider.getBlock("latest");
    const expiry = block.timestamp + 10;

    const tx = await escrow
      .connect(buyer)
      .createDeal(seller.address, usdt.address, amount, expiry);

    const receipt = await tx.wait();
    const dealId = receipt.events[0].args[0];

    await usdt.connect(buyer).approve(escrow.address, amount);
    await escrow.connect(buyer).deposit(dealId);

    await ethers.provider.send("evm_increaseTime", [20]);
    await ethers.provider.send("evm_mine", []);

    await escrow.connect(buyer).refund(dealId);

    const buyerBalance = await usdt.balanceOf(buyer.address);
    expect(buyerBalance).to.be.gt(0);
  });

  it("should reject deposit from non-buyer", async function () {
    const { seller, usdt, escrow } =
      await deployFixture();

    const amount = 100 * 10 ** 6;
    const block = await ethers.provider.getBlock("latest");
    const expiry = block.timestamp + 3600;

    const tx = await escrow.createDeal(
      seller.address,
      usdt.address,
      amount,
      expiry
    );

    const receipt = await tx.wait();
    const dealId = receipt.events[0].args[0];

    await expect(
      escrow.connect(seller).deposit(dealId)
    ).to.be.revertedWith("Only buyer");
  });
});
