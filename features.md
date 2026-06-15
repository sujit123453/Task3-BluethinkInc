#All features:
# 1. Kafka (Event Streaming)

### Why we use it

* Asynchronous communication between microservices
* Decouples services
* Improves performance
* Prevents one service from blocking another

### Where it is used

| Event             | Producer            | Consumer                   |
| ----------------- | ------------------- | -------------------------- |
| Customer Created  | Customer Service    | Notification Service       |
| Account Opened    | Account Service     | Notification, Audit        |
| Money Transferred | Transaction Service | Fraud, Notification, Audit |
| Loan Approved     | Loan Service        | Notification               |
| Card Issued       | Card Service        | Notification               |
| OTP Generated     | Authentication      | Notification               |

### Example

```
Transaction Service
        │
Publish Transaction Event
        │
        ▼
       Kafka
   ┌────┼────┐
   ▼    ▼    ▼
Fraud Notification Audit
```

---

# 2. Swagger / OpenAPI

### Why we use it

* API documentation
* Interactive testing
* Helps frontend developers
* Easy integration with third parties

### Used In

Every microservice:

* Authentication
* Customer
* Account
* Transaction
* Payment
* Loan
* Card
* Notification

Example

```
http://localhost:8081/swagger-ui/index.html
```

---

# 3. Logger (SLF4J + Logback)

### Why we use it

* Debugging
* Error tracking
* Monitoring
* Audit trail
* Production troubleshooting

Example

```java
log.info("Customer Created Successfully");

log.warn("Invalid OTP");

log.error("Database Connection Failed");
```

### Used In

Every service.

---

# 4. Global Exception Handler

### Why

Provides standardized error responses.

Example

```
Customer Not Found

↓

{
   "status":404,
   "message":"Customer not found"
}
```

### Used In

* Customer
* Account
* Loan
* Payment
* Transaction

---

# 5. JWT Authentication

### Why

* Stateless authentication
* Secure APIs
* No server session required

### Flow

```
Login
   │
Generate JWT
   │
Client
   │
Authorization: Bearer Token
   │
API Gateway
   │
Validate Token
```

Used by:

* API Gateway
* Authentication Service

---

# 6. OTP Authentication

### Why

Passwordless login and multi-factor security.

```
Phone
   │
Send OTP
   │
SMS
   │
Verify OTP
   │
Generate JWT
```

Used in:

* Authentication Service
* Notification Service

---

# 7. Redis

### Why

* Store OTPs with expiration
* Cache frequently accessed data
* Improve response times

Example

```
OTP

9876543210

↓

123456

TTL = 5 min
```

Used in:

* Authentication
* Payment
* Account

---

# 8. Spring Cloud Gateway

### Why

Single entry point for all clients.

Responsibilities:

* JWT validation
* Routing
* Rate limiting
* Authentication
* Logging
* CORS

```
Client
   │
Gateway
   │
───────────────
│     │      │
▼     ▼      ▼
Customer Account Loan
```

---

# 9. Eureka Service Discovery

### Why

Automatically discovers running services.

Instead of

```
http://localhost:8082
```

use

```
lb://CUSTOMER-SERVICE
```

Benefits

* Load balancing
* Dynamic discovery
* Easy scaling

---

# 10. Config Server (Recommended)

### Why

Centralized configuration management.

```
Git Repository
      │
      ▼
Config Server
      │
──────────────
│     │      │
▼     ▼      ▼
Customer Loan Payment
```

---

# 11. Circuit Breaker (Resilience4j)

### Why

Prevents cascading failures.

Example

```
Transaction
      │
calls
      ▼
Payment Service

Down

↓

Fallback Response
```

Used in

* Payment
* Loan
* Customer
* Account

---

# 12. Rate Limiter

### Why

Prevent abuse.

Example

```
/login

Only 5 requests/minute
```

Useful for

* OTP APIs
* Login APIs
* Payment APIs

---

# 13. Validation

### Why

Reject invalid data before processing.

Example

```java
@NotBlank
@Email
@NotNull
@Pattern
```

Used in every REST API request DTO.

---

# 14. Lombok

### Why

Avoid boilerplate code.

Instead of

```
Getter
Setter
Constructor
```

Use

```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
```

---

# 15. JPA / Hibernate

### Why

ORM framework to map Java objects to database tables.

Example

```java
@Entity
@Table(name="customer")
```

Used by all persistence services.

---

# 16. Docker

### Why

Containerizes every microservice.

```
Customer Service

↓

Docker Image

↓

Docker Container
```

Benefits

* Consistent deployment
* Easy scaling
* Isolation

---

# 17. Kubernetes

### Why

Container orchestration.

Features

* Auto scaling
* Self healing
* Rolling updates
* Load balancing

```
Pod 1
Pod 2
Pod 3

↓

Service

↓

Load Balancer
```

---

# 18. ELK Stack (Elasticsearch + Logstash + Kibana)

### Why

Centralized log management.

```
Customer Logs
Transaction Logs
Payment Logs

↓

Logstash

↓

Elasticsearch

↓

Kibana Dashboard
```

---

# 19. Prometheus + Grafana

### Why

Application monitoring.

Monitors

* CPU
* Memory
* JVM
* API latency
* Error rates

```
Prometheus

↓

Grafana Dashboard
```

---

# 20. OpenFeign Client

### Why

Simplifies service-to-service communication.

Instead of

```java
RestTemplate
```

Use

```java
@FeignClient("ACCOUNT-SERVICE")
```

Example

```
Loan Service

↓

Account Service

↓

Get Balance
```

---

# 21. API Gateway Filters

### Why

Cross-cutting concerns in one place.

Can perform:

* JWT validation
* Logging
* Rate limiting
* Header manipulation
* Request tracing

---

# 22. Audit Logging

Every critical action is stored.

Example

```
User Login

↓

Audit Service

↓

Store IP
Timestamp
User
Action
```

---

# 23. Distributed Tracing (Zipkin / OpenTelemetry)

### Why

Track a request across multiple services.

```
Client

↓

Gateway

↓

Customer

↓

Account

↓

Transaction

↓

Notification
```

A single trace ID follows the request end-to-end.

---

# 24. Design Patterns Used

| Design Pattern              | Why Used                                              | Services                                               |
| --------------------------- | ----------------------------------------------------- | ------------------------------------------------------ |
| **Singleton**               | Single bean instance managed by Spring                | All services (`@Service`, `@Repository`, `@Component`) |
| **Builder**                 | Creates complex DTOs/entities cleanly                 | Customer, Loan, Account                                |
| **Factory**                 | Creates different object types                        | Notification, Payment                                  |
| **Strategy**                | Different payment or authentication methods           | Payment, Authentication                                |
| **Observer (Pub/Sub)**      | Event-driven communication                            | Kafka producers/consumers                              |
| **Proxy**                   | API Gateway and Feign clients act as intermediaries   | Gateway, Feign                                         |
| **Repository**              | Separates persistence logic                           | All database services                                  |
| **DTO Pattern**             | Transfers only required data                          | All REST APIs                                          |
| **Facade**                  | Exposes a simplified interface over complex workflows | API Gateway, orchestration layers                      |
| **Circuit Breaker**         | Prevents cascading failures                           | Payment, Loan, Account                                 |
| **Adapter**                 | Integrates external providers (SMS, payment gateways) | Notification, Payment                                  |
| **Chain of Responsibility** | Sequential request processing                         | Spring Security filters, Gateway filters               |

# Overall Technology Flow

```text
                Client (Web/Mobile)
                        │
                        ▼
               Spring Cloud Gateway
        (JWT, Rate Limit, Logging, Routing)
                        │
                        ▼
                  Eureka Discovery
                        │
      ┌──────────┬──────────┬──────────┐
      ▼          ▼          ▼          ▼
 Authentication Customer   Account  Transaction
      │          │          │          │
      │          │          │          ├──────────────┐
      │          │          │          │              │
      ▼          ▼          ▼          ▼              ▼
     Redis      MySQL      MySQL      MySQL        Kafka
      │                                          ┌──┼──┬───┐
      │                                          ▼  ▼  ▼   ▼
      │                                  Notification Fraud Audit
      │                                          │
      └──────────── SMS/Email Provider ◄──────────┘

                 Monitoring & Operations
      ┌────────────────────────────────────────────┐
      │ Swagger • Prometheus • Grafana • ELK Stack │
      │ Zipkin/OpenTelemetry • Docker • Kubernetes │
      └────────────────────────────────────────────┘
```

