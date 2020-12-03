
-- En caso de querer borrar las tablas.
-- drop table id_gen;
-- drop table hibernate_sequence;
-- drop table agent_request;
-- drop table request;
-- drop table user;
-- drop table category;
-- drop table feedback;
-- drop table bounding_type;
-- drop table dependency;

-- Datos iniciales de la base de datos.

insert into bounding_type values ('Administrativo');
insert into bounding_type values ('Profesor');
insert into bounding_type values ('Contratista');
insert into bounding_type values ('Estudiante');
insert into bounding_type values ('Otro');
insert into dependency values ('Departamento de ingeniería de Sistemas e Industrial');
insert into dependency values ('Departamento de ingeniería Civil y Agricola');
insert into dependency values ('Departamento de ingeniería Eléctrica y Electrónica');
insert into dependency values ('Departamento de ingeniería Mecánica y Mecatrónica');
insert into dependency values ('Departamento de ingeniería Química y Ambiental');
insert into dependency values ('Dirección de bienestar');
insert into dependency values ('IEI');
insert into dependency values ('Unidad Administrativa');
insert into dependency values ('Secretaria académica');
insert into dependency values ('Oficina de relaciones exteriores');
insert into dependency values ('Vicedecanatura de investigación y extensión');
insert into dependency values ('Otra');
insert into category values ('Mantenimiento preventivo', 1);
insert into category values ('Mantenimiento correctivo', 1);
insert into category values ('Conceptos técnicos: Baja de equipos', 1);
insert into category values ('Conceptos técnicos: Baja de inventario de equipos', 1);
insert into category values ('Conceptos técnicos: Repotenciación de equipos', 1);
insert into category values ('Conceptos técnicos: Compra de equipos', 1);
insert into category values ('Limpieza', 1);
insert into category values ('Mal Funcionamiento de la impresora y/o escaner', 1);
insert into category values ('Mal funcionamiento del computador', 1);
insert into category values ('Baja de equipos', 1);
insert into category values ('Otros', 1);