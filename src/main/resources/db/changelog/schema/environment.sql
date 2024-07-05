--liquibase formatted sql


--changeset andrey:create-table-environment

create table environment
(
    key text primary key,
    value text not null
)