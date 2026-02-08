const { ethers } = require("hardhat");

async function main() {
  const [buyer, seller, oracle] = await ethers.getSigners();

  console.log("Buyer   :", buyer.address);
  console.log("Seller  :", seller.address);
  console.log("Oracle  :", oracle.address);

  // Deploy Fake USDT
  const FakeUSDT = await ethers.getContractFactory("FakeUSDT");
  const usdt = await FakeUSDT.deploy();
  await usdt.deployed();

  console.log("FakeUSDT deployed to:", usdt.address);

  // Deploy DatasetEscrow with ORACLE address
  const DatasetEscrow = await ethers.getContractFactory("DatasetEscrow");
  const escrow = await DatasetEscrow.deploy(oracle.address);
  await escrow.deployed();

  console.log("DatasetEscrow deployed to:", escrow.address);
}

main()
  .then(() => process.exit(0))
  .catch((error) => {
    console.error(error);
    process.exit(1);
  });
