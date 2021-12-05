alter table "scenario_edge"
    add column "answer_key" varchar(5),
    add column "updated_date" timestamp not null default now();