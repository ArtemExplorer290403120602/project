create table responses
(
    id            bigserial
        constraint responses_pk
            primary key,
    request_id    bigserial
        constraint responses_request_id_fk
            references requests
            on update cascade on delete cascade,
    user_id       bigserial
        constraint responses_user_id_fk
            references usr_security
            on update cascade on delete cascade,
    response_text varchar(255) not null,
    response_date timestamp    not null,
    login         varchar(255) not null,
    model_name    varchar(255) not null
);

alter table responses
    owner to postgres;