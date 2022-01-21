alter table "dialing_caller_result"
    add column "start_call_from_start_day" int4 not null default 0,
    add column "end_call_from_start_day" int4 not null default 0,
    add constraint "start_and_end_date" check ( "start_call_from_start_day" <= 86400000 and "end_call_from_start_day" <= 86400000 ),
    drop column "start_call",
    drop column "end_date";