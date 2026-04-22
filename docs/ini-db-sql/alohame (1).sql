-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 22-04-2026 a las 17:28:21
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
(1, 2, 1, 'Muy buena experiencia', 5, '2026-03-29 18:53:22'),
(2, 3, 13, 'Perfecta para relajarse, nuestro anfitrión nos dejó desayuno para todos los dias', 5, '2026-04-10 15:15:44'),
(3, 2, 1, 'muy contentos con el anfitrión, nos dejó guías del lugar, el único problema es que al ser céntrico hay bastante ruido', 5, '2026-04-20 17:01:58'),
(4, 3, 9, 'Hace falta limpieza en esta casa, el aire acondicionado no funcionaba y el dueño no nos hacia caso', 2, '2026-04-21 15:13:41');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `comodidad`
--

CREATE TABLE `comodidad` (
  `id` bigint(20) NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `icono` varchar(80) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `comodidad`
--

INSERT INTO `comodidad` (`id`, `nombre`, `icono`) VALUES
(1, 'WiFi', 'bi-wifi'),
(2, 'TV', 'bi-tv'),
(3, 'Aire acondicionado', 'bi-thermometer-snow'),
(4, 'Calefacción', 'bi-fire'),
(5, 'Cocina equipada', 'bi-egg-fried'),
(6, 'Lavadora', 'bi-recycle'),
(7, 'Secador de pelo', 'bi-wind'),
(8, 'Lavavajillas', 'bi-droplet-half'),
(9, 'Parking', 'bi-p-square-fill'),
(10, 'Piscina', 'bi-water'),
(11, 'Terraza', 'bi-tree'),
(12, 'Ascensor', 'bi-arrow-up-circle'),
(13, 'Mascotas permitidas', 'bi-heart'),
(14, 'Bicicletas', 'bi-bicycle'),
(15, 'Caja fuerte', 'bi-shield-lock'),
(16, 'Cafetera', 'bi-cup-hot');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `favoritos`
--

CREATE TABLE `favoritos` (
  `id` int(11) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_propiedad` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `favoritos`
--

INSERT INTO `favoritos` (`id`, `id_usuario`, `id_propiedad`) VALUES
(1, 2, 1),
(8, 2, 2),
(9, 2, 3),
(5, 3, 8),
(6, 3, 13),
(2, 7, 2),
(3, 7, 3),
(4, 7, 6),
(10, 8, 11);

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
(1, 1, 'valencia1.jpg'),
(2, 2, 'oviedo1.jpg'),
(3, 3, 'madrid1.jpg'),
(4, 4, 'barcelona2.jpg'),
(5, 5, 'salamanca1.jpg'),
(6, 6, 'malaga1.jpg'),
(7, 7, 'badajoz1.jpg'),
(8, 8, 'galicia1.jpg'),
(10, 1, 'valencia2.jpg'),
(11, 1, 'valencia3.jpg'),
(12, 1, 'valencia4.jpg'),
(13, 1, 'valencia5.jpg'),
(14, 1, 'valencia6.jpg'),
(15, 1, 'valencia7.jpg'),
(16, 1, 'valencia8.jpg'),
(17, 2, 'oviedo2.jpg'),
(18, 2, 'oviedo3.jpg'),
(19, 2, 'oviedo4.jpg'),
(20, 2, 'oviedo5.jpg'),
(21, 2, 'oviedo6.jpg'),
(22, 2, 'oviedo7.jpg'),
(23, 2, 'oviedo8.jpg'),
(24, 2, 'oviedo9.jpg'),
(25, 2, 'oviedo10.jpg'),
(26, 3, 'madrid2.jpg'),
(27, 3, 'madrid3.jpg'),
(28, 3, 'madrid4.jpg'),
(29, 3, 'madrid5.jpg'),
(30, 3, 'madrid6.jpg'),
(31, 3, 'madrid7.jpg'),
(32, 3, 'madrid8.jpg'),
(33, 3, 'madrid9.jpg'),
(34, 3, 'madrid10.jpg'),
(35, 4, 'barcelona3.jpg'),
(36, 4, 'barcelona4.jpg'),
(37, 4, 'barcelona5.jpg'),
(38, 4, 'barcelona6.jpg'),
(39, 4, 'barcelona7.jpg'),
(40, 4, 'barcelona8.jpg'),
(41, 4, 'barcelona9.jpg'),
(42, 4, 'barcelona10.jpg'),
(44, 5, 'salamanca2.jpg'),
(45, 5, 'salamanca3.jpg'),
(46, 5, 'salamanca4.jpg'),
(47, 5, 'salamanca5.jpg'),
(48, 5, 'salamanca6.jpg'),
(49, 5, 'salamanca7.jpg'),
(50, 5, 'salamanca8.jpg'),
(51, 6, 'malaga2.jpg'),
(52, 6, 'malaga3.jpg'),
(53, 6, 'malaga4.jpg'),
(54, 6, 'malaga5.jpg'),
(55, 6, 'malaga6.jpg'),
(56, 6, 'malaga7.jpg'),
(57, 6, 'malaga8.jpg'),
(58, 6, 'malaga9.jpg'),
(59, 6, 'malaga10.jpg'),
(60, 6, 'malaga11.jpg'),
(61, 7, 'badajoz2.jpg'),
(62, 7, 'badajoz3.jpg'),
(63, 7, 'badajoz4.jpg'),
(64, 7, 'badajoz5.jpg'),
(65, 7, 'badajoz6.jpg'),
(66, 7, 'badajoz7.jpg'),
(93, 8, 'galicia2.jpg'),
(94, 8, 'galicia3.jpg'),
(95, 8, 'galicia4.jpg'),
(96, 8, 'galicia5.jpg'),
(97, 8, 'galicia6.jpg'),
(98, 8, 'galicia7.jpg'),
(99, 8, 'galicia8.jpg'),
(100, 8, 'galicia9.jpg'),
(101, 10, 'Madrid2_1.jpg'),
(102, 10, 'Madrid2_2.jpg'),
(103, 10, 'Madrid2_3.jpg'),
(104, 10, 'Madrid2_4.jpg'),
(105, 10, 'Madrid2_5.jpg'),
(113, 11, 'segovia1.jpg'),
(114, 11, 'segovia2.jpg'),
(115, 11, 'segovia3.jpg'),
(116, 11, 'segovia4.jpg'),
(117, 11, 'segovia5.jpg'),
(118, 11, 'segovia6.jpg'),
(119, 11, 'segovia7.jpg'),
(120, 12, 'mallorca1.jpg'),
(121, 12, 'mallorca2.jpg'),
(122, 12, 'mallorca3.jpg'),
(123, 12, 'mallorca4.jpg'),
(124, 12, 'mallorca5.jpg'),
(125, 12, 'mallorca6.jpg'),
(126, 12, 'mallorca7.jpg'),
(127, 12, 'mallorca8.jpg'),
(128, 13, 'vitoria1.jpg'),
(129, 13, 'vitoria2.jpg'),
(130, 13, 'vitoria3.jpg'),
(131, 13, 'vitoria4.jpg'),
(132, 13, 'vitoria5.jpg'),
(133, 13, 'vitoria6.jpg'),
(134, 13, 'vitoria7.jpg'),
(135, 14, 'tenerife1.jpg'),
(136, 14, 'tenerife2.jpg'),
(137, 14, 'tenerife3.jpg'),
(138, 14, 'tenerife4.jpg'),
(139, 14, 'tenerife5.jpg'),
(140, 14, 'tenerife6.jpg'),
(141, 14, 'tenerife7.jpg'),
(142, 15, 'sevilla1.jpg'),
(143, 15, 'sevilla2.jpg'),
(144, 15, 'sevilla3.jpg'),
(145, 15, 'sevilla4.jpg'),
(146, 15, 'sevilla5.jpg'),
(147, 15, 'sevilla6.jpg'),
(148, 15, 'sevilla7.jpg'),
(150, 9, 'avila9.jpg'),
(151, 9, 'avila8.jpg'),
(152, 9, 'avila7.jpg'),
(153, 9, 'avila6.jpg'),
(154, 9, 'avila5.jpg'),
(155, 9, 'avila4.jpg'),
(156, 9, 'avila2.jpg'),
(157, 9, 'avila1.jpg'),
(158, 9, 'avila3.jpg');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mensaje`
--

CREATE TABLE `mensaje` (
  `id` bigint(20) NOT NULL,
  `contenido` text NOT NULL,
  `fecha` datetime NOT NULL,
  `propiedad_id` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `id_mensaje_padre` bigint(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `mensaje`
--

INSERT INTO `mensaje` (`id`, `contenido`, `fecha`, `propiedad_id`, `id_usuario`, `id_mensaje_padre`) VALUES
(1, 'Hola estoy interesado en esta casa', '2026-04-14 17:21:19', NULL, NULL, NULL),
(3, 'hola', '2026-04-15 18:42:38', 6, NULL, NULL),
(4, 'hola estaría interesado en esta propiedad', '2026-04-15 18:52:16', 4, NULL, NULL),
(5, 'se me ha olvidado consultar si puedo llevar mascotas', '2026-04-17 15:38:58', 6, NULL, NULL),
(6, 'estoy interesada en esta propiedad pero no veo que se indique que tiene calefacción, hay opción de reserva en epoca de invierno', '2026-04-20 16:08:09', 2, NULL, NULL),
(7, 'aceptan mascotas?', '2026-04-20 16:08:46', 5, NULL, NULL),
(8, 'estoy interesado', '2026-04-20 16:45:58', 3, NULL, NULL),
(9, 'Hola, no veo que indiquen si tienen cuna, sería posible??', '2026-04-22 16:13:02', 10, 7, NULL),
(10, 'Ahora mismo no está disponible', '2026-04-22 16:38:45', 10, 4, 9),
(11, 'dime las fechas en las que quieres reservar', '2026-04-22 17:06:25', 3, 5, 8),
(12, 'ahora mismo está disponible en todo el año', '2026-04-22 17:09:08', 4, 5, 4);

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
(1, 'Apartamento céntrico en Valencia', 'Esta propiedad es un alquiler temporal desde 11 noches hasta 11 meses.\r\n\r\nBienvenido a nuestro encantador apartamento, donde la comodidad y la conveniencia se encuentran en perfecta armonía.\r\nEstá ubicado cerca del centro de la ciudad, al tiempo que se encuentra en un barrio tranquilo y sereno durante la noche, lo que lo convierte en el lugar perfecto para relajarse y descansar después de un largo día de exploración o trabajo.\r\nConsta de 2 dormitorios, cocina equipada y baño completo\r\nOtras comodidades que ofrecemos son Tv, aire acondicionado para calefacción y refrigeración, lavadora/secadora combinada, plancha y secador de pelo.', 80.00, 'Valencia', 4, 4, '2026-03-29 18:53:22'),
(2, 'Apartamento cerca del centro en Gijón (Asturias) ', 'Disfruta de Gijón en este bonito apartamento recién reformado en pleno centro comercial de la ciudad. Alojamiento ideal y decoración alegre.\r\nConsta de dos dormitorios, uno con 2 camas individuales y otro con una cama de matrimonio. Baño completo y cocina americana.\r\nDispone de un parking cercano, a 50 metros (17€/día) , pertenece a un pequeño centro comercial que dispone de supermercado y gimnasio. La plaza mayor y el puerto deportivo se sitúan a 8 minutos a pie', 120.00, 'Asturias', 4, 4, '2026-03-29 18:53:22'),
(3, 'Elegante y contemporáneo con terraza y balcón', '¡Descubre una escapada soleada a Madrid en el corazón de Usera! Este impresionante apartamento, recientemente renovado, combina comodidad con auténtico encanto local.\r\nInteriores llenos de luz: enormes ventanales y una renovación fresca hacen que cada habitación brille.\r\nTerraza privada: tu propio oasis urbano, perfecto para cenas al atardecer.\r\nTotalmente equipado: cuenta con una cocina moderna y todo lo esencial para que te sientas como en casa lejos de casa.', 220.00, 'Madrid', 2, 5, '2026-03-29 18:53:22'),
(4, 'Casa rural afueras de Barcelona', 'Dos habitaciones muy caseras con tres camas y desayuno en familia puro airbnb maravilloso entorno de la payesia en la frontera de La Selva y La Garrotxa. Seguro que repetiréis. Nuestra misión es haceros agradable vuestra estancia. Es ideal para parejas con niños pequeños por su entorno rural sin tráfico. Además de rutas de senderismo y de bicicleta. A 30 kms. dela capital de la Garrotxa: Olot, y a 30kms también de a capital de la Selva: Santa Coloma de Farnés. Se respira tranquilidad en el aire.', 280.00, 'Barcelona', 4, 5, '2026-03-29 18:53:22'),
(5, 'Apartamento en Salamanca', 'Gracias a la ubicación céntrica de este alojamiento, tú y los tuyos lo tendréis todo a mano, en pleno centro de la ciudad, en la plaza Bautista, a 2 minutos andando de todos los monumentos más importantes de Salamanca en el mismo casco histórico.\r\nDispone de una cama de matrimonio, de 135 cm x 190 cm con ropa de cama de calidad y armario ropero abierto de diseño, con plaza para 2 personas. Diseño único y con mucha personalidad.\r\nEl salón cocina con barra americana con 4 sillas, dispone de un sofá -cama de dos plazas ( 140 x 190 cm) muy confortable y una televisión de 40 pulgadas.\r\n\r\nCocina de diseño abierto, amplia dispone de una pequeña mesa con dos sillas y una pequeña terraza para tender la ropa. La cocina moderna está equipada con todos los electrodomésticos necesarios, tales como horno, nevera, microondas, campana, lavadora, lavavajillas, cafetera dolce gusto De capsulas y platos, copas de vino.', 120.00, 'Salamanca', 4, 6, '2026-03-29 18:53:22'),
(6, 'Vivienda en Málaga', 'Villa exclusiva en zona tranquila, un chalet exclusivo para 4 huéspedes, con 2 dormitorios dobles, 2 baños completos, cocina totalmente equipada y sala de estar con WIFI y estacionamiento privado.\r\nLas mascotas son bienvenidas ', 250.00, 'Malaga', 4, 6, '2026-03-29 18:53:22'),
(7, 'Apartamento con Jacuzzi en zona tranquila', '¿Necesitas un lugar para relajarte y desconectar, pero bien conectado para hacer turismo por la ciudad o sus alrededores? No pierdas la oportunidad de disfrutar de este amplio apartamento con Jacuzzi, cama de matrimonio, cocina completa y todas las comodidades para disfrutar en pareja o en familia de unos días en la ciudad. De reciente construcción y con las mejores calidades, te sentirás como en casa en un espacio amplio, agradable y lejos del tráfico y con fácil aparcamiento.\r\n\r\nEl espacio\r\n\r\nEl apartamento consta de cocina completa amueblada (con horno y lavavajillas), con menaje completo, unida a un amplio salón-comedor, con sofá-cama de 3 plazas, convertible en cama de 160x200 (para dos personas) y una habitación con cama de matrimonio (160x200) con un increíble jacuzzi. Bomba de frío/calor en salón y habitación, y todas las comodidades para pasar unos días (sábanas, nórdico, toallas, lavadora, tv de 50 pulgadas, wi-fi, etc.).', 150.00, 'Badajoz', 2, 6, '2026-03-29 18:53:22'),
(8, 'Primera línea en Combarro', 'Gran oportunidad de pasar tus vacaciones en una preciosa casa con vistas al mar. Con una ubicación privilegiada, esta vivienda consta de 2 dormitorios (uno de ellos con dos camas de 90), baño, cocina, salón y terraza con vistas al mar.\r\nEn cuento a los servicios, disponemos de:\r\nLas dos habitaciones dispones de ropa de cama.\r\nLa cocina está equipada con lavavajillas.\r\nDisponemos de parking gratuito para los huéspedes, a 25 metros de la casa.\r\n', 140.00, 'Galicia', 5, 4, '2026-03-30 17:51:39'),
(9, 'La Casa del Mozo', 'Apartamento amplio completamente rehabilitado en el centro de Ávila, situado en la principal vía de acceso a la Muralla y la Basílica de San Vicente. Muy próximo a los monumentos más importantes de esta magnífica ciudad Patrimonio de la Humanidad.\r\n\r\nEl espacio\r\nMenaje de cocina, cafetera, tostador, hervidor de agua, tv con smart tv en salón y habitación principal, wi fi, camas cómodas y baño perfectamente equipado para que te sientas como en casa. Ofrezco una plaza de garaje como cortesía en otro edificio ', 110.00, 'Ávila', 4, 5, '2026-03-30 17:51:39'),
(10, 'Precioso apartamento en Madrid', 'Alojamiento entero: apto. residencial en Madrid, España\r\nConsta de cocina equipada, baño completo y sofá cama en el salón.\r\nNo aceptamos mascotas.\r\n', 170.00, 'Madrid', 4, 4, '2026-04-02 17:44:01'),
(11, 'Cabana Rentals - Acogedora casa con verde jardín', 'Desde CABANA Rentals, te invitamos a disfrutar de este chalet en Ituero y Lama, pensado para conectarte con la naturaleza, desde las amplias ventanas que permiten la entrada de la luz natural hasta el jardín rigurosamente cuidado que te invita a sentarte, relajarte y desconectar en silencio de todo.\r\n\r\nUn lugar para escapar del ajetreo y el bullicio de la ciudad, donde el tiempo se ralentiza y el contacto directo con la naturaleza te renueva y te llena de vitalidad.\r\n\r\nEl espacio\r\nEl chalet cuenta con dos habitaciones cuidadosamente decoradas. La habitación principal en la parte baja del chalet es espaciosa y acogedora, con una cama doble que te garantiza noches de descanso reparador. La segunda habitación, perfecta para niños o acompañantes, cuenta con dos camas individuales y se encuentra ubicada en la planta superior.\r\n\r\nPor otro lado, el salón principal es un espacio amplio y luminoso, decorado con elegancia que transmite creatividad y te hace sentir como en casa. Cuenta con una zona para comer y otra para relajarte en el sofá mientras ves la televisión, disfrutar de música o de una buena conversación.\r\n\r\nBaño moderno, cómodo y completamente equipado al igual que la cocina, la cuál es muy luminosa y cuenta con todo lo necesario.\r\n\r\nJacuzzi solo disponible en temporada de verano\r\n\r\nEn definitiva, un espacio que te ofrece la oportunidad de escapar, descansar y rejuvenecer de múltiples maneras.', 120.00, 'Segovia', 4, 5, '2026-04-09 16:24:00'),
(12, 'Sa Caseta, zona playa del Trenc-Ses Salines', 'Bonita finca situada a 5 Km de la Playa d\'Es Trenc. Está a solo 3 Km del encantador pueblo de Ses Salines. Cerca también de Santanyí y Es Llombards y Colonia de Sant Jordi. Situada en una pequeña colina, en una zona muy tranquila, sin vecinos cerca, en plena naturaleza, con un camino particular, lejos de ruidos. Es un pequeño oasis de paz y tranquilidad en el sur de Mallorca.  Ideal para disfrutar de unas agradables y relajadas vacaciones. Mi intención es que el huésped se sienta como en casa.\r\n\r\nEl espacio\r\nCercanía a las playas mejores de Mallorca y a los pueblos más pintorescos del sur de la isla, con sus famosos mercados y restaurantes. Acceso al parque nacional Es Trenc Salobrar de Campos. Cercanía con el Parque Nacional de Cabrera.', 230.00, 'Mallorca', 6, 5, '2026-04-09 16:44:11'),
(13, 'Casa rural en zona tranquila', 'Conexión con la naturaleza en un pueblo a solo 20 km de Vitoria. En un pequeño pueblo alavés con unas vistas impresionantes al valle de Pobes. Paz y tranquilidad garantizadas.\r\nEl espacio\r\nSe trata de un moderno chalet de dos plantas. Se alquila la parte de abajo, de 166 metros , distribuidos en un gran salon con cocina integrada en el mismo de 55 metros, y dos habitaciones, una de las cuales dispone de baño dentro de la habitación con jacuzzi. La otra habitación tiene un baño en el exterior. Hay ademas del jardín una terraza panorámica de 20 metros cuadrados y garage para un coche a su disposición.\r\n\r\nServicios y zonas comunes\r\nLos viajeros pueden utilizar toda la parcela, incluyendo la barbacoa.\r\n\r\nOtros aspectos destacables\r\nLa casa esta compuesta por dos partes diferenciadas, una planta baja de 166 metros que corresponde a la vivienda principal y que puede ser alquilada por un máximo de 4 personas y una planta superior que no se encuentra disponible.', 180.00, 'Vitoria', 4, 6, '2026-04-09 16:47:45'),
(14, 'Apartamento a pie de playa', 'Bienvenido a este apartamento frente al mar, pensado para disfrutar del entorno con calma y comodidad. Su acceso directo a dos playas lo convierte en una opción ideal para quienes valoran la cercanía real al océano.\r\n\r\nDesde el balcón, las vistas al mar acompañan cada momento del día. La brisa marina y el sonido constante de las olas aportan una sensación de descanso auténtico.\r\n\r\nSu ubicación permite combinar relax y descubrir Tenerife con total libertad, disfrutando del mar a solo unos pasos.\r\n\r\nEl espacio\r\nTerraza:\r\n\r\nLa terraza es uno de los rincones más especiales del apartamento. Con vistas al mar, es ideal para desayunar al aire libre, leer o contemplar el atardecer en un entorno tranquilo y costero.\r\n\r\nHabitaciones:\r\n\r\nEl apartamento cuenta con dos habitaciones, ambas equipadas con camas de matrimonio. Son espacios luminosos, cómodos y pensados para el descanso, con mobiliario funcional y un ambiente tranquilo ideal para relajarte tras un día de playa o excursiones.\r\n\r\nBaño:\r\n\r\nEl baño es amplio y funcional, con ducha, lavabo, inodoro y toallas incluidas. Todo lo necesario para tu rutina diaria en un ambiente limpio y cómodo.\r\n\r\nCocina y salón:\r\n\r\nLa cocina abierta está equipada con lo esencial para preparar tus comidas durante la estancia: placa de inducción, nevera, utensilios y microondas. Comparte espacio con una zona de salón acogedora, perfecta para descansar, charlar o disfrutar de una comida informal.\r\n\r\nServicios y zonas comunes\r\nLa comunidad es tranquila y agradable, un entorno cuidado que invita al descanso y a desconectar del ritmo diario. Es un espacio ideal para quienes valoran la calma y un ambiente relajado durante toda la estancia.\r\n\r\nEl exterior ofrece un entorno agradable para disfrutar del aire libre, ya sea dando un paseo, leyendo o simplemente aprovechando el clima suave de la zona.\r\n\r\nLa zona cuenta con dos playas de arena negra, generalmente poco concurridas durante la mayor parte del año, lo que permite disfrutar del mar de forma pausada, sin aglomeraciones y en un ambiente auténtico.', 190.00, 'Tenerife', 3, 6, '2026-04-09 16:48:51'),
(15, 'Casa en zona céntrica', 'Apartamento en el centro de Sevilla (junto al puente de Triana y la Real Maestranza).  El piso dispone de 3 habitaciones y 2 baños. Desde aquí pueden visitarse a pie todos  los puntos de interés de la ciudad; se sitúa es una calle tranquila junto al río y en una sexta planta.\r\n\r\nEl espacio\r\nInstalaciones y electrodomésticos.', 230.00, 'Sevilla', 5, 6, '2026-04-09 16:49:55');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `propiedad_comodidad`
--

CREATE TABLE `propiedad_comodidad` (
  `propiedad_id` int(11) NOT NULL,
  `comodidad_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `propiedad_comodidad`
--

INSERT INTO `propiedad_comodidad` (`propiedad_id`, `comodidad_id`) VALUES
(1, 1),
(1, 2),
(1, 3),
(1, 4),
(1, 6),
(1, 7),
(1, 8),
(1, 11),
(1, 12),
(1, 16),
(2, 2),
(2, 3),
(2, 5),
(2, 6),
(2, 7),
(2, 8),
(2, 9),
(2, 11),
(2, 12),
(3, 1),
(3, 2),
(3, 3),
(3, 5),
(3, 6),
(3, 11),
(3, 16),
(4, 1),
(4, 2),
(4, 3),
(4, 5),
(4, 9),
(4, 14),
(4, 16),
(5, 1),
(5, 3),
(5, 5),
(5, 9),
(6, 1),
(6, 2),
(6, 3),
(6, 5),
(6, 10),
(6, 13),
(7, 1),
(7, 3),
(7, 4),
(7, 5),
(7, 6),
(7, 9),
(8, 2),
(8, 4),
(8, 5),
(8, 8),
(8, 12),
(8, 16),
(9, 1),
(9, 3),
(9, 4),
(9, 5),
(9, 9),
(9, 16),
(10, 1),
(10, 4),
(10, 5),
(10, 6),
(10, 7),
(10, 16),
(11, 1),
(11, 2),
(11, 3),
(11, 5),
(11, 9),
(11, 14),
(12, 1),
(12, 2),
(12, 3),
(12, 5),
(12, 9),
(12, 10),
(13, 1),
(13, 2),
(13, 3),
(13, 5),
(13, 9),
(13, 14),
(14, 1),
(14, 2),
(14, 5),
(14, 7),
(14, 9),
(14, 11),
(14, 16),
(15, 1),
(15, 2),
(15, 3),
(15, 5),
(15, 11);

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
(1, 2, 1, '2026-04-01', '2026-04-05', 'confirmada'),
(2, 7, 6, '2026-05-10', '2026-05-20', 'confirmada'),
(3, 3, 13, '2026-05-15', '2026-05-25', 'confirmada'),
(4, 3, 9, '2026-05-04', '2026-05-12', 'confirmada'),
(5, 8, 11, '2026-06-01', '2026-04-17', 'cancelada'),
(6, 8, 11, '2026-04-27', '2026-05-03', 'confirmada');

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
(6, 'Paula Navarro', 'paula@gmail.com', '990800', '600777888', '2026-03-29 18:53:22', 'propietario'),
(7, 'Rafael', 'rafael@gmail.com', '12345', '655445556', '2026-04-09 19:32:45', 'cliente'),
(8, 'Sofia garcia', 'sofia@gmail.com', '444555', '915566998', '2026-04-21 15:14:54', 'cliente');

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
-- Indices de la tabla `comodidad`
--
ALTER TABLE `comodidad`
  ADD PRIMARY KEY (`id`);

--
-- Indices de la tabla `favoritos`
--
ALTER TABLE `favoritos`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `uk_favorito_usuario_propiedad` (`id_usuario`,`id_propiedad`),
  ADD KEY `fk_favorito_propiedad` (`id_propiedad`);

--
-- Indices de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_propiedad` (`id_propiedad`);

--
-- Indices de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD PRIMARY KEY (`id`),
  ADD KEY `fk_mensaje_propiedades` (`propiedad_id`),
  ADD KEY `fk_mensaje_usuario` (`id_usuario`),
  ADD KEY `fk_mensaje_padre` (`id_mensaje_padre`);

--
-- Indices de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  ADD PRIMARY KEY (`id`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `propiedad_comodidad`
--
ALTER TABLE `propiedad_comodidad`
  ADD PRIMARY KEY (`propiedad_id`,`comodidad_id`),
  ADD KEY `fk_pc_comodidad` (`comodidad_id`);

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
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=5;

--
-- AUTO_INCREMENT de la tabla `comodidad`
--
ALTER TABLE `comodidad`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- AUTO_INCREMENT de la tabla `favoritos`
--
ALTER TABLE `favoritos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=11;

--
-- AUTO_INCREMENT de la tabla `imagenes`
--
ALTER TABLE `imagenes`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=159;

--
-- AUTO_INCREMENT de la tabla `mensaje`
--
ALTER TABLE `mensaje`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;

--
-- AUTO_INCREMENT de la tabla `propiedades`
--
ALTER TABLE `propiedades`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=19;

--
-- AUTO_INCREMENT de la tabla `reservas`
--
ALTER TABLE `reservas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- AUTO_INCREMENT de la tabla `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

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
-- Filtros para la tabla `favoritos`
--
ALTER TABLE `favoritos`
  ADD CONSTRAINT `fk_favorito_propiedad` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`),
  ADD CONSTRAINT `fk_favorito_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `imagenes`
--
ALTER TABLE `imagenes`
  ADD CONSTRAINT `imagenes_ibfk_1` FOREIGN KEY (`id_propiedad`) REFERENCES `propiedades` (`id`);

--
-- Filtros para la tabla `mensaje`
--
ALTER TABLE `mensaje`
  ADD CONSTRAINT `fk_mensaje_padre` FOREIGN KEY (`id_mensaje_padre`) REFERENCES `mensaje` (`id`),
  ADD CONSTRAINT `fk_mensaje_propiedades` FOREIGN KEY (`propiedad_id`) REFERENCES `propiedades` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_mensaje_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `propiedades`
--
ALTER TABLE `propiedades`
  ADD CONSTRAINT `propiedades_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `usuarios` (`id`);

--
-- Filtros para la tabla `propiedad_comodidad`
--
ALTER TABLE `propiedad_comodidad`
  ADD CONSTRAINT `fk_pc_comodidad` FOREIGN KEY (`comodidad_id`) REFERENCES `comodidad` (`id`) ON DELETE CASCADE,
  ADD CONSTRAINT `fk_pc_propiedad` FOREIGN KEY (`propiedad_id`) REFERENCES `propiedades` (`id`) ON DELETE CASCADE;

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
