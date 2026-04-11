# Lease Requirements

## Overview

Represents a resident occupying an apartment.

---

## Rules

* Only one active lease per apartment
* Apartment must have at least one active owner
* Lease must have a resident

---

## Resident Selection

Exactly one of the following must be provided:

1. Existing user
2. New user
3. Owner of the apartment

---

## Validations

* Multiple selection → rejected
* No selection → rejected
* Existing lease → rejected
* No owner → rejected

---

## Data

* apartmentId
* residentProfileId
* rentAmount
* startDate
* endDate (optional)

---

## Permissions

* SUPER_ADMIN / ADMIN → create lease
