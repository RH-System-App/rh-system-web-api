CREATE TABLE RH_GROUP
(
    GROUP_ID   RAW(16)            NOT NULL,
    GROUP_NAME VARCHAR2(120 CHAR) NOT NULL,
    CONSTRAINT PK_GROUP PRIMARY KEY (GROUP_ID)
);


comment on table rh_group is 'Tabela de grupos';
comment on column rh_group.group_id is 'Identificador do grupo';
comment on column rh_group.group_name is 'Nome do grupo';