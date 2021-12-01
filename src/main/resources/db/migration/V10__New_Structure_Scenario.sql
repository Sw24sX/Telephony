create table "scenario_node_data" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "sound_path" varchar,
    "question" varchar,
    "is_positive" boolean not null default false
);

create table "scenario_node_extra_data" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now()
);

create table "scenario_node" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "type_code" int2 not null,
    "data" int8 not null references "scenario_node_data" ("id") ON DELETE CASCADE on update cascade,
    "extra_data" int8 not null references "scenario_node_extra_data" ("id")  ON DELETE CASCADE on update cascade
);

create table "scenario_edge" (
    "parent_node_id" int8 not null references "scenario_node" ("id"),
    "child_node_id" int8 not null references "scenario_node" ("id"),
    unique ("parent_node_id", "child_node_id")
);

alter table "scenario"
    add column "root_node" int8 not null references "scenario_node" ("id") ON DELETE CASCADE;

drop table "scenario_sound";
