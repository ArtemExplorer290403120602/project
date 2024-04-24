create table usr_security
(
    id           bigserial             not null
        constraint usr_security_pk
            primary key,
    login        varchar(255)          not null,
    password     varchar(255)          not null,
    email        varchar(255)          not null,
    role         varchar(20),
    "is_Blocked" boolean default false not null,
    user_id      bigserial             not null
        constraint usr_security_usr_id_fk
            references usr (id)
            on update cascade on delete cascade
);

create unique index usr_security_login_uindex
    on usr_security (login);

create unique index usr_security_user_id_uindex
    on usr_security (user_id);