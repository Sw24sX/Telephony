alter table "scenario_edge"
    drop column "parent_node_id",
    drop column "child_node_id",
    add column "parent_node_id" int8 references "scenario_node" ("id"),
    add column "child_node_id" int8 references "scenario_node" ("id");