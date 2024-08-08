# Wallet Application

This is a Spring Boot-based Wallet application that allows users to register, create wallets, load money into their wallets, and make payments to other wallets. The application is designed with a modular architecture, ensuring scalability and maintainability.

## Table of Contents

- [Features](#features)
- [Getting Started](#getting-started)
- [Configuration](#configuration)
- [Endpoints](#endpoints)
- [Technology Stack](#technology-stack)
- [License](#license)

## Features

- User Registration
- Wallet Creation
- Load Money into Wallet
- Make Payments
- Transaction History Retrieval
- Logging with unique request IDs for better traceability
- Custom Exception Handling

## Getting Started

### Prerequisites

- Java 19
- Maven 3.6+
- H2 Database (in-memory for development purposes)

### Installation

1. Clone the repository:

    ```bash
    git clone https://github.com/yourusername/wallet-application.git
    ```

2. Navigate to the project directory:

    ```bash
    cd wallet-application
    ```

3. Build the project using Maven:

    ```bash
    mvn clean install
    ```

4. Run the application:

    ```bash
    mvn spring-boot:run
    ```

### Configuration

The application uses the following configurations:

- **H2 Database**: An in-memory database for development.
- **Slf4jMDCFilter**: Adds a unique UUID to each request for logging purposes.
- **CustomAuthFilter**: Checks for `payerUsername` and `payerPassword` in the request headers.

### Endpoints

#### User Controller

- **POST /users/register**
    - Registers a new user.
    - **Request Body**: `RegisterUserRequest`
    - **Response**: `ResponseModel<User>`

#### Wallet Controller

- **POST /wallets/load-money**
    - Loads money into the user's wallet.
    - **Headers**: `username`, `password`
    - **Request Body**: `LoadMoneyRequest`
    - **Response**: `ResponseModel<Wallet>`

- **POST /wallets/make-payment**
    - Makes a payment from one wallet to another.
    - **Headers**: `payerUsername`, `payerPassword`
    - **Request Body**: `MakePaymentRequest`
    - **Response**: `ResponseModel<Void>`

#### Transaction History Controller

- **GET /transactions**
    - Retrieves all transaction history records.
    - **Response**: `ResponseModel<List<TransactionHistory>>`

### Technology Stack

- **Spring Boot**: Application framework
- **H2 Database**: In-memory database
- **Maven**: Build tool
- **Slf4j**: Logging
- **Lombok**: Simplify Java code

### License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.
