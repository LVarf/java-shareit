DROP TABLE IF EXISTS users CASCADE;
DROP TABLE IF EXISTS items CASCADE;
DROP TABLE IF EXISTS bookings CASCADE;
DROP TABLE IF EXISTS requests CASCADE;
DROP TABLE IF EXISTS comments CASCADE;
DROP TYPE IF EXISTS status_enum CASCADE;

CREATE TYPE status_enum AS ENUM ('waiting', 'approved', 'rejected', 'canceled');

create table if not exists users
(
    id    bigint generated always as identity
        primary key,
    email varchar(50) not null
        constraint uq_user_email
            unique,
    name  varchar(50) not null
);

alter table users owner to postgres;

create table if not exists items
(
    id           bigint generated always as identity primary key,
    is_available boolean      not null default false,
    description  varchar(255) not null,
    name         varchar(50) not null,
    owner_id     bigint
        constraint fk_user references users,
    request_id   bigint
        constraint fk_item_rq references item_requests
);

alter table items
    owner to postgres;

create table if not exists item_requests
(
    id           bigint generated always as identity primary key,
    created      timestamp    not null,
    description  varchar(255) not null,
    requester_id bigint
        constraint fk_user
            references users
);

alter table item_requests
    owner to postgres;

create table if not exists bookings
(
    id            bigint generated always as identity primary key,
    end_booking   timestamp,
    start_booking timestamp not null,
    status        integer   not null,
    booker_id     bigint
        constraint fk_user
            references users,
    item_id       bigint
        constraint fk_item
            references items
);

alter table bookings
    owner to postgres;

create table if not exists comments
(
    id         bigint generated always as identity primary key,
    text       varchar(255) not null,
    author_id  bigint
        constraint fk_user
            references users,
    item_id_id bigint
        constraint fk_item
            references items
);

alter table comments
    owner to postgres;





--CREATE SEQUENCE HIBERNATE_SEQUENCE START WITH 1 INCREMENT BY 1;

--CREATE TABLE IF NOT EXISTS users (
--    id BIGINT,-- GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--    email varchar(50) NOT NULL,
--    name varchar(50) NOT NULL--,
    --CONSTRAINT pk_user PRIMARY KEY id,
    --CONSTRAINT uq_user_email UNIQUE (email)
--    );
/*
CREATE TABLE IF NOT EXISTS items (
    id BIGINT PRIMARY KEY,
    owner BIGINT REFERENCES users(id),--NOT NULL,
    name VARCHAR(250) NOT NULL,
    description VARCHAR(1000) NOT NULL,
    available boolean DEFAULT 'false'--,*/
--request BIGINT REFERENCES requests(id)--,
--CONSTRAINT pk_item PRIMARY KEY (id),
--CONSTRAINT fk_items_to_users FOREIGN KEY(owner) REFERENCES users(id)
--);

--CREATE TABLE IF NOT EXISTS tags (
--id BIGINT GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
--item_id BIGINT, name VARCHAR(50),
--CONSTRAINT fk_tags_to_items FOREIGN KEY(item_id) REFERENCES items(id)
--);