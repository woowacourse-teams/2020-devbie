create table answer (
    id bigint auto_increment not null,
    created_date datetime,
    modified_date datetime,
    content TEXT,
    question_id bigint,
    user_id bigint,
    recommended_count bigint default 0,
    non_recommended_count bigint default 0,
    primary key (id)
);

create table answer_recommendation (
    id bigint auto_increment not null ,
    created_date datetime,
    modified_date datetime,
    recommendation_type varchar(255),
    user_id bigint,
    answer_id bigint,
    primary key (id)
);

create table chat (
    id bigint auto_increment not null,
    message varchar(255),
    chat_name varchar(255),
    color varchar(255),
    chatroom_id bigint,
    primary key (id)
);

create table chat_room (
    id bigint auto_increment not null ,
    notice_id bigint,
    primary key (id)
);

create table language (
    notice_id bigint not null,
    languages varchar(255)
);

create table notice (
    id bigint auto_increment not null ,
    created_date datetime,
    modified_date datetime,
    name varchar(255),
    salary integer,
    end_date datetime,
    start_date datetime,
    image varchar(255),
    job_position varchar(255) not null,
    content TEXT,
    notice_type varchar(255) not null,
    title varchar(255) not null,
    apply_url varchar(255),
    recruitment_type varchar(255),
    primary key (id)
);

create table notice_favorite (
    id bigint auto_increment not null ,
    created_date datetime,
    modified_date datetime,
    user_id bigint,
    notice_id bigint,
    primary key (id)
);

create table question (
    id bigint auto_increment not null,
    created_date datetime,
    modified_date datetime,
    content TEXT,
    title varchar(255),
    user_id bigint,
    visit_count bigint,
    recommended_count bigint default 0,
    non_recommended_count bigint default 0,
    primary key (id)
);

create table hashtag (
    id bigint auto_increment not null ,
    created_date datetime,
    modified_date datetime,
    name varchar(255),
    primary key (id)
);

create table question_hashtag (
    id bigint auto_increment not null,
    hashtag_id bigint,
    question_id bigint,
    primary key (id)
);

create table question_favorite (
    id bigint auto_increment not null,
    created_date datetime,
    modified_date datetime,
    user_id bigint,
    question_id bigint,
    primary key (id)
);

create table question_recommendation (
    id bigint auto_increment not null,
    created_date datetime,
    modified_date datetime,
    recommendation_type varchar(255),
    user_id bigint,
    question_id bigint,
    primary key (id)
);

create table user (
    id bigint auto_increment not null,
    created_date datetime,
    modified_date datetime,
    email varchar(255),
    image varchar(255),
    name varchar(255),
    oauth_id varchar(255),
    role_type varchar(255),
    primary key (id)
);
