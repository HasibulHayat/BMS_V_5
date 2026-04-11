# Role Requirements

## Overview

Defines access control using role-based authorization.

---

## Available Roles

* SUPER_ADMIN
* ADMIN
* OWNER
* RESIDENT
* MEMBER
* STAFF

---

## Rules

* A user can have multiple roles
* Roles are assigned via UserAuthRole
* Only active roles are considered

---

## Permissions

### SUPER_ADMIN

* Full system control
* Can create admins

### ADMIN

* Manage buildings, apartments, owners, leases

### OWNER

* View owned buildings and apartments

### RESIDENT

* Associated with lease

---

## Validation

* Unauthorized role access → Forbidden (403)


