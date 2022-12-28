--liquibase formatted sql
--changeset RobocikD:26
alter table `order`
    add order_hash varchar(12);