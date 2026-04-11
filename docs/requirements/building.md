# Building Requirements

## Overview

Represents a physical building.

---

## Rules

* Building code must be unique
* Registration number must be unique
* Only authorized roles can create/update

---

## Permissions

* SUPER_ADMIN → create
* ADMIN → update
* OWNER → view

---

## Data

* Name, type, description
* Address fields (street, city, etc.)
* Infrastructure details:

  * floors
  * elevators
  * parking

---

## Validation

* Duplicate building code → rejected
* Duplicate registration number → rejected
