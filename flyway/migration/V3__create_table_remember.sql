--create table reminds
create table if not exists reminds
(
    remind_id bigint primary key,
    user_id bigint,
    title varchar(64),
    description varchar(64),
    created_at timestamp,
    remind_at  timestamp,
    foreign key (user_id) references users(id)
);

comment on table reminds is 'Таблица напоминаний';
comment on column reminds.user_id is 'Уникальный идентификатор пользователя';
comment on column reminds.title is 'Название напоминания';
comment on column reminds.description is 'Описание напоминания';
comment on column reminds.remind_at is 'Время для напоминания';
comment on column reminds.created_at is 'Дата создания напоминания'