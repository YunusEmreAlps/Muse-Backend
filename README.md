# Muse Backend

Welcome to the **Muse** Banking Application project! This application is designed to guide users through their financial journey with inspiration, wisdom, and harmony. Our goal is to provide a seamless and insightful banking experience that helps users manage their finances efficiently and effectively.

## Table of Contents

- [Muse Backend](#muse-backend)
  - [Table of Contents](#table-of-contents)
  - [Key Features](#key-features)
    - [1. Financial Guidance](#1-financial-guidance)
    - [2. User-Friendly Interface](#2-user-friendly-interface)
    - [3. Insightful Analytics](#3-insightful-analytics)
    - [4. Secure and Reliable](#4-secure-and-reliable)
    - [5. Authentication & Security](#5-authentication--security)
    - [6. Developer Tools](#6-developer-tools)
  - [Name Explanation](#name-explanation)
  - [Development Guide](#development-guide)
    - [1. Prerequisites](#1-prerequisites)
    - [2. Installation](#2-installation)
      - [Manual PostgreSql Installation](#manual-postgresql-installation)
      - [Automatic PostgreSql Installation](#automatic-postgresql-installation)
    - [3. Project Setup](#3-project-setup)
  - [Architecture](#architecture)
  - [Development Workflow](#development-workflow)
  - [Postman Collection](#postman-collection)
  - [Swagger](#swagger)
  - [Code of Conduct](#code-of-conduct)
  - [Security Policy](#security-policy)
  - [Contact Us](#contact-us)

## Key Features

### 1. **Financial Guidance**

- **Personalized Recommendations:** Receive tailored advice based on your financial behavior and goals.
- **Educational Resources:** Access articles, tips, and tutorials to enhance your financial literacy.

### 2. **User-Friendly Interface**

- **Intuitive Dashboard:** Easily navigate through your financial information with a clean and simple interface.
- **Easy Transactions:** Perform transactions quickly and securely with a user-friendly design.

### 3. **Insightful Analytics**

- **Spending Analysis:** Understand your spending patterns with detailed reports and visualizations.
- **Goal Tracking:** Set financial goals and track your progress with clear metrics.

### 4. **Secure and Reliable**

- **Robust Security Measures:** Protect your financial data with advanced encryption and security protocols.
- **Reliable Performance:** Enjoy a smooth and dependable banking experience with minimal downtime.

### 5. **Authentication & Security**

- **JWT Authentication**: Implement secure user login and registration flows using JSON Web Tokens (JWT).
- **Spring Boot Rest API**: Leverage Spring Boot with Spring Security to build a secure and efficient RESTful API.
- **Spring Security Configuration**: Configure Spring Security to integrate seamlessly with JWT for authentication and authorization.
- **Data Models & Associations**: Define and manage data models and associations effectively for secure authentication and authorization.
- **Database Interaction**: Utilize Spring Data JPA for smooth and efficient interaction with PostgreSQL.

### 6. **Developer Tools**

- **API Documentation**: Explore and interact with the API through Swagger UI.
- **Health Check & Metrics**: Monitor the application’s health and performance with Spring Boot Actuator
- **Logging & Exception Handling**: Implement logging and exception handling to ensure smooth operation and error tracking.

## Name Explanation

Inspired by the Greek Muses, goddesses of inspiration and wisdom, **Muse** represents our commitment to providing guidance and insight. Just as the Muses inspire creativity and knowledge, Muse aims to be a source of financial inspiration, helping users make informed decisions and achieve their financial goals.

## Development Guide

### 1. Prerequisites

- Java 8 or higher (The project is built with Java 11)
- Maven 3.6.0 or higher
- PostgreSQL
- IDE (VsCode, IntelliJ IDEA, Eclipse, etc.)
- Postman (optional) for testing the API endpoints. (Swagger UI is available at `localhost:8080/`)
- Git

### 2. Installation

- Clone the repository:

```bash
git clone <repository-url>
```

#### Manual PostgreSql Installation

- Create a PostgreSQL with Docker:

```bash
docker run --name muse -e POSTGRES_USER=muse -e POSTGRES_PASSWORD=muse -e POSTGRES_DB=muse -p 5432:5432 -d postgres
```

- If the database not created, you can create it manually:

```bash
docker exec -it muse psql -U muse -d muse
```

```sql
-- First, create the database
CREATE DATABASE muse;
-- Control the database created or not with \l or \list command
\l
```

#### Automatic PostgreSql Installation

```bash
docker-compose up -d
```

### 3. Project Setup

- Install the dependencies:

```bash
mvn clean install
```

- Open the project in your IDE.
- Configure the database connection in `application.properties`.
- Run the application with Maven:

```bash
mvn spring-boot:run
```

- Access the API endpoints using Postman or any other REST client.
- Start developing!
- For more detailed instructions, refer to the [Development Workflow](docs/DEVELOPMENT_WORKFLOW.md) document.

## Architecture

If you're interested in contributing or learning more about the **Muse** codebase please refer to the [architecture document](docs/ARCHITECTURE.md) first for a high level overview of how the application is put together.

## Development Workflow

In the **Muse** project, we follow a structured development workflow to ensure efficient collaboration and code management. This workflow includes the following key components: branching strategy, versioning, and commit message conventions. By following these guidelines, we aim to maintain a clean and organized codebase that is easy to manage and contribute to. For more information, please refer to the [Development Workflow](docs/DEVELOPMENT_WORKFLOW.md) document.

## Postman Collection

The Postman collection for the **Muse** project can be found in the `postman` directory. It includes a set of API requests that can be used to interact with the application. You can import the collection into Postman by following these steps:

- Open Postman
- Click on the "Import" button in the top left corner
- Drag and drop the `muse.postman_collection.json` file into the import window
- The collection will be imported into Postman, and you can start using the API requests to interact with the application

## Swagger

- Swagger UI is available at `localhost:8080/`

## Code of Conduct

Please read the [Code of Conduct](CODE_OF_CONDUCT.md) before contributing to the project.

## Security Policy

Please read the [Security Policy](SECURITY.md) before contributing to the project.

## Contact Us

For any questions or support, please contact us at:

- Linkedin at [Yunus Emre Alpu](https://www.linkedin.com/in/yunus-emre-alpu-5b1496151/)

Thank you for choosing **Muse** as your financial guide!

---
