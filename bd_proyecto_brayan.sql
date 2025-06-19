
drop database bd_proyecto_brayan;

create database bd_proyecto_brayan;
USE bd_proyecto_brayan;


create table roles(
	id int auto_increment primary key,
    rol varchar(30)
);

insert into roles (rol) values ('administrador');
insert into roles (rol) values ('usuario');

select * from roles;

create table permisos(
	id int auto_increment primary key,
    permiso varchar(50)
);

insert into permisos(permiso) values ('ver');

create table permisos_roles(
	id_rol int,
    id_permiso int,
    foreign key(id_rol) references roles(id)on delete set null,
    foreign key(id_permiso)references permisos(id) on delete set null
);

insert into permisos_roles (id_rol,id_Permiso) values (2,1);

create table usuarios(
	id int auto_increment primary key,
	documento bigint unique,
    nombre varchar(60),
    telefono bigint,
    correo varchar(100) unique,
    contrasenia varchar(30),
    id_rol int,
    foreign key (id_rol) references roles(id)
    on delete set null
);

insert into usuarios (documento,nombre,telefono,correo,contrasenia,id_rol) values (1526352846,'Manuel Villabona',3856312824,'edwin@gmail.com','Edwin1.',1);

create table tipos(
	id int auto_increment not null primary key,
    tipo varchar(30),
    precio_hora decimal(6,2)
);

create table estados_consolas(
	id int auto_increment primary key,
    estado varchar(30)
);

create table consolas(
	id int auto_increment primary key,
    id_tipo int,
    id_estado int,
    foreign key (id_tipo) references tipos(id) on delete set null,
    foreign key (id_estado)references estados_consolas(id)on delete set null
);

create table estados_productos(
	id int auto_increment primary key,
    estado varchar(30)
);

create table productos(
id int auto_increment primary key,
nombre varchar(30),
descripcion text,
precio decimal(7,2),
cantidades_disponibles int,
id_estado_producto int,
foreign key (id_estado_producto)references estados_productos(id) on delete set null
);

create table estados_reservas(
	id int auto_increment primary key,
    estado varchar (30)
);

create table reservas(
	id int auto_increment primary key,
    id_usuario int,
    id_consola int,
    id_estado_reserva int,
    hora_inicio datetime,
    hora_finalizacion datetime,
    foreign key (id_usuario)references usuarios(id) on delete set null,
    foreign key(id_consola)references consolas(id)on delete set null,
    foreign key(id_estado_reserva)references estados_reservas(id) on delete set null
);


create table facturas(
 id int auto_increment primary key,
 id_reserva int,
 total decimal (7,2),
 foreign key (id_reserva)references reservas(id) on delete set null
);

create table consumos(
	id int auto_increment primary key,
	id_reserva int,
    id_producto int,
    cantidad int,
    subtotal decimal(7,2),
    foreign key (id_reserva) references reservas(id)on delete set null,
    foreign key (id_producto)references productos(id)on delete set null
);

create table metodos_pago(
	id int auto_increment primary key,
    metodo_pago varchar(30)
);

create table pagos(
	id int auto_increment primary key,
    id_factura int,
    id_metodo int,
    foreign key (id_factura) references facturas(id)on delete set null,
    foreign key(id_metodo)references metodos_pago(id)on delete set null
);

create table historial(
	id int auto_increment primary key,
    id_reserva int,
    foreign key (id_reserva) references reservas(id) on delete set null
);    
show tables;
select * from usuarios;
SELECT id,documento,nombre,telefono,correo,id_rol FROM usuarios;
