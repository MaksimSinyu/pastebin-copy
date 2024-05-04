# Pastebin Clone

This repository contains a clone of Pastebin, a web application that allows users to store plain text or code snippets for public viewing. This implementation enhances the standard Pastebin by incorporating modern technologies such as Spring Boot, PostgreSQL, Amazon S3 for storage, and Docker for easy deployment and scalability through load balancing.

## Features

- **Text and Code Snippet Storage**: Users can store text or code snippets.
- **Hash Generation**: Each paste generates a unique hash which can be used to retrieve the stored data.
- **Persistence**: Pastes are persistently stored in a PostgreSQL database and Amazon S3.
- **Scalability**: Application is containerized using Docker, with nginx used as a load balancer to distribute requests across multiple instances.

## Technology Stack

- **Spring Boot**: For creating the application framework.
- **PostgreSQL**: Database for storing metadata and hashes.
- **Amazon S3**: Used for storing the actual text snippets.
- **Docker**: For containerizing the application and ensuring consistency across multiple environments.
- **Nginx**: As a reverse proxy and load balancer to distribute client requests efficiently.

## Local Setup

### Prerequisites

- Docker
- Docker Compose
- JDK 21 (Ensure JDK version compatibility with your Spring Boot version)
- Gradle (Optional: If not using Docker for everything)

### Steps to Setup

1. **Clone the repository**

    ```bash
    git clone https://github.com/MaksimSinyu/pastebin-copy
    cd pastebin-copy
    ```

2. **Build and run the Docker containers**

    This command will start all the necessary services defined in `docker-compose.yml`, including the application servers, load balancer, and the database.

    ```bash
    docker-compose up --build
    ```

### Environment Configuration

Before running the application, ensure you have the appropriate `.env` files set up in the project directory for Docker to use. These should contain your environment-specific configurations such as database URLs, credentials, and Amazon S3 access keys.

## Usage

Once the application is running, navigate to `http://localhost:8080` to access the load balancer, which will redirect you to one of the available application instances. 

- **Create Paste**:
  
  ```bash
  curl -X POST http://localhost:8080/api/v1/paste -H 'Content-Type: text/plain' -d 'Hello, Pastebin!'

- **Get paste by hash**:
  ```bash
  curl http://localhost:8080/api/v1/paste/<paste-hash>
