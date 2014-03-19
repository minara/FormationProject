use `computer-database-db`;

drop table if exists log;

create table log (
  id                        bigint not null auto_increment,
  time	                    timestamp,
  operation                 varchar(255),
  table_name		    varchar(255),
  computer_id               bigint,
  constraint pk_log primary key (id))
;