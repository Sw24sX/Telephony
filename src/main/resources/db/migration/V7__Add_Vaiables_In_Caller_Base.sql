alter table "caller" add "variables" jsonb;

alter table "caller_base" add "variables_list" varchar(125) ARRAY NOT NULL;

alter table "caller" drop "name";

alter table "caller" add "valid" boolean NOT NULL default FALSE;