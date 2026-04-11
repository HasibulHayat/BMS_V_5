# User Requirements

## Overview

Represents system users with authentication and profile separation.

---

## Structure

* UserAuth → login credentials
* UserProfile → personal information

---

## Rules

* Email must be unique
* Username must be unique
* Primary phone must be unique
* User must be active to use system

---

## Status Flags

* isActive → user enabled/disabled
* isLocked → login restricted

---

## Profile Data

* Name, gender, phone
* Optional fields:

  * address
  * occupation
  * emergency contact

---

## Update Rules

* Users can update their own profile
* Empty values normalized to null
