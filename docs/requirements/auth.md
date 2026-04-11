# Authentication Requirements

## Overview

Handles user authentication using JWT (JSON Web Tokens).

---

## Login

### Rules

* User must provide valid email and password
* Email must exist in the system
* Password must match stored hash
* User must be:

  * Active
  * Not locked

### Output

* JWT access token
* Expiration timestamp

---

## Token Behavior

* Token contains:

  * User ID
  * Roles
* Token must be included in header:

```
Authorization: Bearer <token>
```

---

## Validation

* Invalid or expired token → Unauthorized (401)
* Missing token → Request proceeds (handled by security rules)

---

## Security Rules

* Stateless authentication (no session)
* Password stored using BCrypt
