alter table "dialing_caller_result"
    add column "start_call_from_start_day" time not null default now(),
    add column "end_call_from_start_day" time not null default now(),
    drop column "start_call",
    drop column "end_date";