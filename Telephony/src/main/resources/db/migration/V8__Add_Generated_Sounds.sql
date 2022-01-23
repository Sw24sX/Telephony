create table "generated_sound" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "path" varchar not null unique,
    "uri" varchar not null unique
);

alter table "sound"
    alter column "path" TYPE varchar,
    alter column "path" set not null;

alter table "sound"
    alter column "uri" TYPE varchar,
    alter column "uri" set not null;

alter table "sound"
    add constraint "path_unique" unique ("path");

alter table "sound"
    add constraint "uri_unique" unique ("uri");