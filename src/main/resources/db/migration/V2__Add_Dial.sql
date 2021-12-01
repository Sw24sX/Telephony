create table "dial" (
    "id" int8 NOT NULL PRIMARY KEY,
    "dialing-start" timestamp NOT NULL,
    "dialing-end" timestamp,
    "name" int8 NOT NULL UNIQUE
);

create table "dial_caller" (
    "id" int8 NOT NULL PRIMARY KEY,
    "dial_id" int8 NOT NULL REFERENCES "dial"("id"),
    "caller_id" int8 NOT NULL REFERENCES "caller"("id"),
    unique ("dial_id", "caller_id")
);