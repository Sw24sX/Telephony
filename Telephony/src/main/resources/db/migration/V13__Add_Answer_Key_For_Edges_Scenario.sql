alter table "scenario_edge"
    add column "answer_key" varchar(5),
    add column "creation_date" timestamp not null default now(),
    add column "id" int8 not null primary key;

alter table "scenario_node_data"
    drop column "answer_key",
    add column "waiting_time" int4;

create table "scenario_node_position" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "x" int4 not null,
    "y" int4 not null
);

alter table "scenario_node_extra_data"
    add column "position" int8 references "scenario_node_position" ("id");