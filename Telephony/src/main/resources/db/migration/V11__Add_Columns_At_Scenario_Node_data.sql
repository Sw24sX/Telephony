alter table "scenario_node_data"
    drop column "is_positive",
    add column "answer_key" varchar(10),
    add column "need_answer" boolean not null default false;