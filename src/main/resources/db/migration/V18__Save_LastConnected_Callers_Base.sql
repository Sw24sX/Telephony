alter table "scenario"
    add column "last_connected_callers_base_id" int8 references "caller_base" ("id");