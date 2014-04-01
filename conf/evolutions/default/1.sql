# --- !Ups

create sequence article_seq;

create sequence comment_seq;

create sequence newsletter_seq;

create sequence user_seq;

create table article (
  id                        integer default nextval('article_seq'::regclass) not null,
  title                     varchar(200),
  intro                     varchar(5000),
  body                      varchar(5000),
  summary                   varchar(5000),
  date                      timestamp(6) default now(),
  image                     bytea,
  views                     integer,
  constraint pk_article primary key (id))
;

create table comment (
  id                        integer default nextval('comment_seq'::regclass) not null,
  name                      varchar(50),
  email                     varchar(100),
  likes                     integer,
  date                      timestamp(6) default now(),
  article_id                integer,
  content                   varchar(255),
  inappropriate             integer,
  constraint pk_comment primary key (id))
;

create table newsletter (
  id                        integer default nextval('newsletter_seq'::regclass) not null,
  name                      varchar(200),
  email                     varchar(200),
  topic1                    integer,
  topic2                    integer,
  topic3                    integer,
  topic4                    integer,
  topic5                    integer,
  constraint pk_newsletter primary key (id))
;

create table users (
  id                        integer default nextval('user_seq'::regclass) not null,
  email                     varchar(100),
  password                  varchar(100),
  first_name                varchar(100),
  last_name                 varchar(100),
  constraint pk_users primary key (id))
;

# --- !Downs

drop table if exists article cascade;

drop table if exists comment cascade;

drop table if exists newsletter cascade;

drop table if exists users cascade;

drop sequence if exists article_seq;

drop sequence if exists comment_seq;

drop sequence if exists newsletter_seq;

drop sequence if exists user_seq;