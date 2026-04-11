# Apartment Ownership Assignment

## Overview

Handles assigning ownership to users.

---

## Rules

* Owner must exist
* Apartment must exist
* Ownership must follow percentage rules

---

## Validations

* Owner already has active ownership → rejected
* Total ownership exceeds 100% → rejected

---

## Output

* Ownership record created
* Ownership is active unless end date is set

---

## Permissions

* SUPER_ADMIN / ADMIN → assign ownership
