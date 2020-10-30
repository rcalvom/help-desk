use helpdesk;

drop table book_seq;
drop table id_gen;
drop table hibernate_sequence;
drop table agent_request;
drop table request;
drop table user;
drop table category;
drop table feedback;
drop table bounding_type;
drop table dependency;

insert into bounding_type values ("Profesor");
insert into bounding_type values ("Administrativo");
insert into dependency values ("Departamento de ingeniería de sistemas e industrial");
insert into dependency values ("Departamento de ingeniería eléctrica y electrónica");
insert into category values ("Mantenimiento preventivo");
insert into category values ("Mantenimiento correctivo");
insert into category values ("Conceptos técticos: Baja de equipos");
insert into category values ("Conceptos técticos: Baja de inventario de equipos");
insert into category values ("Conceptos técticos: Repotenciación de equipos");
insert into category values ("Conceptos técnicos: Compra de equipos");
insert into category values ("Limpieza");
insert into category values ("Baja de equipos");
insert into user values ("user", 0, 0, "Usuario", "Profesor", "Departamento de ingeniería eléctrica y electrónica");
insert into user values ("agent1", 0, 1, "Agente 1", "Administrativo", "Departamento de ingeniería de sistemas e industrial");
insert into user values ("agent2", 0, 1, "Agente 2", "Administrativo", "Departamento de ingeniería eléctrica y electrónica");
insert into user values ("agent3", 0, 1, "Agente 3", "Administrativo", "Departamento de ingeniería eléctrica y electrónica");
insert into user values ("admin", 1, 0, "Administrador", "Administrativo", "Departamento de ingeniería de sistemas e industrial");