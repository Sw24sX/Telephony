create table "scenario" (
    "id" int8 not null primary key,
    "name" varchar(250) not null
);

create table "scenario_sound" (
    "id" int8 not null primary key,
    "scenario_id" int8 not null references "scenario"("id"),
    "sound_id" int8 not null references "sound" ("id")
);

alter table "sound"
    add "path" varchar(1024) not null unique;

alter table "sound" drop column "blob";