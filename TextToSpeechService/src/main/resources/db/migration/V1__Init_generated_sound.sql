create sequence hibernate_sequence start 1 increment 1;

create table "generated_sound" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "text" varchar not null unique,
    "uri" varchar not null unique,
    "path" varchar not null unique
);