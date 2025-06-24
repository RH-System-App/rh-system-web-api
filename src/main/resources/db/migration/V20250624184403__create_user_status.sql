alter table rh_user
    add column user_status varchar(255) default 'ACTIVE';

comment on column rh_user.user_status IS 'Coluna para armazenar o status do usuário';