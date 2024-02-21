--liquibase formatted sql


--changeset andrey:create-table-timeslot

create table timeslot
(
    timeslot_id bigserial primary key,
    day_of_week smallint not null,
    time        time     not null,
    upper       boolean  not null
);

CREATE UNIQUE INDEX idx_time_dow_upper
    ON timeslot (time, day_of_week, upper);