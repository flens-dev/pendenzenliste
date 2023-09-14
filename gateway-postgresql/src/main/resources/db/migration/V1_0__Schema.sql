create table "todos"
(
    "id"            text,
    "headline"      text      not null,
    "description"   text      not null,
    "created"       timestamp not null,
    "last_modified" timestamp not null,
    "completed"     timestamp,
    "state"         text      not null,

    primary key ("id")
);

create table "achievements"
(
    "id"       text,
    "name"     text not null,
    "state"    text not null,
    "unlocked" timestamp,

    primary key ("id")
);