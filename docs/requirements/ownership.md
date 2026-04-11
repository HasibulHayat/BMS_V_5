# Ownership Requirements

## Overview

Represents ownership of an apartment.

---

## Rules

* Multiple owners allowed
* Ownership is percentage-based
* Total active ownership ≤ 100%
* A user cannot have multiple active ownerships for same apartment

---

## Lifecycle

* ownershipStartDate → start
* ownershipEndDate → null = active

---

## Validation

* Ownership must be > 0 and ≤ 100
* Total ownership cannot exceed 100
* Duplicate active ownership → rejected

---

## Permissions

* SUPER_ADMIN / ADMIN → assign ownership

