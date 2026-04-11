# API Overview

## Base URL

```id="q4vh6z"
http://localhost:8080/api/v1
```

---

## Main Endpoints

### Auth

* POST `/auth/login`
* GET `/auth/me`

---

### Super Admin

* POST `/superadmin/admins`
* GET `/superadmin/admins`

---

### Admin

* `/admin/buildings`
* `/admin/apartments`
* `/admin/owners`
* `/admin/leases`
* `/admin/apartment-ownerships`

---

### User

* GET `/users/me`
* PUT `/users/me`

---

## Authentication

All secured endpoints require:

```id="qv0n0j"
Authorization: Bearer <token>
```
