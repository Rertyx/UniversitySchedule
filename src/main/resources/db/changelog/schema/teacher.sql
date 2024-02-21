--liquibase formatted sql


--changeset andrey:create-table-teacher

create table teacher
(
    teacher_id   bigserial primary key,
    name         text not null,
    surname      text not null,
    patronymic   text,
    phone_number text,
    email        text,
    photo        text
);