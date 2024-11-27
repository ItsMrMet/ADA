-- MySQL dump 10.13  Distrib 8.0.38, for Win64 (x86_64)
--
-- Host: localhost    Database: bd_peticionsclients
-- ------------------------------------------------------
-- Server version	8.0.39

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `articulo`
--

DROP TABLE IF EXISTS `articulo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `articulo` (
  `idArticulo` int NOT NULL AUTO_INCREMENT,
  `descripcion` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`idArticulo`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `articulo`
--

LOCK TABLES `articulo` WRITE;
/*!40000 ALTER TABLE `articulo` DISABLE KEYS */;
/*!40000 ALTER TABLE `articulo` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `idCliente` varchar(45) NOT NULL,
  `saldo` decimal(7,2) NOT NULL,
  `limiteCredito` int NOT NULL,
  `descuento` int DEFAULT NULL,
  PRIMARY KEY (`idCliente`),
  UNIQUE KEY `idcliente_UNIQUE` (`idCliente`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES ('123456',5000.00,300,10);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `direccion`
--

DROP TABLE IF EXISTS `direccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `direccion` (
  `idDireccion` int NOT NULL AUTO_INCREMENT,
  `numero` varchar(45) NOT NULL,
  `calle` varchar(45) NOT NULL,
  `codPostal` varchar(45) NOT NULL,
  `ciudad` varchar(45) NOT NULL,
  `idCliente` varchar(45) NOT NULL,
  PRIMARY KEY (`idDireccion`),
  KEY `FK_Direccion_IdCliente_idx` (`idCliente`),
  CONSTRAINT `FK_Direccion_IdCliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `direccion`
--

LOCK TABLES `direccion` WRITE;
/*!40000 ALTER TABLE `direccion` DISABLE KEYS */;
/*!40000 ALTER TABLE `direccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fabrica`
--

DROP TABLE IF EXISTS `fabrica`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fabrica` (
  `idFabrica` int NOT NULL AUTO_INCREMENT,
  `numTelefono` varchar(45) DEFAULT NULL,
  `numArticulosProvistos` int DEFAULT NULL,
  PRIMARY KEY (`idFabrica`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabrica`
--

LOCK TABLES `fabrica` WRITE;
/*!40000 ALTER TABLE `fabrica` DISABLE KEYS */;
/*!40000 ALTER TABLE `fabrica` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fabricar`
--

DROP TABLE IF EXISTS `fabricar`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fabricar` (
  `precio` decimal(7,2) NOT NULL,
  `existencias` int DEFAULT NULL,
  `idArticulo` int NOT NULL,
  `idFabrica` int NOT NULL,
  PRIMARY KEY (`idArticulo`,`idFabrica`),
  KEY `Fk_Fabricar_IdArticulo_idx` (`idArticulo`),
  KEY `FK_Fabricar_idFabrica_idx` (`idFabrica`),
  CONSTRAINT `Fk_Fabricar_IdArticulo` FOREIGN KEY (`idArticulo`) REFERENCES `articulo` (`idArticulo`),
  CONSTRAINT `FK_Fabricar_idFabrica` FOREIGN KEY (`idFabrica`) REFERENCES `fabrica` (`idFabrica`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fabricar`
--

LOCK TABLES `fabricar` WRITE;
/*!40000 ALTER TABLE `fabricar` DISABLE KEYS */;
/*!40000 ALTER TABLE `fabricar` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incluye`
--

DROP TABLE IF EXISTS `incluye`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `incluye` (
  `cantidad` int NOT NULL,
  `idArticulo` int NOT NULL,
  `idPedido` int NOT NULL,
  PRIMARY KEY (`idPedido`,`idArticulo`),
  KEY `FK_Incluye_idArticulo_idx` (`idArticulo`),
  KEY `FK_Incluye_idPedido` (`idPedido`),
  CONSTRAINT `FK_Incluye_idArticulo` FOREIGN KEY (`idArticulo`) REFERENCES `articulo` (`idArticulo`) ON DELETE CASCADE,
  CONSTRAINT `FK_Incluye_idPedido` FOREIGN KEY (`idPedido`) REFERENCES `pedido` (`idPedido`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incluye`
--

LOCK TABLES `incluye` WRITE;
/*!40000 ALTER TABLE `incluye` DISABLE KEYS */;
/*!40000 ALTER TABLE `incluye` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pedido`
--

DROP TABLE IF EXISTS `pedido`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pedido` (
  `fecha` datetime NOT NULL,
  `idCliente` varchar(45) NOT NULL,
  `idDireccion` int NOT NULL,
  `idPedido` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`idPedido`),
  KEY `FK_Pedido_idCliente_idx` (`idCliente`),
  KEY `FK_Pedido_idDireccion_idx` (`idDireccion`),
  CONSTRAINT `FK_Pedido_idCliente` FOREIGN KEY (`idCliente`) REFERENCES `cliente` (`idCliente`),
  CONSTRAINT `FK_Pedido_idDireccion` FOREIGN KEY (`idDireccion`) REFERENCES `direccion` (`idDireccion`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pedido`
--

LOCK TABLES `pedido` WRITE;
/*!40000 ALTER TABLE `pedido` DISABLE KEYS */;
/*!40000 ALTER TABLE `pedido` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-10-13 23:46:31
