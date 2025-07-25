create table rh_group_functionality
(
    group_id           uuid not null,
    functionality_code varchar(50),
    constraint pk_rh_group_functionality primary key (group_id, functionality_code),
    constraint fk_rh_group_functionality_group foreign key (group_id) references rh_group (group_id),
    constraint fk_rh_group_functionality_functionality foreign key (functionality_code) references rh_functionality (code)
);

comment on table rh_group_functionality is 'Tabela para relacionar grupos e funcionalidades';
comment on column rh_group_functionality.group_id is 'Identificador do grupo';
comment on column rh_group_functionality.functionality_code is 'Código da funcionalidade';