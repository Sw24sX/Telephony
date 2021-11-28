alter table "caller"
    add column "number" int4 not null default 1,
    add column "is_valid" boolean not null default true;

alter table "caller_base"
    add column "updated_date" timestamp not null default now();