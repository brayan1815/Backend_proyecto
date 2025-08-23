DROP DATABASE IF EXISTS bd_proyecto_brayan;
CREATE DATABASE bd_proyecto_brayan;
USE bd_proyecto_brayan;

-- ROLES Y PERMISOS
CREATE TABLE roles (
    id INT AUTO_INCREMENT PRIMARY KEY,
    rol VARCHAR(30)
);

INSERT INTO roles (rol) VALUES ('administrador');
INSERT INTO roles (rol) VALUES ('usuario');

CREATE TABLE permisos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    permiso VARCHAR(50)
);

-- Permisos Consolas
INSERT INTO permisos (permiso) VALUES
('consolas.index'),
('consolas.crear'),
('consolas.editar'),
('consolas.eliminar');
-- Permisos Consumos
INSERT INTO permisos(permiso) VALUES
('consumos.index'),
('consumos.crear'),
('consumos.editar'),
('consumos.eliminar');
-- Permiso Metodos Pago
INSERT INTO permisos(permiso) VALUES
('metodos.index'),
('metodos.crear'),
('metodos.editar'),
('metodos.eliminar');
-- Permisos Pagos
INSERT INTO permisos(permiso) VALUES
('pagos.crear');
-- Permisos Productos
INSERT INTO permisos(permiso) VALUES
('productos.index'),
('productos.crear'),
('productos.editar'),
('productos.eliminar');
-- Permisos Reservas
INSERT INTO permisos(permiso) VALUES
('reservas.index'),
('reservas.crear'),
('reservas.eliminar');
-- Permisos tipos
INSERT INTO permisos(permiso) VALUES
('tipos.index'),
('tipos.crear'),
('tipos.editar'),
('tipos.eliminar');
-- Permisos Usuarios
INSERT INTO permisos(permiso) VALUES
('usuarios.index'),
('usuarios.crear'),
('usuarios.editar'),
('usuarios.eliminar');
-- Permisos Historial
INSERT INTO permisos(permiso) VALUES
('historial.index');
-- Permisos Roles
INSERT INTO permisos(permiso) VALUES
('roles.index');
-- Permisos pagos
INSERT INTO permisos(permiso) VALUES
('pagos.index');

select * from permisos;


CREATE TABLE permisos_roles (
    id_rol INT,
    id_permiso INT,
    FOREIGN KEY (id_rol) REFERENCES roles(id) ON DELETE SET NULL,
    FOREIGN KEY (id_permiso) REFERENCES permisos(id) ON DELETE SET NULL
);
-- Permisos Administrador
INSERT INTO permisos_roles(id_rol,id_permiso) VALUES
(1,1),(1,2),(1,3),(1,4),
(1,5),(1,6),(1,7),(1,8),
(1,9),(1,10),(1,11),(1,12),
(1,13),(1,14),(1,15),(1,16),(1,17),
(1,18),(1,19),(1,20),
(1,21),(1,22),(1,23),(1,24),
(1,25),(1,26),(1,27),(1,28),
(1,29),(1,30),(1,31);


select * from permisos;


-- Permisos Usuario
INSERT INTO permisos_roles(id_rol,id_permiso) VALUES
(2,1),
(2,5),
(2,14),
(2,18),(2,19),(2,20),(2,21);

select * from permisos;

CREATE TABLE estados_usuarios(
	id int auto_increment primary key,
    estado varchar(50)
);

INSERT INTO estados_usuarios(estado)VALUES ('Activo');
INSERT INTO estados_usuarios(estado)VALUES ('Inactivo');

-- USUARIOS
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    documento BIGINT UNIQUE,
    nombre VARCHAR(60),
    telefono BIGINT,
    correo VARCHAR(100) UNIQUE,
    contrasenia VARCHAR(100),
    id_rol INT,
    id_estado INT,
    FOREIGN KEY (id_rol) REFERENCES roles(id) ON DELETE SET NULL,
    FOREIGN KEY (id_estado)REFERENCES estados_usuarios(id) ON DELETE SET NULL
);

-- IMÁGENES
CREATE TABLE imagenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    ruta TEXT
);

CREATE TABLE estados_tipos(
id INT AUTO_INCREMENT PRIMARY KEY,
estado VARCHAR(50)
);

INSERT INTO estados_tipos(estado) VALUES ('Activo');
INSERT INTO estados_tipos(estado) VALUES ('Inactivo');

-- TIPOS DE CONSOLA
CREATE TABLE tipos (
    id INT AUTO_INCREMENT NOT NULL PRIMARY KEY,
    tipo VARCHAR(30),
    precio_hora DECIMAL(6,2),
    id_estado_tipo INT,
    FOREIGN KEY (id_estado_tipo) REFERENCES estados_tipos(id)
);

-- ESTADOS DE CONSOLAS
CREATE TABLE estados_consolas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(30)
);

INSERT INTO estados_consolas(estado) VALUES ('disponible');
INSERT INTO estados_consolas(estado) VALUES ('Inactivo');

-- CONSOLAS
CREATE TABLE consolas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    numero_serie VARCHAR(10) UNIQUE,
    nombre VARCHAR(30),
    descripcion TEXT,
    id_tipo INT,
    id_estado INT,
    id_imagen INT,
    FOREIGN KEY (id_tipo) REFERENCES tipos(id) ON DELETE SET NULL,
    FOREIGN KEY (id_estado) REFERENCES estados_consolas(id) ON DELETE SET NULL,
    FOREIGN KEY (id_imagen) REFERENCES imagenes(id) ON DELETE SET NULL
);

-- ESTADOS DE PRODUCTOS
CREATE TABLE estados_productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(30)
);

INSERT INTO estados_productos(estado) VALUES ('disponible');
INSERT INTO estados_productos(estado) VALUES ('agotado');
INSERT INTO estados_productos(estado) VALUES ('inactivo');

-- PRODUCTOS
CREATE TABLE productos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(30),
    descripcion TEXT,
    precio DECIMAL(7,2),
    cantidades_disponibles INT,
    id_estado_producto INT,
    id_imagen INT,
    FOREIGN KEY (id_imagen) REFERENCES imagenes(id) ON DELETE SET NULL,
    FOREIGN KEY (id_estado_producto) REFERENCES estados_productos(id) ON DELETE SET NULL
);

-- ESTADOS DE RESERVAS
CREATE TABLE estados_reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    estado VARCHAR(30)
);

INSERT INTO estados_reservas (estado) VALUES
('pendiente'), ('en proceso'), ('terminada'), ('cobrada');

-- RESERVAS
CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_consola INT,
    id_estado_reserva INT,
    hora_inicio DATETIME,
    hora_finalizacion DATETIME,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id) ON DELETE SET NULL,
    FOREIGN KEY (id_consola) REFERENCES consolas(id) ON DELETE SET NULL,
    FOREIGN KEY (id_estado_reserva) REFERENCES estados_reservas(id) ON DELETE SET NULL
);

-- FACTURAS (modificada)
CREATE TABLE facturas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT UNIQUE,
    minutos INT,
    subtotal_consumos decimal(7,2),
    subtotal_consola decimal(7,2),
    total DECIMAL(7,2),
    FOREIGN KEY (id_reserva) REFERENCES reservas(id) ON DELETE SET NULL
);

-- CONSUMOS TEMPORALES (NO CAMBIAR)
CREATE TABLE consumos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva INT,
    id_producto INT,
    cantidad INT,
    subtotal DECIMAL(7,2),
    FOREIGN KEY (id_reserva) REFERENCES reservas(id) ON DELETE SET NULL,
    FOREIGN KEY (id_producto) REFERENCES productos(id) ON DELETE SET NULL
);

-- MÉTODOS DE PAGO
CREATE TABLE metodos_pago (
    id INT AUTO_INCREMENT PRIMARY KEY,
    metodo_pago VARCHAR(30)
);

INSERT INTO metodos_pago(metodo_pago) VALUES ('Tranferencia'), ('Nequi'), ('Efectivo');

-- PAGOS
CREATE TABLE pagos (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_factura INT,
    id_metodo INT,
    FOREIGN KEY (id_factura) REFERENCES facturas(id) ON DELETE SET NULL,
    FOREIGN KEY (id_metodo) REFERENCES metodos_pago(id) ON DELETE SET NULL
);

-- USUARIO DE PRUEBA
INSERT INTO usuarios(documento,nombre,telefono,correo,contrasenia,id_rol,id_estado) VALUES
(1096512824,'Brayan Fernandez',3112114081,'brayan@gmail.com','$2a$10$XWDD07M527ov2C1.R/wYnedQuxhK2f5ACmUTysVXAE0Az752TKQqq',1,1); -- Brayan123.

select * from facturas;
SELECT * FROM consolas WHERE numero_serie='asd123';

select * from tipos;
SELECT SUM(subtotal) FROM consumos WHERE id_reserva = 3;

use bd_proyecto_brayan;
-- RESERVA EN PROCESO:
INSERT INTO reservas (id_usuario, id_consola,id_estado_reserva,hora_inicio,hora_finalizacion)
VALUES (1,1,1,NOW(), DATE_ADD(NOW(), INTERVAL 30 MINUTE));

-- RESERVA FIANLIZADA:
-- INSERT INTO reservas (id_usuario, id_consola,id_estado_reserva,hora_inicio,hora_finalizacion)
-- VALUES (1,1,1, DATE_SUB(NOW(), INTERVAL 30 MINUTE), DATE_SUB(NOW(), INTERVAL 5 MINUTE));

    use bd_proyecto_brayan;
SELECT * FROM permisos;

UPDATE reservas SET hora_inicio = '2025-08-22 17:00:00', hora_finalizacion='2025-08-22 17:10:00' WHERE id=2;

