# ♠️ Ace Links ♠️
![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=openjdk&logoColor=white)
![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white)
![Maven](https://img.shields.io/badge/apachemaven-C71A36.svg?style=for-the-badge&logo=apachemaven&logoColor=white)
![Docker](https://img.shields.io/badge/docker-%230db7ed.svg?style=for-the-badge&logo=docker&logoColor=white)
![Postgres](https://img.shields.io/badge/postgres-%23316192.svg?style=for-the-badge&logo=postgresql&logoColor=white)

This is the back-end service for Ace Links, a Linktree-like application. It's a RESTful API built with Spring Boot that handles all the data management, business logic, and persistence for the front-end client.

## 📝 About The Project

The Ace Links API is the server-side component that powers the Ace Links front-end. It's responsible for securely storing and retrieving user profiles and links.

This project was developed to showcase proficiency in back-end development with Java and the Spring ecosystem, demonstrating best practices for API design, data management, and containerization.

## ⚙️ Built With

This API is built with a focus on performance, security, and scalability using industry-standard tools.
- Spring Boot: An opinionated framework for creating stand-alone, production-grade Spring-based Applications.
- Java: A robust, object-oriented programming language.
- Spring Data JPA: For simplifying data access layers and interacting with the database.
- Spring Security: For handling authentication and securing API endpoints using JWTs.
- PostgreSQL 🐘: A powerful, open-source object-relational database system.
- Docker 🐳: For containerizing the database for consistent development and deployment environments.
- Maven: A powerful project management and build automation tool.
- Lombok: A library to reduce boilerplate code for model and data objects.

## 🚀 Getting Started

Follow these instructions to set up and run the project locally using Docker.

### Prerequisites

You will need the following software installed on your machine:
- JDK 24 or later
- Maven
- Docker and Docker Compose

### Installation & Setup

#### Configure the application

Create a docker-compose.yml file in the root of the project following the example:

```
services:
  db:
    image: postgres:latest
    restart: always
    environment:
      - POSTGRES_USER=ace-links
      - POSTGRES_PASSWORD=password
    volumes:
      - db:/var/lib/postgresql/data
    ports:
      - "5432:5432"
volumes:
  db:
    driver: local
```

In src/main/resources/, create a application.properties file following the example.

Ensure the properties match the docker-compose.yml environment variables.

```
# PostgreSQL Database
spring.application.name=ace-links
spring.datasource.url=jdbc:postgresql://localhost:5432/ace-links
spring.datasource.username=ace-links
spring.datasource.password=password

# JWT Secret Key (change this to a long, random string)
api.security.token.secret=${JWT_SECRET:my-secret-key}
```

#### Launch the database container

```
docker-compose up -d
```

The -d flag runs the container in detached mode.

#### Run the Spring Boot application

```
mvn spring-boot:run
```

The API will now be running and connected to the PostgreSQL database inside the Docker container. You can access it at http://localhost:8080.

## 📄 License

Distributed under the MIT License. See LICENSE.txt for more information.

## 📧 Contact

Otávio Fava Souza de Assis - otavio.assis@hotmail.com

Project Link: [https://github.com/otavioassis-git/ace-links-backend](https://github.com/otavioassis-git/ace-links-backend)

LinkedIn: [https://linkedin.com/in/otavio-assis](https://www.linkedin.com/in/otavio-assis/)
