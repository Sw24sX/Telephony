drop table "dial_caller";
drop table "dial";

create table "caller_base" (
    "id" int8 NOT NULL PRIMARY KEY,
    "name" varchar(250) NOT NULL
);

create table "caller_base_callers" (
    "caller_base_id" int8 NOT NULL REFERENCES "caller_base"("id"),
    "caller_id" int8 NOT NULL REFERENCES "caller"("id"),
    unique ("caller_base_id", "caller_id")
);