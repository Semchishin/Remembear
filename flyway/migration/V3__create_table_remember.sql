--create table reminds
create table if not exists reminds
(
    remindId bigint primary key,
    userId bigint,
    title varchar(64),
    description varchar(64),
    createdAt timestamp,
    remindAt  timestamp,
    foreign key (userId) references users(id)
);

comment on table reminds is 'Таблица напоминаний';
comment on column reminds.userId is 'Уникальный идентификатор пользователя';
comment on column reminds.title is 'Название напоминания';
comment on column reminds.description is 'Описание напоминания';
comment on column reminds.remindAt is 'Время для напоминания';
comment on column reminds.createdAt is 'Дата создания напоминания'