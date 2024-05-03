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

--changeset andrey:alter-table-unique
CREATE UNIQUE INDEX id_all
    ON teacher (name, surname, patronymic, phone_number, email, photo) nulls not distinct;