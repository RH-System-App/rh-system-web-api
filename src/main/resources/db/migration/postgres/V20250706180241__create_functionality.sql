create table rh_functionality
(
    code        varchar(50)  not null,
    description varchar(200) not null,
    active      boolean      not null default true,
    constraint pk_rh_functionality primary key (code)
);

comment on table rh_functionality is 'Tabela de funcionalidades';

comment on column rh_functionality.code is 'Código da funcionalidade';
comment on column rh_functionality.description is 'Descrição da funcionalidade';
comment on constraint pk_rh_functionality on rh_functionality is 'Chave primaria para funcionalidade';