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
    start_date timestamp/*not null*/,
    end_date   timestamp,
    status        varchar(50)   not null,
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