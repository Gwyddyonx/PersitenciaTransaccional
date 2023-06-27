drop sequence sec_paises;
drop sequence sec_departamento;
drop sequence sec_localiz;
drop sequence sec_historico;
drop sequence sec_empleados;
drop sequence sec_cargos;
drop sequence sec_ciudades;

drop table paises;
drop table departamentos;
drop table localizaciones;
drop table historico;
drop table empleados;
drop table cargos;
drop table ciudades;


CREATE SEQUENCE sec_paises
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 200
NOCYCLE;

CREATE TABLE paises (
pais_ID NUMBER(11) DEFAULT sec_paises.nextval,
pais_nombre VARCHAR2(50) NOT NULL,
CONSTRAINT pais_ID_PK PRIMARY KEY (pais_ID)
);

CREATE SEQUENCE sec_departamento
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 200
NOCYCLE;

CREATE TABLE departamentos (
dpto_ID NUMBER(11) DEFAULT sec_departamento.nextval,
dpto_nombre VARCHAR2(100) NOT NULL,
CONSTRAINT dpto_ID_PK PRIMARY KEY (dpto_ID)
);

CREATE SEQUENCE sec_localiz
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 200
NOCYCLE;

CREATE TABLE localizaciones (
localiz_ID NUMBER(11) DEFAULT sec_localiz.nextval,
localiz_direccion VARCHAR2(100) NOT NULL,
localiz_ciuddad_ID NUMBER(11) NOT NULL,
CONSTRAINT localiz_ID_PK PRIMARY KEY (localiz_ID),
CONSTRAINT localizacion_fk FOREIGN KEY(localiz_ciuddad_ID)
REFERENCES ciudades (ciud_ID)
);

CREATE SEQUENCE sec_historico
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 200
NOCYCLE;

CREATE TABLE historico (
emphist_ID INT DEFAULT sec_historico.nextval,
emphist_fecha_retiro DATE NOT NULL,
emphist_cargo_ID INT NOT NULL,
emphist_dpto_ID INT NOT NULL,
CONSTRAINT emphist_ID_PK PRIMARY KEY (emphist_ID),
CONSTRAINT emphist_cargo_ID_fk FOREIGN KEY(emphist_cargo_ID)
REFERENCES cargos (cargo_ID),
CONSTRAINT emphist_dpto_ID_fk FOREIGN KEY(emphist_dpto_ID)
REFERENCES departamentos (dpto_ID)
);

CREATE SEQUENCE sec_empleados
START WITH 1
INCREMENT BY 1
MINVALUE 1
MAXVALUE 200
NOCYCLE;

CREATE TABLE empleados (
empl_ID INT DEFAULT sec_empleados.nextval,
empl_primer_nombre VARCHAR2(100) NOT NULL,
empl_segundo_nombre VARCHAR2(100) NOT NULL,
empl_email VARCHAR2(100) NOT NULL,
empl_fecha_nac DATE NOT NULL,
empl_sueldo NUMBER NOT NULL,
empl_comision INT NOT NULL,
empl_cargo_ID INT NOT NULL,
empl_gerente_ID INT NOT NULL,
empl_dpto_ID INT NOT NULL,
CONSTRAINT empl_ID_PK PRIMARY KEY (empl_ID),
CONSTRAINT empl_cargo_ID_fk FOREIGN KEY(empl_cargo_ID)
REFERENCES cargos (cargo_ID),
--CONSTRAINT empl_gerente_ID_fk FOREIGN KEY(empl_gerente_ID)
--REFERENCES empleados (empl_ID),
CONSTRAINT empl_dpto_ID_fk FOREIGN KEY(empl_dpto_ID)
REFERENCES departamentos(dpto_ID)
);

create sequence sec_cargos
start with 1
increment by 1
minvalue 1
maxvalue 200
NOCYCLE;

CREATE TABLE cargos (
	cargo_ID numeric(11) DEFAULT sec_cargos.nextval,
	cargo_nombre VARCHAR(100) NOT NULL,
	cargo_sueldo_minimo number NOT NULL,
	cargo_sueldo_maximo number NOT NULL,
	CONSTRAINT cargo_ID_PK PRIMARY KEY (cargo_ID)
);

create sequence sec_ciudades
start with 1
increment by 1
minvalue 1
maxvalue 200
NOCYCLE;

CREATE TABLE ciudades (
	ciud_ID NUMERIC(11) DEFAULT sec_ciudades.nextval,
	ciud_nombre varchar(50) NOT NULL,
	ciud_pais_ID NUMBER(11) NOT NULL,
	CONSTRAINT ciud_ID_PK PRIMARY KEY (ciud_ID),
	CONSTRAINT ciudades_fk FOREIGN KEY(ciud_pais_ID)
	REFERENCES paises (pais_ID)
); 

GRANT ALL ON ADMIN.paises TO POLI;
create public synonym paises for ADMIN.paises;

GRANT ALL ON ADMIN.departamentos TO POLI;
create public synonym departamentos for ADMIN.departamentos;

GRANT ALL ON ADMIN.localizaziones TO POLI;
create public synonym localizaziones for ADMIN.localizaziones;

GRANT ALL ON ADMIN.historico TO POLI;
create public synonym historico for ADMIN.historico;

GRANT ALL ON ADMIN.empleados TO POLI;
create public synonym empleados for ADMIN.empleados;

GRANT ALL ON ADMIN.cargos TO POLI;
create public synonym cargos for ADMIN.cargos;

GRANT ALL ON ADMIN.ciudades TO POLI;
create public synonym ciudades for ADMIN.ciudades;