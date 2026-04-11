# BMS_V_5 - Building Management System
<br>

## Overview
A full-stack Apartment Management System for managing buildings, apartments, ownership structures,lease management and others with role-based access control.

## Architecture
- Frontend: Bootstrap + JavaScript
- Backend: Spring Boot (REST API)
- Database: PostgreSQL

## Project Structure
BMS_V_5/
<br>
 ├── backend
 <br>
 ├── frontend

## Features
- Building Management
- Apartment Management
- Resident Management
- Owner Management
- Lease Management
- User Authentication
- Role-based access control

## User Roles

| Role        | Description                                   |
| ----------- | --------------------------------------------- |
| SUPER_ADMIN | Full system control, can create admins        |
| ADMIN       | Manages buildings, apartments, owners, leases |
| OWNER       | Owns apartments, can view buildings           |
| RESIDENT    | Lives in apartments                           |
| MEMBER      | Household member                              |
| STAFF       | Operational staff                             |

## Future Improvements
- Payment Tracking
- Notification System
- Dashboard analytics
- Production deployment
