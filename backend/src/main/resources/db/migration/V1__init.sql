create sequence address_id_seq start with 1 increment by 1;
create sequence authority_id_seq start with 1 increment by 1;
create sequence reset_password_codes_seq start with 1 increment by 1;
create sequence role_id_seq start with 1 increment by 1;
create sequence users_id_seq start with 1 increment by 1;

create table address (
                         id bigint not null,
                         address varchar(255),
                         full_name varchar(255),
                         main boolean not null,
                         phone_number varchar(255),
                         title varchar(255),
                         version integer not null,
                         user_id bigint,
                         primary key (id)
);

create table authority (
                           id bigint not null,
                           permission varchar(255),
                           primary key (id)
);

create table reset_password_codes (
                                      id bigint not null,
                                      code integer not null,
                                      email varchar(255),
                                      expiry_date timestamp(6),
                                      primary key (id)
);

create table role (
                      id bigint not null,
                      name varchar(255),
                      primary key (id)
);

create table role_authority (
                                role_id bigint not null,
                                authority_id bigint not null,
                                primary key (role_id, authority_id)
);

create table user_role (
                           user_id bigint not null,
                           role_id bigint not null,
                           primary key (user_id, role_id)
);

create table users (
                       id bigint not null,
                       created_at timestamp(6),
                       email varchar(255),
                       enabled boolean not null,
                       full_name varchar(255),
                       password varchar(255),
                       phone_number varchar(255),
                       points bigint not null,
                       used_points bigint not null,
                       username varchar(255),
                       version integer not null,
                       primary key (id)
);

create table verification (
                              id varchar(36) not null,
                              username varchar(255) not null,
                              primary key (id)
);

alter table if exists role
    add constraint role_name_key unique (name);

alter table if exists users
    add constraint users_email_key unique (email);

alter table if exists users
    add constraint users_username_key unique (username);

alter table if exists verification
    add constraint verification_username_key unique (username);

alter table if exists address
    add constraint address_user_fkey
    foreign key (user_id)
    references users;

alter table if exists role_authority
    add constraint FKqbri833f7xop13bvdje3xxtnw
    foreign key (authority_id)
    references authority;

alter table if exists role_authority
    add constraint FK2052966dco7y9f97s1a824bj1
    foreign key (role_id)
    references role;

alter table if exists user_role
    add constraint FKa68196081fvovjhkek5m97n3y
    foreign key (role_id)
    references role;

alter table if exists user_role
    add constraint FKj345gk1bovqvfame88rcx7yyx
    foreign key (user_id)
    references users;
