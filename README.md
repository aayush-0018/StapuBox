# Sports Venue Booking Service

A dockerized backend service built with **Spring Boot** and **MySQL** to manage sports venues, time slots, availability, and bookings with strict conflict prevention.

This project simulates a real-world sports turf / ground booking system and follows clean backend architecture principles.

---

## 🚀 Tech Stack

- Java 17
- Spring Boot
- Spring Data JPA (Hibernate)
- MySQL
- Docker & Docker Compose
- REST APIs (no UI)

---

## 🧩 Core Features

### 1. Sport Management (External Source Driven)
- Sports are **not hardcoded**
- Sports are fetched from the public API:
- Sports are synced into the database on application startup
- Each sport is stored with:
- `sport_id` (external ID)
- `sport_code`
- `sport_name`

This ensures the system always remains aligned with the external source.

---

### 2. Venue Management
- Create sports venues linked to a valid sport
- List all venues with their sport details

Each venue belongs to **exactly one sport**.

---

### 3. Slot Management (Overlap Prevention)
- Slots are created per venue
- Each slot has:
- `startTime`
- `endTime`
- Slot creation **rejects overlapping time ranges** for the same venue

Overlap logic:
- A slot overlaps if:

This ensures **no two slots clash for the same venue**.

---

### 4. Booking System (Concurrency Safe)
- One booking per slot
- Uses **pessimistic locking** at database level
- Prevents double booking even under concurrent requests
- Booking states:
- `BOOKED`
- `CANCELLED`
- Cancelled bookings free the slot immediately

---

### 5. Availability Search
- Fetch available venues for:
- a given sport
- a given time range
- A venue is available if:
- It has at least one slot in the time range
- No **BOOKED** slot overlaps the requested time

---

## 🔐 Data Integrity & Safety

- Database-level unique constraints
- Indexed foreign keys
- Pessimistic locks for booking safety
- Transactional boundaries for booking and cancellation

---

## 📡 REST API Endpoints

### Venue APIs
- `POST /venues` – Create a venue
- `GET /venues` – List all venues

### Slot APIs
- `POST /venues/{venueId}/slots` – Create a slot (no overlaps allowed)

### Availability API
- `POST /venues/available` – Fetch available venues by sport & time range

### Booking APIs
- `POST /bookings` – Book a slot
- `PUT /bookings/{id}/cancel` – Cancel a booking

---

## 🐳 Docker Setup

The application is fully dockerized.

### Services:
- Spring Boot Application
- MySQL Database

### Start Everything
```bash
docker-compose up --build

