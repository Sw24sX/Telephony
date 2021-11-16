create table "variables_type_name" (
    "id" int8 not null primary key,
    "current_name" varchar(128) not null,
    "table_name" varchar(128) not null,
    "type_code" int2 not null,
    "caller_base_id" int8 not null references "caller_base" ("id"),
    "creation_date" timestamp not null default now()
);

alter table "caller_base"
    drop "variables_list",
    add column "is_confirmed" boolean not null default false,
    add column "number_id" int8 references "variables_type_name" ("id");

create table "caller_variables" (
    "id" int8 not null primary key,
    "variable_id" int8 not null references "variables_type_name" ("id"),
    "caller_id" int8 not null references "caller" ("id"),
    "value" varchar not null,
    "creation_date" timestamp not null default now()
);

alter table "caller"
    drop column "variables",
    drop column "number",
    add column "callers_base_id" int8 not null references "caller_base" ("id"),
    drop column "valid";


drop table "caller_base_callers";

create table "invalid_variables_caller" (
    "caller_id" int8 not null references "caller" ("id"),
    "caller_variables_id" int8 not null references "caller_variables" ("id"),
    unique ("caller_id", "caller_variables_id")
);