INSERT INTO role (
    id,
    role_name,
    description,
    created_at,
    updated_at,
    created_by,
    updated_by
)
VALUES
    (
        gen_random_uuid(),
        'SUPER_ADMIN',
        'System super administrator with full access',
        NOW(),
        NOW(),
        NULL,
        NULL
    ),
    (
        gen_random_uuid(),
        'ADMIN',
        'Administrator with elevated privileges',
        NOW(),
        NOW(),
        NULL,
        NULL
    ),
    (
        gen_random_uuid(),
        'OWNER',
        'Apartment or building owner',
        NOW(),
        NOW(),
        NULL,
        NULL
    ),
    (
        gen_random_uuid(),
        'RESIDENT',
        'Resident living in an apartment',
        NOW(),
        NOW(),
        NULL,
        NULL
    ),
    (
        gen_random_uuid(),
        'MEMBER',
        'Household member under a lease or ownership',
        NOW(),
        NOW(),
        NULL,
        NULL
    ),
    (
        gen_random_uuid(),
        'STAFF',
        'Operational or maintenance staff',
        NOW(),
        NOW(),
        NULL,
        NULL
    )
ON CONFLICT (role_name) DO NOTHING;
