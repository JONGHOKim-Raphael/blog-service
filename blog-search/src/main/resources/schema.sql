-- H2 schema for @DataJpaTest
--   table drop 하고 다시 create 한다
drop table if exists keyword_log;
create table keyword_log (
    keyword varchar primary key,
    search_count int not null
);