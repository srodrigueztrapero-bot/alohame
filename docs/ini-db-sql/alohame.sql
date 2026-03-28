DROP DATABASE IF EXISTS alohame;
CREATE DATABASE alohame;
USE alohame;

-- USUARIOS
CREATE TABLE usuarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    password VARCHAR(255),
    telefono VARCHAR(15),
    fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
    tipo_usuario VARCHAR(20)
);

-- PROPIEDADES
CREATE TABLE propiedades (
    id INT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(150),
    descripcion TEXT,
    precio_noche DECIMAL(10,2),
    ubicacion VARCHAR(150),
    capacidad INT,
    id_usuario INT,
    fecha_publicacion DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id)
);

-- RESERVAS
CREATE TABLE reservas (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_propiedad INT,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado ENUM('pendiente','confirmada','cancelada'),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_propiedad) REFERENCES propiedades(id)
);

-- COMENTARIOS
CREATE TABLE comentarios (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    id_propiedad INT,
    comentario TEXT,
    puntuacion INT CHECK (puntuacion BETWEEN 1 AND 5),
    fecha DATETIME DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id),
    FOREIGN KEY (id_propiedad) REFERENCES propiedades(id)
);

-- IMAGENES (CLAVE)
CREATE TABLE imagenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_propiedad INT,
    url VARCHAR(255),
    FOREIGN KEY (id_propiedad) REFERENCES propiedades(id)
);

-- DATOS
INSERT INTO usuarios (nombre, email, password, telefono, tipo_usuario)
VALUES 
('Julio Romero', 'julio@gmail.com', '11589', '611558445', 'cliente'),
('Marisol Torrijos', 'marisole@gmail.com', '44544', '655998990', 'cliente');

INSERT INTO propiedades (titulo, descripcion, precio_noche, ubicacion, capacidad, id_usuario)
VALUES 
('Apartamento en la playa', 'Muy bonito y cerca del mar', 80.00, 'Valencia', 4, 1),
('Casa rural', 'Ideal para desconectar', 120.00, 'Asturias', 6, 2);

-- IMÁGENES (8 para propiedad 1)
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia1.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia2.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia3.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia4.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia5.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia6.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia7.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (1, '/images/valencia8.jpg');

-- RESERVA (SIN DUPLICADOS)
INSERT INTO reservas (id_usuario, id_propiedad, fecha_inicio, fecha_fin, estado)
VALUES (2, 1, '2026-04-01', '2026-04-05', 'confirmada');

-- COMENTARIO
INSERT INTO comentarios (id_usuario, id_propiedad, comentario, puntuacion)
VALUES (2, 1, 'Muy buena experiencia', 5);

INSERT INTO propiedades (titulo, descripcion, precio_noche, ubicacion, capacidad, id_usuario)
VALUES 
('Casa rural', 'Ideal para desconectar', 120.00, 'Asturias', 6, 2);
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo1.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo2.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo3.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo4.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo5.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo6.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo7.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo8.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo9.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (2, '/images/oviedo10.jpg');

INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid1.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid2.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid3.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid4.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid5.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid6.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid7.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid8.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid9.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/madrid10.jpg');

INSERT INTO usuarios (nombre, email, password, telefono, tipo_usuario)
VALUES 
('Daniel Garcia', 'danituri@gmail.com', '332445', '655455621','propietario'),
('Laura Sanchez', 'laura@gmail.com', '553220', '600111222', 'cliente'),
('Carlos Ruiz', 'carlos@gmail.com', '899422', '600222333', 'propietario'),
('Marta López', 'marta@gmail.com', '448443', '600333444', 'cliente'),
('Javier Gómez', 'javier@gmail.com', '800222', '600444555', 'propietario'),
('Elena Torres', 'elena@gmail.com', '112233', '600555666', 'cliente'),
('David Moreno', 'david@gmail.com', '005005', '600666777', 'cliente'),
('Paula Navarro', 'paula@gmail.com', '990800', '600777888', 'propietario');

INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/badajoz1.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/badajoz2.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/badajoz3.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/badajoz4.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/badajoz5.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/badajoz6.jpg');
INSERT INTO imagenes (id_propiedad, url) VALUES (3, '/images/badajoz7.jpg');

CREATE TABLE tipos_usuario (
    id INT PRIMARY KEY AUTO_INCREMENT,
    nombre VARCHAR(50)
);

INSERT INTO tipos_usuario (nombre) VALUES ('admin'), ('usuario'), ('propietario');
ALTER TABLE usuarios ADD id_tipo INT;
UPDATE usuarios SET id_tipo = 1 WHERE tipo_usuario = 'admin';