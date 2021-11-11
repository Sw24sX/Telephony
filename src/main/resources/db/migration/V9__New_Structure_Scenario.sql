create table "scenario_step" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "parent_id" int8 references "scenario_step" ("id") ON DELETE CASCADE,
    "sound_path" varchar,
    "question" varchar,
    "positive_way" boolean not null default false
);

alter table "scenario"
    add column "first_step" int8 not null references "scenario_step" ("id");

drop table "scenario_sound";
