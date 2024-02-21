--liquibase formatted sql


--changeset andrey:create-table-room

create table room
(
    room_id     bigserial primary key,
    building    text not null,
    room_number int  not null,
    letter     varchar(1)
);