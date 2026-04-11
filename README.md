# BMS_V_5 - Apartment Management System

## Overview
A full-stack Apartment Management System for managing residents, owners, notification, payments and role based authentications

## Architecture
- Frontend: Bootstrap + JavaScript
- Backend: Spring Boot (REST API)
- Database: PostgreSQL

## Project Structure
BMS_V_5/
<br>
 ├── backend
 <br>
 ├── frontend

## Features
- User Authentication
- Resident Management
- Owner Management
- Lease Management
- Payment Tracking
- Notification System
- Role-based access control

## Future Improvements
- Email notifications
- Dashboard analytics
- Production deployment



# 🏢 Building Management System (BMS)

A backend system designed to manage buildings, apartments, ownership structures, and lease management with role-based access control.

---

## 🚀 Overview

The Building Management System (BMS) is a backend service that enables administrators to manage:

* Buildings and apartments
* Ownership distribution (percentage-based)
* Lease lifecycle
* Users (Admins, Owners, Residents)
* Authentication and authorization using JWT

This system is designed with real-world constraints and business logic, making it suitable as a foundation for scalable property management platforms.

---

## 🧩 Core Features

### 🏗️ Building Management

* Create and update buildings
* Store detailed location and infrastructure data
* Unique building code and registration enforcement

### 🏠 Apartment Management

* Manage apartments within buildings
* Track rooms, area, parking, etc.
* Enforce uniqueness within a building

### 👥 Ownership Management

* Multiple owners per apartment
* Ownership tracked using percentage
* Time-based ownership (start/end dates)
* Total ownership cannot exceed 100%

### 📄 Lease Management

* Only one active lease per apartment
* Lease requires an active owner
* Resident can be:

  * Existing user
  * Newly created user
  * Owner of the apartment

### 🔐 Authentication & Authorization

* JWT-based stateless authentication
* Role-based access control (RBAC)
* Secure endpoints using Spring Security

---

## 👤 User Roles

| Role        | Description                                   |
| ----------- | --------------------------------------------- |
| SUPER_ADMIN | Full system control, can create admins        |
| ADMIN       | Manages buildings, apartments, owners, leases |
| OWNER       | Owns apartments, can view buildings           |
| RESIDENT    | Lives in apartments                           |
| MEMBER      | Household member                              |
| STAFF       | Operational staff                             |

---

## 🧠 Key Business Rules

### Ownership Rules

* Ownership must be between 0.01% and 100%
* Total active ownership per apartment ≤ 100%
* A user cannot have multiple active ownerships for the same apartment

### Lease Rules

* Only one active lease per apartment
* Apartment must have at least one active owner
* Exactly one resident selection must be provided

### User Rules

* Email, username, and phone must be unique
* Users can have multiple roles

---

## 🏗️ Architecture

```
Client → Controller → Service → Repository → Database
```

### Key Components

* **Controllers** → Handle HTTP requests
* **Services** → Business logic & validations
* **Repositories** → Database interaction (JPA)
* **Security Layer** → JWT filter + Spring Security
* **Exception Handler** → Centralized error handling

---

## 🔐 Authentication Flow

1. User logs in with email & password
2. JWT token is generated
3. Client sends token in `Authorization: Bearer <token>`
4. Token is validated via filter
5. User identity & roles stored in security context
6. Services use current user context for authorization

---

## 🛠️ Tech Stack

* **Java 17**
* **Spring Boot**
* **Spring Security**
* **JWT (JSON Web Tokens)**
* **PostgreSQL**
* **JPA / Hibernate**
* **Lombok**

---

## ⚙️ Setup Instructions

### Prerequisites

* Java 17+
* PostgreSQL
* Gradle or Maven

---

### 1. Clone the repository

```
git clone <your-repo-url>
cd building-management-system
```

---

### 2. Configure environment

Update `application.yml` or `application.properties`:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/bms
spring.datasource.username=your_user
spring.datasource.password=your_password

jwt.secret=your-secret-key
jwt.expiration=86400000
```

---

### 3. Run the application

```
./gradlew bootRun
```

---

### 4. Access API

```
http://localhost:8080/api/v1
```

---

## 🔌 API Overview

### Auth

* `POST /api/v1/auth/login`
* `GET /api/v1/auth/me`

### Super Admin

* `POST /api/v1/superadmin/admins`
* `GET /api/v1/superadmin/admins`

### Admin

* Buildings → `/api/v1/admin/buildings`
* Apartments → `/api/v1/admin/apartments`
* Owners → `/api/v1/admin/owners`
* Leases → `/api/v1/admin/leases`
* Ownership → `/api/v1/admin/apartment-ownerships`

### User

* `GET /api/v1/users/me`
* `PUT /api/v1/users/me`

---

## 📊 Database Design Highlights

* Normalized relational schema
* UUID-based primary keys
* Indexed for performance
* Supports:

  * Ownership history
  * Lease lifecycle
  * Role-based user system

---

## ⚠️ Known Limitations / Future Improvements

* No pagination on list endpoints
* No caching (Redis) yet
* No rate limiting
* No file/image upload support
* No notification system (e.g., lease expiry alerts)

---

## 🚀 Future Enhancements

* Pagination & filtering APIs
* Redis caching layer
* Rate limiting (per user/IP)
* Event-driven architecture (Kafka/RabbitMQ)
* Email/SMS notifications
* Frontend dashboard integration

---

## 📌 Why This Project?

This project was built to practice **real-world backend system design**, focusing on:

* Business rule enforcement
* Clean architecture
* Security best practices
* Scalable data modeling

---

## 👨‍💻 Author

**Hasibul Hayat**

---

## 📄 License

This project is open-source and available under the MIT License.
