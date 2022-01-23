alter table "dialing_caller_result"
    add column if not exists "start_call" timestamp not null default now(),
    add column if not exists  "end_date" timestamp not null default now();