# BMS_V_5 - Building Management System
<br>


## Overview

A modern system designed to simplify and automate building and apartment management.

It helps property managers, owners, and administrators efficiently handle:

* Apartments and buildings
* Ownership tracking (even with multiple owners)
* Tenant and lease management
* User access and roles

Built with real-world scenarios in mind to reduce manual work, errors, and operational complexity.

<br>

## What Problems It Solves

* Eliminates manual tracking of ownership and tenants
* Prevents conflicts with clear ownership percentages
* Ensures only one active lease per apartment
* Centralizes all building-related data in one system
* Provides controlled access for different user roles

<br>

## Architecture

* **Frontend:** Bootstrap + JavaScript
* **Backend:** Spring Boot (REST API)
* **Database:** PostgreSQL

<br>

## Project Structure

BMS_V_5/
├── backend
├── frontend

<br>

## Features

* Building & Apartment Management
* Ownership Management (multi-owner, percentage-based)
* Lease Management with validation rules
* User & Role Management
* Secure Authentication (JWT-based)

<br>

## User Roles

| Role        | Description                                   |
| ----------- | --------------------------------------------- |
| SUPER_ADMIN | Full system control, can create admins        |
| ADMIN       | Manages buildings, apartments, owners, leases |
| OWNER       | Owns apartments, can view buildings           |
| RESIDENT    | Lives in apartments                           |
| MEMBER      | Household member                              |
| STAFF       | Operational staff                             |

<br>

## Future Improvements

* Payment Tracking
* Notification System (rent reminders, alerts)
* Dashboard analytics
* Production deployment

<br>

## Why This System?

This system is designed not just as software, but as a **practical solution for real estate and property management businesses**, helping them operate more efficiently and scale easily.
