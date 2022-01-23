create table "sound" (
    "id" int8 not null primary key,
    "blob" bytea NOT NULL,
    "name" varchar(250) not null
);