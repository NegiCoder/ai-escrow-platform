🚀 AI-Assisted Escrow Platform

An AI-powered escrow system designed to securely manage transactions between buyers and sellers using role-based workflows, secure fund locking, and blockchain integration.
Built with scalability, security, and real-world fintech use cases in mind.

🔥 Key Features

🔐 Role-Based Access Control

Buyer, Seller, and Admin roles

Secure authorization using Spring Security

🤝 Escrow Deal Lifecycle

Deal creation, acceptance, completion, and cancellation

Funds locked until predefined conditions are met

⛓️ Blockchain-Backed Escrow

Smart contract integration for transparent fund holding

Hardhat-based local blockchain setup

🤖 AI-Assisted Risk Analysis (Planned)

Transaction risk scoring

Fraud and dispute prediction using behavioral patterns

⚖️ Dispute Resolution System

Admin-controlled dispute handling

Audit-friendly transaction logs

🧩 Modular & Scalable Architecture

Clean separation of controllers, services, and repositories

Designed for extensibility and microservices readiness

🛠 Tech Stack

Backend

Java

Spring Boot

Spring Security

JPA / Hibernate

Blockchain

Solidity

Hardhat

Local Ethereum Network

Database

MySQL / PostgreSQL (configurable)

Tools

Maven

Git & GitHub

📂 Project Structure
ai-escrow-platform/
│
├── backend/
│   ├── controller/
│   ├── service/
│   ├── repository/
│   ├── dto/
│   └── security/
│
├── blockchain/
│   ├── contracts/
│   ├── scripts/
│   └── hardhat.config.js
│
└── README.md

⚙️ How It Works (High Level)

Buyer creates an escrow deal

Funds are locked via smart contract

Seller fulfills the agreement

Buyer confirms delivery

Funds are released securely

🚧 Future Enhancements

AI-based fraud detection

Dynamic trust scoring for users

Web dashboard (React / Next.js)

Multi-chain support

👤 Author

Anshul Singh Negi
B.Tech CSE | Backend & Blockchain Enthusiast
