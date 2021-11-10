create table "scenario_step" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "next_positive" int8 not null references "scenario_step" ("id"),
    "next_negative" int8 not null references "scenario_step" ("id"),
    "sound_path" varchar,
    "question" varchar
);

alter table "scenario"
    add column "first_step" int8 not null references "scenario_step" ("id");

alter table "scenario_sound"
    rename to "scenario_scenario_step";

alter table "scenario_scenario_step"
    drop column "sound_id",
    drop column "creation_date",
    add column "scenario_step_id" int8 not null references "scenario_step" ("id");