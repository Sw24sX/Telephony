alter table "scenario"
    add column "count_steps" int4 not null;

create view scenario_header as
select
    s.id,
    s."name",
    s.creation_date,
    s.count_steps
from scenario s;