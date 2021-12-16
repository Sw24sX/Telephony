create table "question" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now()
);

create table "question_part" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "question_part" varchar not null,
    "is_variable" boolean not null,
    "question_id" int8 not null references "question" ("id")
);

alter table "scenario_node_data"
    drop column "question",
    add column "question_id" int8 not null references "question" ("id") unique;
