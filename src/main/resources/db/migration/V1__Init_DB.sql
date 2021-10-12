create sequence hibernate_sequence start 1 increment 1;

DROP TABLE IF EXISTS "caller" CASCADE ;

create table "caller" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(255) NOT NULL,
    "number" varchar(255) NOT NULL
);