create table "dialing" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "start_date" timestamp not null default now(),
    "is_scheduled" boolean not null default false,
    "caller_base_id" int8 not null references "caller_base" ("id"),
    "scenario_id" int8 not null references "scenario" ("id"),
    "status_code" int2 not null
);

create table "dialing_caller_result" (
    "id" int8 not null primary key,
    "creation_date" timestamp not null default now(),
    "caller_id" int8 not null references "caller" ("id"),
    "is_hold_on" boolean not null,
    "answers" varchar[],
    "dialing_id" int8 not null references "dialing"("id")
);