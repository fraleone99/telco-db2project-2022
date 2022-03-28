CREATE DATABASE  IF NOT EXISTS `telco`;
USE `telco`;
-- MySQL dump 10.13  Distrib 8.0.19, for Win64 (x86_64)
--
-- Host: localhost    Database: telco
-- ------------------------------------------------------
-- Server version	10.0.4

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
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(45) NOT NULL,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `isInsolvent` boolean NOT NULL,
  PRIMARY KEY (`username`),
  UNIQUE KEY `user_id` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES	`user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS*/;
INSERT INTO `user` VALUES(1,'fraleone99@gmail.com','spaceranger','pippo1',false);
/*!40000 ALTER TABLE `user` ENABLE KEYS*/;
UNLOCK TABLES;
--
-- Table structure for table `servicePackage`
--

DROP TABLE IF EXISTS `servicePackage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `servicePackage` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `monthlyFee12` float NOT NULL,
  `monthlyFee24` float NOT NULL,
  `monthlyFee36` float NOT NULL,
  `soldNumber` int NOT NULL,
  `usernameEmployee` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username_employee_idx` (`usernameEmployee`),
  CONSTRAINT `package_username_employee` FOREIGN KEY (`usernameEmployee`) REFERENCES `employee` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `type` ENUM('fixedPhone','mobilePhone','fixedInternet','mobileInternet'),
  `minutesNumber` int,
  `smsNumber` int,
  `minutesFee` float,
  `smsFee` float,
  `gigabytesNumber` int,
  `gigabytesFee` float,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `optionalProduct`
--

DROP TABLE IF EXISTS `optionalProduct`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optionalProduct` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(45) NOT NULL,
  `monthlyFee` float NOT NULL,
  `usernameEmployee` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `username_employee_idx` (`usernameEmployee`),
  CONSTRAINT `optional_username_employee` FOREIGN KEY (`usernameEmployee`) REFERENCES `employee` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  PRIMARY KEY (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `order`
--

DROP TABLE IF EXISTS `order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order` (
  `id` int NOT NULL AUTO_INCREMENT,
  `dateOfOrder` DATETIME NOT NULL,
  `totalValue` float NOT NULL,
  `startDate` DATE NOT NULL,
  `isValid` boolean NOT NULL,
  `duration` int NOT NULL,
  `idBuyer` int NOT NULL,
  `idPackage` int NOT NULL,
  PRIMARY KEY (`id`),
  KEY `boughtby_idx` (`idBuyer`),
  KEY `contain_idx` (`idPackage`),
  CONSTRAINT `boughtby` FOREIGN KEY (`idBuyer`) REFERENCES `user` (`id`),
  CONSTRAINT `contain` FOREIGN KEY (`idPackage`) REFERENCES `servicePackage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `auditingTable`
--

DROP TABLE IF EXISTS `auditingTable`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `auditingTable` (
  `userId` int NOT NULL,
  `username` varchar(45) NOT NULL,
  `email` varchar(45) NOT NULL,
  `lastAmount` float NOT NULL,
  `lastDate` DATETIME NOT NULL,
  PRIMARY KEY (`userId`,`username`),
  CONSTRAINT `fk_auditing_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
  CONSTRAINT `fk_auditing_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `serviceActivationSchedule`
--

DROP TABLE IF EXISTS `serviceActivationSchedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviceActivationSchedule` (
  `orderId` int NOT NULL,
  PRIMARY KEY (`orderId`),
  CONSTRAINT `fk_schedule_orderId` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `buy`
--

DROP TABLE IF EXISTS `buy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `buy` (
  `userId` int(11) NOT NULL,
  `packageId` int(11) NOT NULL,
  PRIMARY KEY (`userId`,`packageId`),
  KEY `buy_userId_idx` (`userId`),
  KEY `buy_packageId_idx` (`packageId`),
  CONSTRAINT `buy_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`),
  CONSTRAINT `buy_packageId` FOREIGN KEY (`packageId`) REFERENCES `servicePackage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `associatedTo`
--

DROP TABLE IF EXISTS `associatedTo`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `associatedTo` (
  `optionalId` int(11) NOT NULL,
  `packageId` int(11) NOT NULL,
  PRIMARY KEY (`optionalId`,`packageId`),
  KEY `associatedTo_optionalId_idx` (`optionalId`),
  KEY `associatedTo_packageId_idx` (`packageId`),
  CONSTRAINT `associatedTo_optionalId` FOREIGN KEY (`optionalId`) REFERENCES `optionalProduct` (`id`),
  CONSTRAINT `associatedTo_packageId` FOREIGN KEY (`packageId`) REFERENCES `servicePackage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `composedOf`
--

DROP TABLE IF EXISTS `composedOf`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `composedOf` (
  `serviceId` int(11) NOT NULL,
  `packageId` int(11) NOT NULL,
  PRIMARY KEY (`serviceId`,`packageId`),
  KEY `composedOf_serviceId_idx` (`serviceId`),
  KEY `composedOf_packageId_idx` (`packageId`),
  CONSTRAINT `composedOf_serviceId` FOREIGN KEY (`serviceId`) REFERENCES `service` (`id`),
  CONSTRAINT `composedOf_packageId` FOREIGN KEY (`packageId`) REFERENCES `servicePackage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;