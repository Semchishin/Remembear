--create table user
create table if not exists users(
    id serial primary key,
    name varchar(64),
    login varchar(64),
    password varchar(64)
);

comment on table users is 'Таблица пользователей';
comment on column users.id is 'Уникальный идентификатор пользователя';
comment on column users.name is 'Имя пользователя';
comment on column users.login is 'Логин для входа (уникальный)';
comment on column users.password is 'Пароль';