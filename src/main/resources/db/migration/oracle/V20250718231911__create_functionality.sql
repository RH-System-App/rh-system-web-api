CREATE TABLE RH_FUNCTIONALITY
(
    CODE        VARCHAR2(50 CHAR)   NOT NULL,
    DESCRIPTION VARCHAR2(255 CHAR),
    ACTIVE      NUMBER(1) DEFAULT 1 NOT NULL,
    CONSTRAINT PK_RH_FUNCTIONALITY PRIMARY KEY (CODE)
);


comment on table rh_functionality is 'Tabela de funcionalidades';

comment on column rh_functionality.code is 'Código da funcionalidade';
comment on column rh_functionality.description is 'Descrição da funcionalidade';
comment on column rh_functionality.active is 'Define se a funcionalidade esta ativa';