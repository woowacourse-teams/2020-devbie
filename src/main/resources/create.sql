create table if not exists user
(
   id bigint auto_increment not null,
   oauth_id varchar(255) not null,
   email varchar(255),
   role_type varchar(255) not null,
   created_date datetime,
   modified_date datetime,
   primary key(id)
);

create table if not exists answer_recommendation
(
   id bigint auto_increment not null,
   user_id bigint not null,
   recommendation_type varchar(255),
   answer_id bigint not null,
   created_date datetime,
   modified_date datetime,
   primary key(id)
)

create table if not exists question_recommendation
(
   id bigint auto_increment not null,
   user_id bigint not null,
   recommendation_type varchar(255),
   question_id bigint not null,
   created_date datetime,
   modified_date datetime,
   primary key(id)
)

create table if not exists question
(
   id bigint auto_increment not null,
   user_id bigint not null,
   title varchar(255) not null,
   content varchar(255),
   visit_count bigint not null,
   created_date datetime,
   modified_date datetime,
   primary key(id)
)

create table if not exists answer
(
   id bigint auto_increment not null,
   user_id bigint not null,
   content varchar(255),
   created_date datetime,
   modified_date datetime,
   primary key(id)
)

create table if not exists notice
(
   id bigint auto_increment not null,
   title varchar(255) not null,
   content varchar(255) not null,
   notice_type varchar(255) not null,
   name varchar(255) not null,
   salary integer,
   start_time datetime,
   end_time datetime,
   job_position varchar(255) not null,
   image varchar(255),
   created_date datetime,
   modified_date datetime,
   primary key(id)
)

create table if not exists languages
(
   notice_id bigint not null,
   language varchar(255)
)
