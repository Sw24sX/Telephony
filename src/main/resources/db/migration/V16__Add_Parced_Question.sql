create table "question_part" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "question_part" varchar not null,
    "is_variable" boolean not null
);

create table "question" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now()
);

create table "question_question_part" (
    "question_part_id" int8 not null references "question_part" ("id"),
    "question_id" int8 not null references "question" ("id"),
    unique ("question_part_id", "question_id")
);

create table "question_variables" (
    "question_part_id" int8 not null references "question_part" ("id"),
    "question_id" int8 not null references "question" ("id"),
    unique ("question_part_id", "question_id")
);
