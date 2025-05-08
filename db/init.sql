CREATE EXTENSION IF NOT EXISTS pg_trgm;

CREATE OR REPLACE FUNCTION ARRAY_TO_STRING_IMMUTABLE(
    arr TEXT[],
    sep TEXT
) RETURNS TEXT
    IMMUTABLE PARALLEL SAFE
    LANGUAGE SQL AS
$$
SELECT ARRAY_TO_STRING(arr, sep)
$$;

create table if not exists pessoas
(
    id         uuid not null,
    apelido    text
        constraint id_pk primary key,
    nome       text not null,
    nascimento date not null,
    stack     varchar(32)[],
    busca      text
);

create unlogged table if not exists cache
(
    key  text
        constraint cache_id_pk primary key,
    data uuid
);

create unique index if not exists idx_id_uuid on pessoas (id);
create index if not exists index_pessoas_on_search on pessoas using gist (busca gist_trgm_ops);

CREATE USER rinhaubr WITH PASSWORD 'rinhaubr';
GRANT CONNECT ON DATABASE rinha TO rinhaubr;

GRANT USAGE, CREATE ON SCHEMA public TO rinhaubr;
GRANT TRUNCATE ON ALL TABLES IN SCHEMA public TO rinhaubr;
GRANT SELECT, INSERT, UPDATE, DELETE, TRUNCATE ON ALL TABLES IN SCHEMA public TO rinhaubr;
ALTER DEFAULT PRIVILEGES IN SCHEMA public GRANT SELECT, INSERT, UPDATE, DELETE ON TABLES TO rinhaubr;
ALTER USER rinhaubr WITH SUPERUSER;
