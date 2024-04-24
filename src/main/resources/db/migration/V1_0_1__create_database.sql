--create database SBP

create table usr
(
    id          bigserial    not null
        constraint usr_pk
            primary key,
    username    varchar(255) not null,
    surname     varchar(255) not null,
    age         integer      not null,
    city        varchar(255) not null,
    interesting text         not null
);