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

-- IMAGENES
CREATE TABLE imagenes (
    id INT AUTO_INCREMENT PRIMARY KEY,
    id_propiedad INT,
    url VARCHAR(255),
    FOREIGN KEY (id_propiedad) REFERENCES propiedades(id)
);

-- 🔥 USUARIOS (PRIMERO)
INSERT INTO usuarios (nombre, email, password, telefono, tipo_usuario)
VALUES 
('Admin Alohame', 'admin@alohame.com', '0001', '600000000', 'admin'),
('Julio Romero', 'julio@gmail.com', '11589', '611558445', 'cliente'),
('Marisol Torrijos', 'marisole@gmail.com', '44544', '655998990', 'cliente'),
('Daniel Garcia', 'danituri@gmail.com', '332445', '655455621','propietario'),
('Carlos Ruiz', 'carlos@gmail.com', '899422', '600222333', 'propietario'),
('Paula Navarro', 'paula@gmail.com', '990800', '600777888', 'propietario');

-- 🔥 PROPIEDADES (AHORA SÍ FUNCIONA)
INSERT INTO propiedades 
(titulo, descripcion, precio_noche, ubicacion, capacidad, id_usuario) 
VALUES 
('Apartamento en la playa', 'Muy bonito y cerca del mar', 80.00, 'Valencia', 4, 4),
('Casa rural', 'Ideal para desconectar', 120.00, 'Asturias', 6, 4),
('Piso en el centro', 'Cerca de la Almudena', 220.00, 'Madrid', 2, 5),
('Apartamento centro', 'Muy bonito y céntrico', 280.00, 'Barcelona', 4, 5),
('Casa estudiantes', 'Perfecto para estudiantes', 120.00, 'Salamanca', 2, 6),
('Estudio barato', 'Pequeño pero acogedor', 250.00, 'Malaga', 2, 6),
('Estudio en el centro', 'Con todas las comodidades', 150.00, 'Badajoz', 2, 6);

-- 🔥 IMÁGENES (UNA MÍNIMA POR PROPIEDAD)
INSERT INTO imagenes (id_propiedad, url) VALUES 
(1, 'images/valencia1.jpg'),
(2, 'images/oviedo1.jpg'),
(3, 'images/madrid1.jpg'),
(4, 'images/barcelona1.jpg'),
(5, 'images/salamanca1.jpg'),
(6, 'images/malaga1.jpg'),
(7, 'images/badajoz1.jpg');

-- 🔥 RESERVA
INSERT INTO reservas (id_usuario, id_propiedad, fecha_inicio, fecha_fin, estado)
VALUES (2, 1, '2026-04-01', '2026-04-05', 'confirmada');

-- 🔥 COMENTARIO
INSERT INTO comentarios (id_usuario, id_propiedad, comentario, puntuacion)
VALUES (2, 1, 'Muy buena experiencia', 5);