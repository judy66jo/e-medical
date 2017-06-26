-- MySQL dump 10.13  Distrib 5.7.17, for Win64 (x86_64)
--
-- Host: localhost    Database: e-medical
-- ------------------------------------------------------
-- Server version	5.7.18-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `devices`
--

DROP TABLE IF EXISTS `devices`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `devices` (
  `iddevice` int(11) NOT NULL AUTO_INCREMENT,
  `device_name` varchar(10) NOT NULL,
  `idhospital` varchar(10) NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `status` int(1) DEFAULT NULL,
  `cost` float DEFAULT NULL,
  `level` float DEFAULT NULL,
  PRIMARY KEY (`iddevice`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `devices`
--

LOCK TABLES `devices` WRITE;
/*!40000 ALTER TABLE `devices` DISABLE KEYS */;
INSERT INTO `devices` VALUES (21,'A','1',5,1,4,4),(22,'A','2',4,1,4,4),(23,'A','3',2,0,8,7),(24,'B','1',1,1,3,8),(25,'B','2',4,1,6,7),(26,'B','3',1,1,6,6),(27,'C','1',3,0,7,5),(28,'C','2',5,0,9,8),(29,'D','2',4,1,6,6),(30,'D','3',8,1,5,3),(31,'A','4',4,1,4,4),(32,'C','3',3,0,3,2),(33,'E','2',6,1,6,4),(34,'B','4',1,1,2,4),(35,'D','4',7,1,7,5),(36,'E','4',1,1,8,7),(37,'C','5',1,1,7,5),(38,'F','4',6,1,8,7),(39,'E','5',1,1,3,3),(40,'F','5',4,1,6,4),(41,'B','6',5,1,5,4),(42,'C','6',4,1,7,8),(43,'D','6',6,1,4,5),(44,'F','6',2,1,3,3);
/*!40000 ALTER TABLE `devices` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-06-22 15:00:04
