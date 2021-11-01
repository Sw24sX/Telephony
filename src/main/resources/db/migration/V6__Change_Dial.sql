drop table "dial_caller";
drop table "dial";

create table "caller_base" (
    "id" int8 NOT NULL PRIMARY KEY,
    "dialing-start" timestamp NOT NULL,
    "dialing-end" timestamp,
    "name" varchar(250) NOT NULL UNIQUE
);

create table "caller_base_callers" (
    "id" int8 NOT NULL PRIMARY KEY,
    "caller_base_id" int8 NOT NULL REFERENCES "caller_base"("id"),
    "caller_id" int8 NOT NULL REFERENCES "caller"("id"),
    unique ("caller_base_id", "caller_id")
);