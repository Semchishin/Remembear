alter table users
add column role varchar(64);

comment on column users.role is 'Роль пользователя';