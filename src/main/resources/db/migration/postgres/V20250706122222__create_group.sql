create table rh_group
(
    group_id   uuid         not null,
    group_name varchar(120) not null,
    constraint pk_group primary key (group_id)
);

comment on table rh_group is 'Tabela de grupos';
comment on column rh_group.group_id is 'Identificador do grupo';
comment on column rh_group.group_name is 'Nome do grupo';

comment on constraint pk_group on rh_group is 'Chave primária da tabela de grupos';
