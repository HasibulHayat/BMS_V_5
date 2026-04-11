# System Design

## Architecture Style

Layered architecture:

Client → Controller → Service → Repository → Database

---

## Components

### Controllers

Handle HTTP requests and responses.

### Services

Contain business logic and validations.

### Repositories

Handle database operations using JPA.

### Database

PostgreSQL with relational schema.

---

## Key Design Decisions

* JWT for stateless authentication
* Separation of UserAuth and UserProfile
* Ownership modeled with percentage
* Validation handled at service layer

