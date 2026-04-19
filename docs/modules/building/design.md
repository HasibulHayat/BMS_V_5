# 🧠 Building Module – Design

---

## 🎯 Core Idea

The Building module uses a **progressive data model**:

- Buildings are created with minimal data
- Additional data is added later
- Different roles interact at different stages

---

## 👥 Roles & Permissions

| Role         | Permissions              |
|-------------|--------------------------|
| SUPER_ADMIN | Create, View, Update     |
| ADMIN       | View, Update             |
| OWNER       | View only                |

---

## 🔄 Lifecycle

### 1. Skeleton Creation
- Actor: SUPER_ADMIN
- Purpose: Register building in system

#### Required Fields:
- name
- buildingCode (unique)
- registrationNumber (unique)

#### Optional Fields:
- buildingType
- city, country
- description

---

### 2. Enrichment / Completion
- Actor: SUPER_ADMIN, ADMIN
- Purpose: Add detailed building information

#### Includes:
- Address (street, area, district, postalCode)
- Structure (floors, units, parking, elevator)
- Facilities (generator, guard, CCTV)
- Geo (latitude, longitude)
- Area info (land, floor, unit area)
- Dates (construction start/end)
- Metadata (developer, association, notes)

---

### 3. Consumption
- Actor: OWNER
- Purpose: Read-only access to building data

---

## 🧩 Data Behavior

### Partial Entities
- Buildings may exist with incomplete data
- Null values are expected in early stages

---

### Updates
- Updates are flexible (partial updates allowed)
- Only provided fields are modified

---

## 🔐 Constraints

- buildingCode must be unique
- registrationNumber must be unique
- Only authorized roles can perform actions

---

## ⚠️ Known Decisions

### No Ownership Filtering
- Owners can view all buildings
- Simplifies current system design

---

### No Completion Flag (Yet)
- System does not track if a building is "complete"
- Can be added later if needed

---

## 🚀 Future Improvements

- Add `isProfileComplete`
- Add ownership-based filtering
- Add pagination for large datasets
