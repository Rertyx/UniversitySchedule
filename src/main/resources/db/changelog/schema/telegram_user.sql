--liquibase formatted sql

--changeset andrey:create-table-telegram_user

create table telegram_user
(
    chat_id bigserial primary key,
    group_number int not null
);