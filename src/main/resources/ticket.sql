CREATE DATABASE  IF NOT EXISTS `ticket` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `ticket`;
-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ticket
-- ------------------------------------------------------
-- Server version	8.0.33

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
-- Table structure for table `permissao`
--

DROP TABLE IF EXISTS `permissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permissao` (
  `id_permissao` bigint NOT NULL AUTO_INCREMENT,
  `nome` varchar(45) NOT NULL,
  PRIMARY KEY (`id_permissao`),
  UNIQUE KEY `permissaocol_UNIQUE` (`nome`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permissao`
--

LOCK TABLES `permissao` WRITE;
/*!40000 ALTER TABLE `permissao` DISABLE KEYS */;
INSERT INTO `permissao` VALUES (1,'administrador'),(2,'usuario');
/*!40000 ALTER TABLE `permissao` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `ticket`
--

DROP TABLE IF EXISTS `ticket`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `ticket` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nome` varchar(50) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `telefone` varchar(11) DEFAULT NULL,
  `titulo` varchar(30) DEFAULT NULL,
  `descricao` varchar(200) DEFAULT NULL,
  `situacao` tinyint(1) DEFAULT '0',
  `data_criacao` datetime DEFAULT NULL,
  `resolucao` varchar(200) DEFAULT NULL,
  `data_fechamento` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `ticket`
--

LOCK TABLES `ticket` WRITE;
/*!40000 ALTER TABLE `ticket` DISABLE KEYS */;
INSERT INTO `ticket` VALUES (1,'Ramon editado','ramon@gmail.com','21999955555','PC','Estou com problema no SSD',1,NULL,'mudei','2024-11-06 00:00:00'),(2,'teste01','teste01@teste.com','212121','teste01','teste',1,NULL,'teste01','2024-11-06 00:00:00'),(3,'teste5','teste5@teste.com','21987654325','teste5','teste5',1,'2024-11-08 00:00:00','','2024-11-08 07:40:28'),(4,'teste6','teste6@teste.com','21987654325','teste6','teste6',1,'2024-11-08 00:00:00','resolvido teste 6','2024-11-08 07:30:15'),(6,'teste8','teste8@teste.com','21987654328','teste8','teste8',0,'2024-11-12 05:06:44',NULL,NULL),(7,'teste9','teste9@email.com','21987654329','teste9','teste9',0,'2024-11-12 05:10:59',NULL,NULL);
/*!40000 ALTER TABLE `ticket` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario`
--

DROP TABLE IF EXISTS `usuario`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario` (
  `id_usuario` bigint NOT NULL AUTO_INCREMENT,
  `cpf` varchar(11) NOT NULL,
  `email` varchar(45) NOT NULL,
  `nome` varchar(45) NOT NULL,
  `senha` varchar(255) NOT NULL,
  `data_expiracao` date DEFAULT NULL,
  `data_nascimento` date DEFAULT NULL,
  `permissao` int DEFAULT NULL,
  `primeiro_login` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`),
  UNIQUE KEY `cpf_UNIQUE` (`cpf`)
) ENGINE=InnoDB AUTO_INCREMENT=19 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario`
--

LOCK TABLES `usuario` WRITE;
/*!40000 ALTER TABLE `usuario` DISABLE KEYS */;
INSERT INTO `usuario` VALUES (1,'00000000000','administrador@gmail.com','Ramon ','$2a$10$5LdcO7KXll2ul69d9chNRuqNN25OosxdwU172w4LqdX4vvj7o5Zxe',NULL,'2010-10-10',1,0),(2,'12345678901','teste@teste','vinicius','1234',NULL,'2010-10-10',1,0),(3,'12345678902','teste1@teste','Novo usario','$2a$10$r3EHCZW575zUZwwLQiyd3uO8aE7OAKL5gJSqPVguNdMStYuYRsKty',NULL,'2010-10-10',1,0),(4,'12345678991','teste@teste','Arnaldo','$2a$10$099hV55gdc43tN.aoYC2MuePo71uz3TaOVUw19HMcpcdrSD7HJFji',NULL,'2010-10-10',2,0),(15,'12345678909','testehoje@teste.com','Teste hoje','$2a$10$NpDXigQiATYlmct15nmd4ek.Ao30DUCrJpP0ZgXYEletTcuUtydHS',NULL,'2010-10-10',1,0),(16,'00380639050','hoje@teste.com','teste hoje user','$2a$10$H3y2aOijcdxMaNasgq2pbe7VboKOzPA/QXZwyMTybt8malXtlcWMe',NULL,'2010-10-10',2,0),(17,'78182273005','josecarlos.@teste.com','jose carlos','$2a$10$EiNFTkKzFKhiRwG/X1SMceTrkxLorvpgyQRJXAx3AglJfXAINHBq.',NULL,'2010-10-10',1,0),(18,'03611107077','agorahoje@teste.com','agora hoje','$2a$10$x4MRrBIoi9ooh5hhPsT0zOzLpAIpzyZb9GaRPHce48nUOAVQmPwYW',NULL,'2010-10-10',1,0);
/*!40000 ALTER TABLE `usuario` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `usuario_permissao`
--

DROP TABLE IF EXISTS `usuario_permissao`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `usuario_permissao` (
  `usuario_id_usuario` bigint NOT NULL,
  `permissao_id_permissao` bigint NOT NULL,
  PRIMARY KEY (`usuario_id_usuario`,`permissao_id_permissao`),
  KEY `fk_usuario_permissao_permissao_idx` (`permissao_id_permissao`),
  CONSTRAINT `fk_usuario_permissao_permissao` FOREIGN KEY (`permissao_id_permissao`) REFERENCES `permissao` (`id_permissao`),
  CONSTRAINT `fk_usuario_permissao_usuario` FOREIGN KEY (`usuario_id_usuario`) REFERENCES `usuario` (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `usuario_permissao`
--

LOCK TABLES `usuario_permissao` WRITE;
/*!40000 ALTER TABLE `usuario_permissao` DISABLE KEYS */;
INSERT INTO `usuario_permissao` VALUES (1,1),(2,1),(3,1),(15,1),(17,1),(18,1),(4,2),(16,2);
/*!40000 ALTER TABLE `usuario_permissao` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-12-02 19:57:36
