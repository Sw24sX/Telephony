create table "dialing_statistic" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "dialing_id" int8 not null references "dialing" ("id") unique,
    "start_date" timestamp not null,
    "end_date" timestamp
);