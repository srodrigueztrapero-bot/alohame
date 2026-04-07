-- Reset database
DROP DATABASE IF EXISTS alohame;
CREATE DATABASE alohame;
USE alohame;

-- (Contenido original completo)

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";

-- Tables

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `nombre` varchar(100),
  `email` varchar(100),
  `password` varchar(255),
  `telefono` varchar(15),
  `fecha_registro` datetime DEFAULT current_timestamp(),
  `tipo_usuario` varchar(20),
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`)
);

CREATE TABLE `propiedades` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `titulo` varchar(150),
  `descripcion` text,
  `precio_noche` decimal(10,2),
  `ubicacion` varchar(150),
  `capacidad` int(11),
  `id_usuario` int(11),
  `fecha_publicacion` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
);

CREATE TABLE `imagenes` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_propiedad` int(11),
  `url` varchar(255),
  PRIMARY KEY (`id`)
);

CREATE TABLE `comentarios` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11),
  `id_propiedad` int(11),
  `comentario` text,
  `puntuacion` int(11),
  `fecha` datetime DEFAULT current_timestamp(),
  PRIMARY KEY (`id`)
);

CREATE TABLE `reservas` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `id_usuario` int(11),
  `id_propiedad` int(11),
  `fecha_inicio` date,
  `fecha_fin` date,
  `estado` enum('pendiente','confirmada','cancelada'),
  PRIMARY KEY (`id`)
);

-- Foreign keys

ALTER TABLE `propiedades`
  ADD CONSTRAINT `fk_prop_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

ALTER TABLE `imagenes`
  ADD CONSTRAINT `fk_img_prop` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`);

ALTER TABLE `comentarios`
  ADD CONSTRAINT `fk_com_user` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `fk_com_prop` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`);

ALTER TABLE `reservas`
  ADD CONSTRAINT `fk_res_user` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `fk_res_prop` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`);

COMMIT;
