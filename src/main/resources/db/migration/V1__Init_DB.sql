CREATE TABLE users
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    username varchar(64) UNIQUE NOT NULL,
    password_hash varchar(96) NOT NULL,
    balance double precision NOT NULL DEFAULT 0.0,
    is_admin bool NOT NULL DEFAULT false,

    CONSTRAINT PK_users__key PRIMARY KEY(id)
);

CREATE TABLE transport_types
(
    id smallint GENERATED ALWAYS AS IDENTITY,
    type varchar(18) UNIQUE NOT NULL,

    CONSTRAINT PK_transport_types__key PRIMARY KEY(id)
);

INSERT INTO transport_types(type)
VALUES ('Car'), ('Bike'), ('Scooter');

CREATE TABLE transports
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    identifier varchar(64) NOT NULL,
    model varchar(64) NOT NULL,
    color varchar(48) NOT NULL,
    description varchar(256),
    latitude double precision NOT NULL,
    longitude double precision NOT NULL,
    minute_price double precision,
    day_price double precision,
    type_id int NOT NULL,
    owner_id bigint NOT NULL,
    can_be_rented bool NOT NULL,

    CONSTRAINT PK_transports__key PRIMARY KEY(id),
    CONSTRAINT FK_transports__type FOREIGN KEY(type_id) REFERENCES transport_types(id),
    CONSTRAINT FK_transports__owner FOREIGN KEY(owner_id) REFERENCES users(id),
    CONSTRAINT CK_transports__minute_price CHECK(minute_price>=0),
    CONSTRAINT CK_transports__day_price CHECK(day_price>=0)
);

CREATE TABLE rent_transport_types
(
    id smallint GENERATED ALWAYS AS IDENTITY,
    type varchar(18) UNIQUE NOT NULL,

    CONSTRAINT PK_rent_transport_types__key PRIMARY KEY(id)
);

INSERT INTO rent_transport_types(type)
VALUES ('Minutes'), ('Days');

CREATE TABLE rent_transports
(
    id bigint GENERATED ALWAYS AS IDENTITY,
    user_id bigint NOT NULL,
    transport_id bigint NOT NULL,
    type_id int NOT NULL,
    final_price double precision,
    price_of_unit double precision NOT NULL,
    time_start timestamp NOT NULL,
    time_end timestamp,

    CONSTRAINT PK_rents__key PRIMARY KEY(id),
    CONSTRAINT FK_rents__user FOREIGN KEY(user_id) REFERENCES users(id),
    CONSTRAINT FK_rents__transport FOREIGN KEY(transport_id) REFERENCES transports(id),
    CONSTRAINT FK_rents__type FOREIGN KEY(type_id) REFERENCES rent_transport_types(id),
    CONSTRAINT CK_rents__final_price CHECK(final_price>=0),
    CONSTRAINT CK_rents__price_of_unit CHECK(price_of_unit>=0),
    CONSTRAINT CK_rents__time_start__time_end CHECK(time_start<=time_end)
);

CREATE TABLE user_blocked_token_keys
(
    key uuid,
    user_id bigint NOT NULL,

    CONSTRAINT PK_user_blocked_token_keys__key PRIMARY KEY(key),
    CONSTRAINT FK_user_blocked_token_keys__user FOREIGN KEY(user_id) REFERENCES users(id)
);