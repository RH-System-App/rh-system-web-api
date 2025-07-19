CREATE TABLE RH_GROUP_FUNCTIONALITY
(
    GROUP_ID           RAW(16)           NOT NULL,
    FUNCTIONALITY_CODE VARCHAR2(50 CHAR) NOT NULL,
    CONSTRAINT PK_RH_GROUP_FUNCTIONALITY PRIMARY KEY (GROUP_ID, FUNCTIONALITY_CODE),
    CONSTRAINT FK_GROUP_FUNCTIONALITY_GROUP FOREIGN KEY (GROUP_ID) REFERENCES RH_GROUP (GROUP_ID),
    CONSTRAINT FK_GROUP_FUNCTIONALITY_FUNCTIONALITY FOREIGN KEY (FUNCTIONALITY_CODE) REFERENCES RH_FUNCTIONALITY (CODE)
);


comment on table rh_group_functionality is 'Tabela para relacionar grupos e funcionalidades';
comment on column rh_group_functionality.group_id is 'Identificador do grupo';
comment on column rh_group_functionality.functionality_code is 'Código da funcionalidade';