# 🚀 Microservices Demo Project

This project demonstrates a basic **microservices architecture** using Spring Boot and Spring Cloud. It features three core services—**Company**, **Job**, and **Review**—with supporting tools such as **Eureka**, **API Gateway**, **Spring Cloud Config Server**, **Resilience4j**, **Actuator**, and **Zipkin** for service discovery, routing, fault tolerance, health monitoring, and distributed tracing.

---

## 🧠 Microservices Overview

### 1. Company Service (`/companies`)
- Manages company data (CRUD)
- Uses **H2 in-memory database**
- Includes **Spring Boot Actuator**

### 2. Job Service (`/jobs`)
- Manages job listings
- Implements **Resilience4j** for circuit breaking
- Uses **H2 in-memory database**
- Includes **Spring Boot Actuator**

### 3. Review Service (`/reviews`)
- Manages reviews for companies
- Computes **average company rating**
- Sends messages via a **message producer** after review creation
- Uses **H2 in-memory database**
- Includes **Spring Boot Actuator**

---

## 🛠️ Supporting Components

| Component               | Description                                 |
|------------------------|---------------------------------------------|
| **Eureka Server**       | Service registry for discovery              |
| **Spring Cloud Config** | Centralized configuration (Git backend)     |
| **API Gateway**         | Routing and filtering requests              |
| **Zipkin**              | Distributed tracing and request monitoring  |
| **Resilience4j**        | Circuit breaker and fault tolerance         |
| **Actuator**            | Health, metrics, and service insights       |

---

## 🧪 API Endpoints

### 📘 Company Service

- `POST /companies` – Add new company
- `GET /companies` – Get all companies
- `GET /companies/{id}` – Get company by ID
- `PUT /companies/{id}` – Update company
- `DELETE /companies/{id}` – Delete company

### 📘 Job Service

- `POST /jobs` – Create job
- `GET /jobs` – Get all jobs
- `GET /jobs/{id}` – Get job by ID
- `PUT /jobs/{id}` – Update job
- `DELETE /jobs/{id}` – Delete job

### 📘 Review Service

- `POST /reviews?companyId=` – Add review to a company
- `GET /reviews?companyId=` – Get reviews for a company
- `GET /reviews/{id}` – Get review by ID
- `PUT /reviews/{id}` – Update review
- `DELETE /reviews/{id}` – Delete review
- `GET /reviews/average-rating?company-id=` – Get company’s average rating

---

## 💾 Database

All services use **H2** in-memory databases for fast and lightweight persistence.

- H2 Console (if enabled): `http://localhost:{port}/h2-console`

---

## 📊 Health & Monitoring

All core services expose Spring Boot **Actuator** endpoints:

- `/actuator/health` – Service health
- `/actuator/info` – App metadata
- `/actuator/metrics` – Metrics and stats

---

## 🔄 Fault Tolerance

- **Resilience4j** is integrated in **Job Service** to enable:
  - Circuit breaking
  - Retry mechanisms
  - Graceful fallback options

---

## 🔍 Distributed Tracing

**Zipkin** is integrated with Spring Cloud Sleuth to trace requests across services.

- Zipkin UI: [http://localhost:9411](http://localhost:9411)

---

## ▶️ Running the Project

1. **Start Config Server**
2. **Start Eureka Server**
3. **Start API Gateway**
4. **Start Company, Job, and Review Services**
5. (Optional) Start Zipkin for tracing

---

## 🚀 Future Enhancements

- Integrate a persistent DB like PostgreSQL or MySQL
- Add security using Spring Security or OAuth2
- Dockerize each service
- Add Kafka/RabbitMQ for asynchronous messaging
- Implement centralized logging with ELK stack

---

## 👨‍💻 Author

**Shaukat**  

