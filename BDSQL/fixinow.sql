-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 14, 2023 at 03:17 AM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `fixinow`
--

-- --------------------------------------------------------

--
-- Table structure for table `empresas`
--

CREATE TABLE `empresas` (
  `id` int(11) NOT NULL,
  `nombre` varchar(255) DEFAULT NULL,
  `servicio` varchar(255) DEFAULT NULL,
  `correo` varchar(255) DEFAULT NULL,
  `telefono` varchar(20) DEFAULT NULL,
  `fotos` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `empresas`
--

INSERT INTO `empresas` (`id`, `nombre`, `servicio`, `correo`, `telefono`, `fotos`) VALUES
(1, 'Farmacia', 'Servicios de farmacia', 'farmacia@example.com', '123456789', 'fotos_farmacia'),
(2, 'Mecánico', 'Servicios de mecánica', 'mecanico@example.com', '987654321', 'fotos_mecanico'),
(3, 'Comida', 'Servicios de comida', 'comida@example.com', '555555555', 'fotos_comida');

-- --------------------------------------------------------

--
-- Table structure for table `pedidos`
--

CREATE TABLE `pedidos` (
  `id` int(11) NOT NULL,
  `empresa` varchar(50) DEFAULT NULL,
  `servicio` varchar(50) DEFAULT NULL,
  `dia` date DEFAULT NULL,
  `usuarioid` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `pedidos`
--

INSERT INTO `pedidos` (`id`, `empresa`, `servicio`, `dia`, `usuarioid`) VALUES
(1, 'Empresa A', 'Servicio X', '2023-07-01', 1),
(2, 'Empresa B', 'Servicio Y', '2023-07-02', 1),
(3, 'Empresa C', 'Servicio Z', '2023-07-03', 1),
(4, 'Empresa D', 'Servicio X', '2023-07-04', 1),
(5, 'Empresa E', 'Servicio Y', '2023-07-05', 1);

-- --------------------------------------------------------

--
-- Table structure for table `usuarios`
--

CREATE TABLE `usuarios` (
  `id` int(10) NOT NULL,
  `name` varchar(50) NOT NULL,
  `user` varchar(50) NOT NULL,
  `psw` varchar(50) NOT NULL,
  `correo` varchar(50) NOT NULL,
  `telefono` int(50) NOT NULL,
  `foto` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `usuarios`
--

INSERT INTO `usuarios` (`id`, `name`, `user`, `psw`, `correo`, `telefono`, `foto`) VALUES
(1, 'Juan Perez', 'juan', '123', 'juan', 123456789, ''),
(14, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Memes.png'),
(15, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Nosequees.png'),
(16, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Jajaja.png'),
(17, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Lolz.png'),
(18, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Prueba no se.png'),
(19, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Buando el error.png'),
(20, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Buando el error.png'),
(21, '', '', '', '', 0, 'http://localhost/phpconex/Fotos/.png'),
(22, '', '', '', '', 0, 'http://localhost/phpconex/Fotos/.png'),
(23, '', '', '', '', 0, 'http://localhost/phpconex/Fotos/.png'),
(24, '', '', '', '', 0, 'http://localhost/phpconex/Fotos/.png'),
(25, '', '', '', '', 0, 'http://localhost/phpconex/Fotos/.png'),
(26, '', '', '', '', 0, 'http://localhost/phpconex/Fotos/.png'),
(27, '', '', '', 'juan', 123456789, 'http://localhost/phpconex/Fotos/Proceso.png');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `empresas`
--
ALTER TABLE `empresas`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `pedidos`
--
ALTER TABLE `pedidos`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `usuarios`
--
ALTER TABLE `usuarios`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `empresas`
--
ALTER TABLE `empresas`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `pedidos`
--
ALTER TABLE `pedidos`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT for table `usuarios`
--
ALTER TABLE `usuarios`
  MODIFY `id` int(10) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=28;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
