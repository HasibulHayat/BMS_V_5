/* =========================================================
   1. SUPERADMIN USER PROFILE
   ========================================================= */

INSERT INTO user_profile (
    id,
    first_name,
    last_name,
    phone_primary,
    gender,
    is_active,
    created_at,
    updated_at,
    created_by,
    updated_by
)
SELECT
    gen_random_uuid(),
    'Hasibul',
    'Hayat',
    '+8801879393371',
    'MALE',
    TRUE,
    NOW(),
    NOW(),
    NULL,
    NULL
WHERE NOT EXISTS (
    SELECT 1
    FROM user_profile
    WHERE phone_primary = '+8801879393371'
);


/* =========================================================
   2. SUPERADMIN USER AUTH
   ========================================================= */

INSERT INTO user_auth (
    id,
    username,
    email,
    password_hash,
    user_profile_id,
    is_active,
    is_locked,
    created_at,
    updated_at,
    created_by,
    updated_by
)
SELECT
    gen_random_uuid(),
    'superadmin',
    'hasibulhayathasib@gmail.com',
    '$2a$12$0PXQZocNSEUCH6tzPy2Po.Zq9VcGgi0BptFLvcvZTvJ39PqOMmeka',
    up.id,
    TRUE,
    FALSE,
    NOW(),
    NOW(),
    NULL,
    NULL
FROM user_profile up
WHERE up.phone_primary = '+8801879393371'
  AND NOT EXISTS (
    SELECT 1
    FROM user_auth
    WHERE email = 'hasibulhayathasib@gmail.com'
);


/* =========================================================
   3. ASSIGN SUPER_ADMIN ROLE
   ========================================================= */

INSERT INTO user_auth_role (
    id,
    user_auth_id,
    role_id,
    is_active,
    created_at,
    updated_at,
    created_by,
    updated_by
)
SELECT
    gen_random_uuid(),
    ua.id,
    r.id,
    TRUE,
    NOW(),
    NOW(),
    NULL,
    NULL
FROM user_auth ua
         JOIN role r
              ON r.role_name = 'SUPER_ADMIN'
WHERE ua.email = 'hasibulhayathasib@gmail.com'
ON CONFLICT (user_auth_id, role_id) DO NOTHING;
