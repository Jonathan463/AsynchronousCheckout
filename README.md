# AsynchronousCheckout

A Spring Boot case study exploring race condition prevention in high-concurrency order checkout, comparing Pessimistic Locking, Atomic SQL, and Kafka-based async processing — each implemented on a dedicated branch and load tested with k6.

---

## Overview

**AsynchronousCheckout** simulates a high-concurrency e-commerce checkout scenario where 1,000 users simultaneously attempt to purchase the last available item. The project deliberately reproduces the overselling race condition and then progressively solves it using three industry-standard strategies, each isolated on its own Git branch for clear comparison.

This is not a production application — it is a structured learning reference for understanding concurrency problems and the trade-offs between different solutions in real Java backend systems.

---

## The Problem — Race Condition & Overselling

In a naive checkout implementation, multiple threads can simultaneously:
1. Read `productQuantity = 1`
2. Each pass the stock check (`quantity > 0`)
3. Each decrement and save, resulting in negative stock

Without protection, 19 out of 1,000 concurrent users were able to purchase an item that only had 1 unit in stock — a classic **overselling race condition**.

---

## Branch Structure

Each branch represents a standalone implementation. Switch between them to compare approaches:

| Branch | Strategy | Correct | p95 Latency |
|---|---|---|---|
| `feature/no-locking` | No protection — baseline race condition | ❌ Overselling | ~32ms |
| `feature/pessimistic-locking` | `SELECT FOR UPDATE` via `@Lock(PESSIMISTIC_WRITE)` | ✅ | ~715ms* |
| `feature/atomic-sql` | Single atomic `UPDATE ... WHERE quantity > 0` | ✅ | ~30ms |
| `feature/kafka` | Async Kafka pipeline with single-partition sequential consumption | ✅ | ~22ms** |

> \* Includes an artificial `Thread.sleep(500ms)` to amplify and observe the locking behaviour. Without it, p95 would be significantly lower.
>
> \*\* Kafka p95 measures time to publish and acknowledge — actual order processing happens asynchronously in the consumer.

---

## Tech Stack

- **Java 21**
- **Spring Boot 4**
- **Spring Data JPA** — repository layer with custom locking queries
- **PostgreSQL 17** — primary database
- **Apache Kafka** — async event-driven pipeline (Kafka branch)
- **HikariCP** — connection pooling (pool size tuned for concurrency)
- **Docker & Docker Compose** — containerised infrastructure
- **Lombok** — boilerplate reduction
- **k6** — load testing (1,000 virtual users)

---

## Strategy Deep Dives

### 1. No Locking (`feature/no-locking`)
The baseline. All 1,000 requests hit the DB concurrently. Multiple threads read the same stock value before any thread writes back, causing overselling. Fastest response time but incorrect.

### 2. Pessimistic Locking (`feature/pessimistic-locking`)
Uses `@Lock(LockModeType.PESSIMISTIC_WRITE)` on a custom repository query. The database row is locked exclusively for each transaction, forcing all competing requests to queue. Correct — only 1 order succeeds — but serialises all throughput under high load, significantly increasing p95.

### 3. Atomic SQL (`feature/atomic-sql`)
Replaces the SELECT + Java check + UPDATE pattern with a single atomic SQL statement:
```sql
UPDATE product SET productQuantity = productQuantity - 1
WHERE id = :id AND productQuantity > 0
```
The read, check, and write happen as one indivisible database operation. No explicit locking is needed. Correct, fast, and simple — the recommended approach for this use case.

### 4. Kafka (`feature/kafka`)
Decouples the HTTP layer from order processing entirely. The controller publishes each order request to a Kafka topic and returns `202 Accepted` immediately. A single-partition topic with one consumer thread ensures messages are processed **sequentially**, eliminating concurrent DB access without any locking. Best suited for event-driven systems with multiple downstream consumers.

---

## API Endpoints

### Product
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/product/v1` | Create a product |
| `GET` | `/product/v1/{id}` | Get product by ID (check stock) |

### Order
| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/order/v1` | Place an order |
| `GET` | `/order/v1/{id}` | Get order by ID *(Kafka branch only)* |

#### Place Order — Request Body
```json
{
  "productId": 1,
  "numberOfItem": 1
}
```

---

## Running the Application

### Prerequisites
- Docker & Docker Compose installed
- k6 installed (`brew install k6`) for load testing

### Start with Docker Compose
```bash
docker-compose up --build
```

The application starts on `http://localhost:8080`. A product with `productQuantity = 1` is automatically seeded on startup via `ProductSeeder`.

> **Note:** The Kafka branch includes additional Zookeeper and Kafka containers in `docker-compose.yml`. All other branches only require PostgreSQL and the app container.

---

## Load Testing with k6

Once the application is running, fire 1,000 concurrent requests using k6:

```javascript
// k6-script.js
import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 1000,
  duration: '10s',
};

export default function () {
  const payload = JSON.stringify({ productId: 1, numberOfItem: 1 });
  const params = { headers: { 'Content-Type': 'application/json' } };
  http.post('http://localhost:8080/order/v1', payload, params);
}
```

```bash
k6 run k6-script.js
```

After the test, call `GET /product/v1/1` to observe the final `productQuantity`. A negative value indicates overselling occurred. A value of `0` with exactly one order in the database confirms the implementation is correct.

---

## Key Observations

| Factor | Pessimistic Lock | Atomic SQL | Kafka |
|---|---|---|---|
| Correctness | ✅ | ✅ | ✅ |
| Client gets result immediately | ✅ | ✅ | ❌ Must poll |
| Infrastructure complexity | Low | Low | High |
| Code complexity | Low | Low | High |
| Best for | Low-medium concurrency | High concurrency | Event-driven, multi-consumer systems |
