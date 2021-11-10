create table "scenario_step" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "next_positive" int8 not null references "scenario_step" ("id"),
    "next_negative" int8 not null references "scenario_step" ("id"),
    "sound_path" varchar,
    "question" varchar,
    "scenario_id" int8 not null references "scenario" ("id")
);

alter table "scenario"
    add column "first_step" int8 not null references "scenario_step" ("id");

drop table "scenario_sound";
