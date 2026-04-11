# Authentication Flow

## Login Process

1. User sends email and password
2. System validates credentials
3. JWT token is generated
4. Token is returned to client

---

## Request Flow

1. Client sends request with JWT token
2. Token is validated
3. User ID and roles extracted
4. Security context is populated
5. Request proceeds

---

## Security Features

* Stateless authentication
* Role-based authorization
* Token expiration handling
