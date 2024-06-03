create table requests
(
    id               bigserial    not null
        constraint requests_pk
            primary key,
    model_name       varchar(50)  not null,
    data             timestamp    not null,
    user_security_id bigserial    not null
        constraint requests_usr_security_id_fk
            references usr_security
            on update cascade on delete cascade,
    text             varchar(255) not null
);

alter table requests
    add login varchar(255) not null;