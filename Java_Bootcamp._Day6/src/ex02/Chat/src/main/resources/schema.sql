drop schema if exists chat cascade;

create schema if not exists chat;

create table if not exists chat.user
(
    id       serial primary key,
    login    varchar not null unique,
    password varchar not null
);

create table if not exists chat.chatroom
(
    id    serial primary key,
    name  varchar not null unique,
    owner integer not null,
    foreign key (owner) references chat.user (id)
);

create table if not exists chat.message
(
    id        serial primary key,
    author    integer not null,
    room      integer not null,
    text      text    not null,
    date_time timestamp default current_timestamp,
    foreign key (author) references chat.user (id),
    foreign key (room) references chat.chatroom (id)
);

create table if not exists chat.user_chatroom
(
    id          serial primary key,
    user_id     integer not null,
    chatroom_id integer not null,
    foreign key (user_id) references chat.user (id),
    foreign key (chatroom_id) references chat.chatroom (id)
);