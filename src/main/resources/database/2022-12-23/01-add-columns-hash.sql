--liquibase formatted sql
--changeset RobocikD:23
alter table users add hash varchar(120);
--changeset RobocikD:24
alter table users add hash_date datetime;