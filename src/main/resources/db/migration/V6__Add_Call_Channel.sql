create table "call_channel" (
    "id" int8 not null primary key,
    "channel_id" varchar(30) not null unique,
    "scenario_id" int8 not null references "scenario" ("id"),
    "caller_id" int8 not null references "caller" ("id")
);