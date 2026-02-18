/* =========================
   APARTMENT & BUILDING
   ========================= */

CREATE INDEX idx_apartment_building_id
    ON apartment (building_id);


/* =========================
   APARTMENT OWNERSHIP
   ========================= */

CREATE INDEX idx_apartment_ownership_apartment_id
    ON apartment_ownership (apartment_id);

CREATE INDEX idx_apartment_ownership_user_profile_id
    ON apartment_ownership (user_profile_id);

/* useful when checking ownership of a user for a specific apartment */
CREATE INDEX idx_apartment_ownership_apartment_user
    ON apartment_ownership (apartment_id, user_profile_id);


/* =========================
   LEASE
   ========================= */

CREATE INDEX idx_lease_apartment_id
    ON lease (apartment_id);

CREATE INDEX idx_lease_resident_profile_id
    ON lease (resident_profile_id);


/* =========================
   LEASE MEMBER
   ========================= */

CREATE INDEX idx_lease_member_lease_id
    ON lease_member (lease_id);

CREATE INDEX idx_lease_member_member_profile_id
    ON lease_member (member_profile_id);

/* important: supports fast existence checks + resident member limits */
CREATE INDEX idx_lease_member_lease_member
    ON lease_member (lease_id, member_profile_id);


/* =========================
   USER AUTH / ROLES
   ========================= */

CREATE INDEX idx_user_auth_role_user_auth_id
    ON user_auth_role (user_auth_id);

CREATE INDEX idx_user_auth_role_role_id
    ON user_auth_role (role_id);

/* speeds up role resolution per user */
CREATE INDEX idx_user_auth_role_user_auth_active
    ON user_auth_role (user_auth_id, is_active);


/* =========================
   AUTH LOOKUPS
   ========================= */

/* UNIQUE constraints already create indexes,
   these are optional but explicit and readable */

CREATE INDEX idx_user_auth_email
    ON user_auth (email);

CREATE INDEX idx_user_auth_username
    ON user_auth (username);


/* =========================
   OPTIONAL (ADD LATER)
   ========================= */
/*
-- Audit querying (only if you build audit screens)
CREATE INDEX idx_building_created_by
    ON building (created_by);

CREATE INDEX idx_apartment_created_by
    ON apartment (created_by);
*/
