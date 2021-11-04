alter table "caller" add "variables" jsonb;

alter table "caller_base" add "variables_list" varchar(125) ARRAY NOT NULL;

alter table "caller" drop "name";

alter table "caller" add "valid" boolean NOT NULL default FALSE;

alter table "caller_base_callers" drop constraint "caller_base_callers_caller_base_id_caller_id_key";

alter table "caller_base_callers" add constraint "unique_caller_id" unique ("caller_id");