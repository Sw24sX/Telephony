alter table "scenario_edge"
    add column "answer_key" varchar(5),
    add column "creation_date" timestamp not null default now(),
    add column "id" int8 not null primary key;