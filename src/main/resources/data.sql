
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

-- -- Vinculaciones.
insert into bounding_type (name)
select * from (select 'Administrativo') as tmp
where not exists (
        select name from bounding_type where name = 'Administrativo'
) limit 1;

insert into bounding_type (name)
select * from (select 'Profesor') as tmp
where not exists (
        select name from bounding_type where name = 'Profesor'
) limit 1;

insert into bounding_type (name)
select * from (select 'Contratista') as tmp
where not exists (
        select name from bounding_type where name = 'Contratista'
) limit 1;

insert into bounding_type (name)
select * from (select 'Estudiante') as tmp
where not exists (
        select name from bounding_type where name = 'Estudiante'
) limit 1;

insert into bounding_type (name)
select * from (select 'Otro') as tmp
where not exists (
        select name from bounding_type where name = 'Otro'
) limit 1;

-- -- Dependencias.

insert into dependency (name)
select * from (select 'Departamento de ingeniería de Sistemas e Industrial') as tmp
where not exists (
        select name from dependency where name = 'Departamento de ingeniería de Sistemas e Industrial'
) limit 1;

insert into dependency (name)
select * from (select 'Departamento de ingeniería Civil y Agricola') as tmp
where not exists (
        select name from dependency where name = 'Departamento de ingeniería Civil y Agricola'
) limit 1;

insert into dependency (name)
select * from (select 'Departamento de ingeniería Eléctrica y Electrónica') as tmp
where not exists (
        select name from dependency where name = 'Departamento de ingeniería Eléctrica y Electrónica'
) limit 1;

insert into dependency (name)
select * from (select 'Departamento de ingeniería Mecánica y Mecatrónica') as tmp
where not exists (
        select name from dependency where name = 'Departamento de ingeniería Mecánica y Mecatrónica'
) limit 1;

insert into dependency (name)
select * from (select 'Departamento de ingeniería Química y Ambiental') as tmp
where not exists (
        select name from dependency where name = 'Departamento de ingeniería Química y Ambiental'
) limit 1;

insert into dependency (name)
select * from (select 'Dirección de bienestar') as tmp
where not exists (
        select name from dependency where name = 'Dirección de bienestar'
) limit 1;

insert into dependency (name)
select * from (select 'IEI') as tmp
where not exists (
        select name from dependency where name = 'IEI'
) limit 1;

insert into dependency (name)
select * from (select 'Unidad Administrativa') as tmp
where not exists (
        select name from dependency where name = 'Unidad Administrativa'
) limit 1;

insert into dependency (name)
select * from (select 'Secretaria académica') as tmp
where not exists (
        select name from dependency where name = 'Secretaria académica'
) limit 1;

insert into dependency (name)
select * from (select 'Oficina de relaciones exteriores') as tmp
where not exists (
        select name from dependency where name = 'Oficina de relaciones exteriores'
) limit 1;

insert into dependency (name)
select * from (select 'Vicedecanatura de investigación y extensión') as tmp
where not exists (
        select name from dependency where name = 'Vicedecanatura de investigación y extensión'
) limit 1;

insert into dependency (name)
select * from (select 'Otra') as tmp
where not exists (
        select name from dependency where name = 'Otra'
) limit 1;

-- -- Categorías

insert into category (name, is_active)
select * from (select 'Mantenimiento preventivo', 1) as tmp
where not exists (
        select name from category where name = 'Mantenimiento preventivo'
) limit 1;

insert into category (name, is_active)
select * from (select 'Mantenimiento correctivo', 1) as tmp
where not exists (
        select name from category where name = 'Mantenimiento correctivo'
) limit 1;

insert into category (name, is_active)
select * from (select 'Conceptos técnicos: Baja de equipos', 1) as tmp
where not exists (
        select name from category where name = 'Conceptos técnicos: Baja de equipos'
) limit 1;

insert into category (name, is_active)
select * from (select 'Conceptos técnicos: Baja de inventario de equipos', 1) as tmp
where not exists (
        select name from category where name = 'Conceptos técnicos: Baja de inventario de equipos'
) limit 1;

insert into category (name, is_active)
select * from (select 'Conceptos técnicos: Repotenciación de equipos', 1) as tmp
where not exists (
        select name from category where name = 'Conceptos técnicos: Repotenciación de equipos'
) limit 1;

insert into category (name, is_active)
select * from (select 'Conceptos técnicos: Compra de equipos', 1) as tmp
where not exists (
        select name from category where name = 'Conceptos técnicos: Compra de equipos'
) limit 1;

insert into category (name, is_active)
select * from (select 'Limpieza', 1) as tmp
where not exists (
        select name from category where name = 'Limpieza'
) limit 1;

insert into category (name, is_active)
select * from (select 'Mal Funcionamiento de la impresora y/o escaner', 1) as tmp
where not exists (
        select name from category where name = 'Mal Funcionamiento de la impresora y/o escaner'
) limit 1;

insert into category (name, is_active)
select * from (select 'Mal funcionamiento del computador', 1) as tmp
where not exists (
        select name from category where name = 'Mal funcionamiento del computador'
) limit 1;

insert into category (name, is_active)
select * from (select 'Baja de equipos', 1) as tmp
where not exists (
        select name from category where name = 'Baja de equipos'
) limit 1;

insert into category (name, is_active)
select * from (select 'Otros', 1) as tmp
where not exists (
        select name from category where name = 'Otros'
) limit 1;

-- create trigger unique_admin before update on user for each row
-- begin
--     if (select count(*) from user where is_administrator = 1) > 0 then
--         signal SQLSTATE '45000';
--     end if;
-- end;
