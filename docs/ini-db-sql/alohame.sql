-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 07-04-2026 a las 19:09:22
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `alohame`
--
DROP DATABASE IF EXISTS alohame;
CREATE DATABASE alohame;
USE alohame;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comentarios`
--

CREATE TABLE `comentarios` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_propiedad` int(11) DEFAULT NULL,
  `comentario` text DEFAULT NULL,
  `puntuacion` int(11) DEFAULT NULL CHECK (`puntuacion` between 1 and 5),
  `fecha` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comentarios`
--

INSERT INTO `comentarios` (`id`, `id_usuario`, `id_propiedad`, `comentario`, `puntuacion`, `fecha`) VALUES
(1, 2, 1, 'Muy buena experiencia', 5, '2026-03-29 18:53:22');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `imagenes`
--

CREATE TABLE `imagenes` (
  `id` int(11) NOT NULL,
  `id_propiedad` int(11) DEFAULT NULL,
  `url` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `imagenes`
--

INSERT INTO `imagenes` (`id`, `id_propiedad`, `url`) VALUES
(1, 1, 'images/valencia1.jpg'),
(2, 2, 'images/oviedo1.jpg'),
(3, 3, 'images/madrid1.jpg'),
(4, 4, 'images/barcelona1.jpg'),
(5, 5, 'images/salamanca1.jpg'),
(6, 6, 'images/malaga1.jpg'),
(7, 7, 'images/badajoz1.jpg'),
(8, 8, 'images/galicia1.jpg'),
(9, 9, 'images/avila1.jpg'),
(10, 1, 'images/valencia2.jpg'),
(11, 1, 'images/valencia3.jpg'),
(12, 1, 'images/valencia4.jpg'),
(13, 1, 'images/valencia5.jpg'),
(14, 1, 'images/valencia6.jpg'),
(15, 1, 'images/valencia7.jpg'),
(16, 1, 'images/valencia8.jpg'),
(17, 2, 'images/oviedo2.jpg'),
(18, 2, 'images/oviedo3.jpg'),
(19, 2, 'images/oviedo4.jpg'),
(20, 2, 'images/oviedo5.jpg'),
(21, 2, 'images/oviedo6.jpg'),
(22, 2, 'images/oviedo7.jpg'),
(23, 2, 'images/oviedo8.jpg'),
(24, 2, 'images/oviedo9.jpg'),
(25, 2, 'images/oviedo10.jpg'),
(26, 3, 'images/madrid2.jpg'),
(27, 3, 'images/madrid3.jpg'),
(28, 3, 'images/madrid4.jpg'),
(29, 3, 'images/madrid5.jpg'),
(30, 3, 'images/madrid6.jpg'),
(31, 3, 'images/madrid7.jpg'),
(32, 3, 'images/madrid8.jpg'),
(33, 3, 'images/madrid9.jpg'),
(34, 3, 'images/madrid10.jpg'),
(35, 4, 'images/barcelona2.jpg'),
(36, 4, 'images/barcelona3.jpg'),
(37, 4, 'images/barcelona4.jpg'),
(38, 4, 'images/barcelona5.jpg'),
(39, 4, 'images/barcelona6.jpg'),
(40, 4, 'images/barcelona7.jpg'),
(41, 4, 'images/barcelona8.jpg'),
(42, 4, 'images/barcelona9.jpg'),
(43, 4, 'images/barcelona10.jpg'),
(44, 5, 'images/salamanca2.jpg'),
(45, 5, 'images/salamanca3.jpg'),
(46, 5, 'images/salamanca4.jpg'),
(47, 5, 'images/salamanca5.jpg'),
(48, 5, 'images/salamanca6.jpg'),
(49, 5, 'images/salamanca7.jpg'),
(50, 5, 'images/salamanca8.jpg'),
(51, 6, 'images/malaga2.jpg'),
(52, 6, 'images/malaga3.jpg'),
(53, 6, 'images/malaga4.jpg'),
(54, 6, 'images/malaga5.jpg'),
(55, 6, 'images/malaga6.jpg'),
(56, 6, 'images/malaga7.jpg'),
(57, 6, 'images/malaga8.jpg'),
(58, 6, 'images/malaga9.jpg'),
(59, 6, 'images/malaga10.jpg'),
(60, 6, 'images/malaga11.jpg'),
(61, 7, 'images/badajoz2.jpg'),
(62, 7, 'images/badajoz3.jpg'),
(63, 7, 'images/badajoz4.jpg'),
(64, 7, 'images/badajoz5.jpg'),
(65, 7, 'images/badajoz6.jpg'),
(66, 7, 'images/badajoz7.jpg'),
(76, 9, 'images/avila2.jpg'),
(77, 9, 'images/avila3.jpg'),
(78, 9, 'images/avila4.jpg'),
(79, 9, 'images/avila5.jpg'),
(80, 9, 'images/avila6.jpg'),
(81, 9, 'images/avila7.jpg'),
(82, 9, 'images/avila8.jpg'),
(83, 9, 'images/avila9.jpg'),
(93, 8, 'images/galicia2.jpg'),
(94, 8, 'images/galicia3.jpg'),
(95, 8, 'images/galicia4.jpg'),
(96, 8, 'images/galicia5.jpg'),
(97, 8, 'images/galicia6.jpg'),
(98, 8, 'images/galicia7.jpg'),
(99, 8, 'images/galicia8.jpg'),
(100, 8, 'images/galicia9.jpg'),
(101, 10, 'images/Madrid2_1.jpg'),
(102, 10, 'images/Madrid2_2.jpg'),
(103, 10, 'images/Madrid2_3.jpg'),
(104, 10, 'images/Madrid2_4.jpg'),
(105, 10, 'images/Madrid2_5.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `propiedades`
--

CREATE TABLE `propiedades` (
  `id` int(11) NOT NULL,
  `titulo` varchar(150) DEFAULT NULL,
  `descripcion` text DEFAULT NULL,
  `precio_noche` decimal(10,2) DEFAULT NULL,
  `ubicacion` varchar(150) DEFAULT NULL,
  `capacidad` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `fecha_publicacion` datetime DEFAULT current_timestamp()
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `propiedades`
--

INSERT INTO `propiedades` (`id`, `titulo`, `descripcion`, `precio_noche`, `ubicacion`, `capacidad`, `id_usuario`, `fecha_publicacion`) VALUES
(1, 'Apartamento en la playa', 'Muy bonito y cerca del mar', 80.00, 'Valencia', 4, 4, '2026-03-29 18:53:22'),
(2, 'Casa rural', 'Ideal para desconectar', 120.00, 'Asturias', 6, 4, '2026-03-29 18:53:22'),
(3, 'Piso en el centro', 'Cerca de la Almudena', 220.00, 'Madrid', 2, 5, '2026-03-29 18:53:22'),
(4, 'Apartamento centro', 'Muy bonito y céntrico', 280.00, 'Barcelona', 4, 5, '2026-03-29 18:53:22'),
(5, 'Casa estudiantes', 'Perfecto para estudiantes', 120.00, 'Salamanca', 2, 6, '2026-03-29 18:53:22'),
(6, 'Casa de vacaciones', 'Pequeño pero acogedor', 250.00, 'Malaga', 2, 6, '2026-03-29 18:53:22'),
(7, 'Estudio en el centro', 'Con todas las comodidades', 150.00, 'Badajoz', 2, 6, '2026-03-29 18:53:22'),
(8, 'Casa en Galicia', 'Con vistas al mar y muy tranquila', 140.00, 'Galicia', 5, 4, '2026-03-30 17:51:39'),
(9, 'Casa rural en Ávila', 'Perfecta para escapadas', 110.00, 'Ávila', 4, 5, '2026-03-30 17:51:39'),
(10, 'Precioso apartamento en Madrid', 'Alojamiento entero: apto. residencial en Madrid, España', 170.00, 'Madrid', 2, 4, '2026-04-02 17:44:01');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reservas`
--

CREATE TABLE `reservas` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_propiedad` int(11) DEFAULT NULL,
  `fecha_inicio` date DEFAULT NULL,
  `fecha_fin` date DEFAULT NULL,
  `estado` enum('pendiente','confirmada','cancelada') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reservas`
--

INSERT INTO `reservas` (`id`, `id_usuario`, `id_propiedad`, `fecha_inicio`, `fecha_fin`, `estado`) VALUES
(1, 2, 1, '2026-04-01', '2026-04-05', 'confirmada');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `fecha_registro` datetime DEFAULT current_timestamp(),
  `tipo_usuario` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `usuarios`
--

INSERT INTO `usuarios` (`id`, `nombre`, `email`, `password`, `telefono`, `fecha_registro`, `tipo_usuario`) VALUES
(1, 'Admin Alohame', 'admin@alohame.com', '0001', '600000000', '2026-03-29 18:53:22', 'admin'),
(2, 'Julio Romero', 'julio@gmail.com', '11589', '611558445', '2026-03-29 18:53:22', 'cliente'),
(3, 'Marisol Torrijos', 'marisole@gmail.com', '44544', '655998990', '2026-03-29 18:53:22', 'cliente'),
(4, 'Daniel Garcia', 'danituri@gmail.com', '332445', '655455621', '2026-03-29 18:53:22', 'propietario'),
(5, 'Carlos Ruiz', 'carlos@gmail.com', '899422', '600222333', '2026-03-29 18:53:22', 'propietario'),
(6, 'Paula Navarro', 'paula@gmail.com', '990800', '600777888', '2026-03-29 18:53:22', 'propietario');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_propiedad` (`id_propiedad`);

--
-- Indices de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_propiedad` (`id_propiedad`);

--
-- Indices de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_propiedad` (`id_propiedad`);

--
-- Indices de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `comentarios`
--
ALTER TABLE `comentarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=106;

--
-- AUTO_INCREMENT de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `comentarios`
--
ALTER TABLE `comentarios`
  ADD CONSTRAINT `comentarios_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `comentarios_ibfk_2` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`);

--
-- Filtros para la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD CONSTRAINT `imagenes_ibfk_1` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`);

--
-- Filtros para la tabla `propiedades`
--
ALTER TABLE `propiedades`
  ADD CONSTRAINT `propiedades_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `reservas`
--
ALTER TABLE `reservas`
  ADD CONSTRAINT `reservas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`),
  ADD CONSTRAINT `reservas_ibfk_2` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
