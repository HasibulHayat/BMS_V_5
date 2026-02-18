CREATE TABLE apartment
(
    id                  UUID                        NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by          UUID,
    updated_by          UUID,
    name                VARCHAR(255)                NOT NULL,
    floor_number        INTEGER,
    sector_name         VARCHAR(255),
    building_id         UUID                        NOT NULL,
    total_room          INTEGER,
    total_bathroom      INTEGER,
    total_bedroom       INTEGER,
    total_balconies     INTEGER,
    area_sq_ft          DOUBLE PRECISION,
    parking_spot_number INTEGER,
    landphone_extension VARCHAR(255),
    notes               VARCHAR(1000),
    CONSTRAINT pk_apartment PRIMARY KEY (id)
);

CREATE TABLE apartment_ownership
(
    id                   UUID                        NOT NULL,
    created_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at           TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by           UUID,
    updated_by           UUID,
    apartment_id         UUID                        NOT NULL,
    user_profile_id      UUID                        NOT NULL,
    ownership_percentage DECIMAL(5, 2)               NOT NULL,
    ownership_start_date date                        NOT NULL,
    ownership_end_date   date,
    notes                VARCHAR(1000),
    CONSTRAINT pk_apartmentownership PRIMARY KEY (id)
);

CREATE TABLE building
(
    id                     UUID                        NOT NULL,
    created_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at             TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by             UUID,
    updated_by             UUID,
    name                   VARCHAR(255)                NOT NULL,
    building_code          VARCHAR(255)                NOT NULL,
    building_type          VARCHAR(255),
    description            VARCHAR(255),
    full_address           VARCHAR(255),
    street                 VARCHAR(255),
    area                   VARCHAR(255),
    city                   VARCHAR(255),
    district               VARCHAR(255),
    country                VARCHAR(255),
    postal_code            VARCHAR(255),
    latitude               DOUBLE PRECISION,
    longitude              DOUBLE PRECISION,
    total_floor            INTEGER,
    basement_floor         INTEGER,
    total_unit             INTEGER,
    construction_start     date,
    construction_end       date,
    elevator_count         INTEGER,
    has_generator          BOOLEAN,
    total_parking          INTEGER,
    has_guard              BOOLEAN,
    hascctv                BOOLEAN,
    water_source           VARCHAR(255),
    registration_number    VARCHAR(255)                NOT NULL,
    land_area_sq_ft        DOUBLE PRECISION,
    floor_area_sq_ft       DOUBLE PRECISION,
    unit_area_sq_ft        DOUBLE PRECISION,
    developer_name         VARCHAR(255),
    owner_association_name VARCHAR(255),
    notes                  VARCHAR(1000),
    CONSTRAINT pk_building PRIMARY KEY (id)
);

CREATE TABLE lease
(
    id                  UUID                        NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by          UUID,
    updated_by          UUID,
    apartment_id        UUID                        NOT NULL,
    resident_profile_id UUID                        NOT NULL,
    rent_amount         DECIMAL(10, 2)              NOT NULL,
    start_date          date                        NOT NULL,
    end_date            date,
    CONSTRAINT pk_lease PRIMARY KEY (id)
);

CREATE TABLE lease_member
(
    id                         UUID                        NOT NULL,
    created_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at                 TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by                 UUID,
    updated_by                 UUID,
    lease_id                   UUID                        NOT NULL,
    member_profile_id          UUID                        NOT NULL,
    relationship_with_resident VARCHAR(20)                 NOT NULL,
    CONSTRAINT pk_leasemember PRIMARY KEY (id)
);

CREATE TABLE role
(
    id          UUID                        NOT NULL,
    created_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at  TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by  UUID,
    updated_by  UUID,
    role_name   VARCHAR(20)                 NOT NULL,
    description VARCHAR(255)                NOT NULL,
    CONSTRAINT pk_role PRIMARY KEY (id)
);

CREATE TABLE user_auth
(
    id              UUID                        NOT NULL,
    created_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at      TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by      UUID,
    updated_by      UUID,
    username        VARCHAR(50)                 NOT NULL,
    email           VARCHAR(100)                NOT NULL,
    password_hash   VARCHAR(255)                NOT NULL,
    user_profile_id UUID                        NOT NULL,
    is_active       BOOLEAN                     NOT NULL,
    is_locked       BOOLEAN                     NOT NULL,
    CONSTRAINT pk_userauth PRIMARY KEY (id)
);

CREATE TABLE user_auth_role
(
    id           UUID                        NOT NULL,
    created_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at   TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by   UUID,
    updated_by   UUID,
    user_auth_id UUID                        NOT NULL,
    role_id      UUID                        NOT NULL,
    is_active    BOOLEAN                     NOT NULL,
    CONSTRAINT pk_userauthrole PRIMARY KEY (id)
);

CREATE TABLE user_profile
(
    id                      UUID                        NOT NULL,
    created_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    updated_at              TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    created_by              UUID,
    updated_by              UUID,
    first_name              VARCHAR(255)                NOT NULL,
    last_name               VARCHAR(255)                NOT NULL,
    middle_name             VARCHAR(255),
    phone_primary           VARCHAR(255)                NOT NULL,
    phone_secondary         VARCHAR(255),
    gender                  VARCHAR(255)                NOT NULL,
    blood_group             VARCHAR(255),
    birth_date              date,
    permanent_address       VARCHAR(255),
    occupation              VARCHAR(255),
    national_id             VARCHAR(255),
    passport_id             VARCHAR(255),
    driving_license_id      VARCHAR(255),
    emergency_contact_name  VARCHAR(255),
    emergency_contact_phone VARCHAR(255),
    is_married              BOOLEAN,
    is_active               BOOLEAN                     NOT NULL,
    notes                   VARCHAR(1000),
    CONSTRAINT pk_userprofile PRIMARY KEY (id)
);

ALTER TABLE user_auth_role
    ADD CONSTRAINT uc_b7fbd97b80cc8fc621f87bedf UNIQUE (user_auth_id, role_id);

ALTER TABLE building
    ADD CONSTRAINT uc_building_buildingcode UNIQUE (building_code);

ALTER TABLE building
    ADD CONSTRAINT uc_building_registrationnumber UNIQUE (registration_number);

ALTER TABLE apartment
    ADD CONSTRAINT uc_d66fe5f9c458d3a323a8352bc UNIQUE (building_id, name);

ALTER TABLE lease_member
    ADD CONSTRAINT uc_d6fb59c3bd21038475983e78f UNIQUE (lease_id, member_profile_id);

ALTER TABLE role
    ADD CONSTRAINT uc_role_rolename UNIQUE (role_name);

ALTER TABLE user_auth
    ADD CONSTRAINT uc_userauth_email UNIQUE (email);

ALTER TABLE user_auth
    ADD CONSTRAINT uc_userauth_user_profile UNIQUE (user_profile_id);

ALTER TABLE user_auth
    ADD CONSTRAINT uc_userauth_username UNIQUE (username);

ALTER TABLE user_profile
    ADD CONSTRAINT uc_userprofile_phoneprimary UNIQUE (phone_primary);

ALTER TABLE apartment_ownership
    ADD CONSTRAINT FK_APARTMENTOWNERSHIP_ON_APARTMENT FOREIGN KEY (apartment_id) REFERENCES apartment (id);

ALTER TABLE apartment_ownership
    ADD CONSTRAINT FK_APARTMENTOWNERSHIP_ON_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES user_profile (id);

ALTER TABLE apartment
    ADD CONSTRAINT FK_APARTMENT_ON_BUILDING FOREIGN KEY (building_id) REFERENCES building (id);

ALTER TABLE lease_member
    ADD CONSTRAINT FK_LEASEMEMBER_ON_LEASE FOREIGN KEY (lease_id) REFERENCES lease (id);

ALTER TABLE lease_member
    ADD CONSTRAINT FK_LEASEMEMBER_ON_MEMBER_PROFILE FOREIGN KEY (member_profile_id) REFERENCES user_profile (id);

ALTER TABLE lease
    ADD CONSTRAINT FK_LEASE_ON_APARTMENT FOREIGN KEY (apartment_id) REFERENCES apartment (id);

ALTER TABLE lease
    ADD CONSTRAINT FK_LEASE_ON_RESIDENT_PROFILE FOREIGN KEY (resident_profile_id) REFERENCES user_profile (id);

ALTER TABLE user_auth_role
    ADD CONSTRAINT FK_USERAUTHROLE_ON_ROLE FOREIGN KEY (role_id) REFERENCES role (id);

ALTER TABLE user_auth_role
    ADD CONSTRAINT FK_USERAUTHROLE_ON_USER_AUTH FOREIGN KEY (user_auth_id) REFERENCES user_auth (id);

ALTER TABLE user_auth
    ADD CONSTRAINT FK_USERAUTH_ON_USER_PROFILE FOREIGN KEY (user_profile_id) REFERENCES user_profile (id);