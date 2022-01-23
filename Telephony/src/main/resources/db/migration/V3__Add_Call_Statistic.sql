create table "call_statistic" (
    "id" int8 NOT NULL PRIMARY KEY,
    "caller_id" int8 NOT NULL REFERENCES "caller" ("id"),
    "channel" varchar(100)
);

create table "call_statistic_digit" (
    "id" int8 NOT NULL PRIMARY KEY,
    "call_statistic_id" int8 NOT NULL REFERENCES "call_statistic"("id"),
    "digit" varchar(50)
);