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
                        `numberFailedPayment` int NOT NULL DEFAULT 0,
                        `lastFailedId` int,
                        PRIMARY KEY (`username`),
                        UNIQUE KEY `user_id` (`id`),
                        CONSTRAINT `fk_user_invalidOrder` FOREIGN KEY (`lastFailedId`) REFERENCES `order` (`id`)

) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


LOCK TABLES	`user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS*/;
INSERT INTO `user` VALUES(1,'fraleone99@gmail.com','spaceranger','pippo1',false,0,null);
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

LOCK TABLES	`service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS*/;
INSERT INTO `service` VALUES(1,'fixedPhone',null,null,null,null,null,null);
/*!40000 ALTER TABLE `service` ENABLE KEYS*/;
UNLOCK TABLES;

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

LOCK TABLES	`employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS*/;
INSERT INTO `employee` VALUES('franco','franco');
/*!40000 ALTER TABLE `employee` ENABLE KEYS*/;
UNLOCK TABLES;

LOCK TABLES	`optionalProduct` WRITE;
/*!40000 ALTER TABLE `optionalProduct` DISABLE KEYS*/;
INSERT INTO `optionalProduct` VALUES(1,'TV',5,'franco');
/*!40000 ALTER TABLE `optionalProduct` ENABLE KEYS*/;
UNLOCK TABLES;

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
                                 `username` varchar(45),
                                 `email` varchar(45),
                                 `lastAmount` float,
                                 `lastDate` DATETIME,
                                 PRIMARY KEY (`userId`),
                                 CONSTRAINT `fk_auditing_userId` FOREIGN KEY (`userId`) REFERENCES `user` (`id`) ON DELETE RESTRICT,
                                 CONSTRAINT `fk_auditing_username` FOREIGN KEY (`username`) REFERENCES `user` (`username`)

) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;


-- trigger
DELIMITER ;;
CREATE TRIGGER updateSoldNumber1
    AFTER INSERT ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) THEN

    UPDATE servicePackage
    SET soldNumber=soldNumber+1
    WHERE id=new.idPackage;

END IF;
END;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER updateSoldNumber2
    AFTER UPDATE ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) THEN

    UPDATE servicePackage
    SET soldNumber=soldNumber+1
    WHERE id=new.idPackage;

END IF;
END;;
DELIMITER ;


-- trigger
DELIMITER ;;
CREATE TRIGGER AlertForFailedPaymentUser
    AFTER UPDATE ON user
    FOR EACH ROW
BEGIN
    DECLARE amount INT;
    DECLARE lastDateFailed DATETIME;
    IF !(new.numberFailedPayment <=> old.numberFailedPayment) AND
       NOT EXISTS (SELECT 1 FROM auditingTable WHERE userId = new.id) AND
       (new.numberFailedPayment) >= 3 THEN
        SET amount = (SELECT totalValue FROM `order` WHERE id = new.lastFailedId);
        SET lastDateFailed = (SELECT dateOfOrder FROM `order` WHERE id = new.lastFailedId);
    INSERT INTO auditingTable VALUES (new.id,new.username,new.email,amount,lastDateFailed);
END IF;
END;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER AlertForFailedPaymentUser_setOrderInfo
    AFTER UPDATE ON user
    FOR EACH ROW
BEGIN
    IF !(new.lastFailedId <=> old.lastFailedId) AND
        EXISTS (SELECT 1 FROM auditingTable WHERE userId = new.id) THEN

    UPDATE auditingTable
    SET lastAmount = (SELECT totalValue FROM `order` where id = new.lastFailedId)
    WHERE userId=new.id;

    UPDATE auditingTable
    SET lastDate = (SELECT dateOfOrder FROM `order` where id = new.lastFailedId)
    WHERE userId=new.id;

END IF;
END;;
DELIMITER ;





--
-- Table structure for table `serviceActivationSchedule`
--

DROP TABLE IF EXISTS `serviceActivationSchedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `serviceActivationSchedule` (
                                             `id` int NOT NULL AUTO_INCREMENT,
                                             `orderId` int NOT NULL,
                                             `startDate` DATE NOT NULL,
                                             `endDate` DATE NOT NULL,
                                             PRIMARY KEY (`id`),
                                             KEY `order_idx` (`orderId`),
                                             CONSTRAINT `fk_schedule_orderId` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`) ON DELETE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `services`
--

DROP TABLE IF EXISTS `services`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `services` (
                            `activationScheduleId` int(11) NOT NULL,
                            `serviceId` int(11) NOT NULL,
                            PRIMARY KEY (`activationScheduleId`,`serviceId`),
                            KEY `associatedTo_activationScheduleId_idx` (`activationScheduleId`),
                            KEY `associatedTo_serviceId_idx` (`serviceId`),
                            CONSTRAINT `associatedTo_ScheduleId` FOREIGN KEY (`activationScheduleId`) REFERENCES `serviceActivationSchedule` (`id`),
                            CONSTRAINT `associatedTo_ServiceId` FOREIGN KEY (`serviceId`) REFERENCES `service` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `optionals`
--

DROP TABLE IF EXISTS `optionals`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optionals` (
                             `activationScheduleId` int(11) NOT NULL,
                             `optionalId` int(11) NOT NULL,
                             PRIMARY KEY (`activationScheduleId`,`optionalId`),
                             KEY `associatedTo_activationScheduleId_idx` (`activationScheduleId`),
                             KEY `associatedTo_optionalId_idx` (`optionalId`),
                             CONSTRAINT `associatedTo_ScheduleId_optionals` FOREIGN KEY (`activationScheduleId`) REFERENCES `serviceActivationSchedule` (`id`),
                             CONSTRAINT `associatedTo_OptionalId_optionals` FOREIGN KEY (`optionalId`) REFERENCES `optionalProduct` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
-- Table structure for table `optionalSelected`
--

DROP TABLE IF EXISTS `optionalSelected`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `optionalSelected` (
                                    `optionalId` int(11),
                                    `orderId` int(11),
                                    PRIMARY KEY (`optionalId`,`orderId`),
                                    KEY `associatedTo_optionalId_idx` (`optionalId`),
                                    KEY `associatedTo_orderId_idx` (`orderId`),
                                    CONSTRAINT `optionalSelected_optionalId` FOREIGN KEY (`optionalId`) REFERENCES `optionalProduct` (`id`),
                                    CONSTRAINT `optionalSelected_orderId` FOREIGN KEY (`orderId`) REFERENCES `order` (`id`)
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



DROP TABLE IF EXISTS `packagePerValidityPeriod`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `packagePerValidityPeriod` (
                                            `packageId` int(11) NOT NULL,
                                            `soldNumber12` int DEFAULT 0,
                                            `soldNumber24` int DEFAULT 0,
                                            `soldNumber36` int DEFAULT 0,
                                            PRIMARY KEY (`packageId`),
                                            CONSTRAINT `validityPeriod_packageId` FOREIGN KEY (`packageId`) REFERENCES `servicePackage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- trigger
DELIMITER ;;
CREATE TRIGGER validityPeriod1
    AFTER INSERT ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) AND (SELECT soldNumber FROM `servicePackage` WHERE id=new.idPackage)>=2 THEN
		IF (new.duration=12) THEN
    UPDATE packagePerValidityPeriod
    SET soldNumber12 = soldNumber12 + 1
    WHERE packageId=new.idPackage;
END IF;
IF (new.duration=24) THEN
UPDATE packagePerValidityPeriod
SET soldNumber24 = soldNumber24 + 1
WHERE packageId=new.idPackage;
END IF;
        IF (new.duration=36) THEN
UPDATE packagePerValidityPeriod
SET soldNumber36 = soldNumber36 + 1
WHERE packageId=new.idPackage;
END IF;
END IF;
END ;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER validityPeriod2
    AFTER UPDATE ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) AND (SELECT soldNumber FROM `servicePackage` WHERE id=new.idPackage)>=2 THEN
		IF (new.duration=12) THEN
    UPDATE packagePerValidityPeriod
    SET soldNumber12 = soldNumber12 + 1
    WHERE packageId=new.idPackage;
END IF;
IF (new.duration=24) THEN
UPDATE packagePerValidityPeriod
SET soldNumber24 = soldNumber24 + 1
WHERE packageId=new.idPackage;
END IF;
        IF (new.duration=36) THEN
UPDATE packagePerValidityPeriod
SET soldNumber36 = soldNumber36 + 1
WHERE packageId=new.idPackage;
END IF;
END IF;
END ;;
DELIMITER ;

DROP TABLE IF EXISTS `valueOfSales`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `valueOfSales` (
                                `packageId` int(11) NOT NULL,
                                `valueOfSalesWith` float DEFAULT 0,
                                `valueOfSalesWithout` float DEFAULT 0,
                                PRIMARY KEY (`packageId`),
                                CONSTRAINT `valueOfSales_packageId` FOREIGN KEY (`packageId`) REFERENCES `servicePackage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- trigger
DELIMITER ;;
CREATE TRIGGER sales1
    AFTER INSERT ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) AND (SELECT soldNumber FROM `servicePackage` WHERE id=new.idPackage)>=2 THEN
    UPDATE valueOfSales
    SET valueOfSalesWith = valueOfSalesWith + new.totalValue
    WHERE packageId=new.idPackage;
    UPDATE valueOfSales
    SET valueOfSalesWithout = valueOfSalesWithout + (new.totalValue - ((SELECT monthlyFee FROM optionalProduct WHERE id =
                                                                                                                     (SELECT optionalId FROM optionalSelected  WHERE orderId=new.id)) * new.duration))
    WHERE packageId=new.idPackage;
END IF;
END ;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER sales2
    AFTER UPDATE ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) AND (SELECT soldNumber FROM `servicePackage` WHERE id=new.idPackage)>=2 THEN
    UPDATE valueOfSales
    SET valueOfSalesWith = valueOfSalesWith + new.totalValue
    WHERE packageId=new.idPackage;
    UPDATE valueOfSales
    SET valueOfSalesWithout = valueOfSalesWithout + (new.totalValue - ((SELECT monthlyFee FROM optionalProduct WHERE id =
                                                                                                                     (SELECT optionalId FROM optionalSelected  WHERE orderId=new.id)) * new.duration))
    WHERE packageId=new.idPackage;
END IF;
END ;;
DELIMITER ;

DROP TABLE IF EXISTS `averageOptional`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `averageOptional` (
                                   `packageId` int(11) NOT NULL,
                                   `optionals` int DEFAULT 0,
                                   `average` float DEFAULT 0,
                                   PRIMARY KEY (`packageId`),
                                   CONSTRAINT `average_packageId` FOREIGN KEY (`packageId`) REFERENCES `servicePackage` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

-- trigger
DELIMITER ;;
CREATE TRIGGER average1
    AFTER INSERT ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) AND (SELECT soldNumber FROM `servicePackage` WHERE id=new.idPackage)>=2 THEN
    UPDATE averageOptional
    SET optionals = optionals + (SELECT COUNT(*) FROM optionalSelected WHERE orderId=new.id)
    WHERE packageId = new.idPackage;
    UPDATE averageOptional
    SET average = optionals / (SELECT soldNumber FROM servicePackage WHERE id=new.idPackage)
    WHERE packageId = new.idPackage;
END IF;
END ;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER average2
    AFTER UPDATE ON `order`
    FOR EACH ROW
BEGIN
    IF (new.isValid=1) AND (SELECT soldNumber FROM `servicePackage` WHERE id=new.idPackage)>=2 THEN
    UPDATE averageOptional
    SET optionals = optionals + (SELECT COUNT(*) FROM optionalSelected WHERE orderId=new.id)
    WHERE packageId = new.idPackage;
    UPDATE averageOptional
    SET average = optionals / (SELECT soldNumber FROM servicePackage WHERE id=new.idPackage)
    WHERE packageId = new.idPackage;
END IF;
END ;;
DELIMITER ;

DROP TABLE IF EXISTS `bestSeller`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bestSeller` (
                              `optionalId` int(11) NOT NULL,
                              `soldNumber` int DEFAULT 0,
                              `valueOfSales` float DEFAULT 0,
                              PRIMARY KEY (`optionalId`),
                              CONSTRAINT `bestSeller_optionalId` FOREIGN KEY (`optionalId`) REFERENCES `optionalProduct` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

LOCK TABLES	`bestSeller` WRITE;
/*!40000 ALTER TABLE `bestSeller` DISABLE KEYS*/;
INSERT INTO `bestSeller` VALUES(1,0,0);
/*!40000 ALTER TABLE `bestSeller` ENABLE KEYS*/;
UNLOCK TABLES;

-- trigger
DELIMITER ;;
CREATE TRIGGER bestSeller1
    AFTER INSERT ON `optionalSelected`
    FOR EACH ROw
BEGIN
    IF (SELECT isValid FROM `order` WHERE id = new.orderId)=1 THEN
    UPDATE bestSeller
    SET soldNumber = soldNumber + (SELECT COUNT(*) FROM optionalSelected WHERE optionalId = new.optionalId AND orderId = new.orderId)
    WHERE optionalId=new.optionalId;
    UPDATE bestSeller
    SET valueOfSales = soldNumber * (SELECT monthlyFee FROM optionalProduct WHERE id=new.optionalId)
    WHERE optionalId=new.optionalId;
END IF;
END ;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER bestSeller2
    AFTER UPDATE ON `optionalSelected`
    FOR EACH ROW
BEGIN
    IF (SELECT isValid FROM `order` WHERE id = new.orderId)=1 THEN
    UPDATE bestSeller
    SET soldNumber = (SELECT COUNT(*) FROM optionalSelected WHERE optionalId=new.optionalId AND
            (SELECT isValid FROM `order` WHERE id=new.optionalId)=1)
    WHERE optionalId=new.optionalId;
    UPDATE bestSeller
    SET valueOfSales = soldNumber * (SELECT monthlyFee FROM optionalProduct WHERE id=new.optionalId)
    WHERE optionalId=new.optionalId;
END IF;
END ;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER newPackage
    AFTER INSERT ON `servicePackage`
    FOR EACH ROW
BEGIN
    INSERT INTO packagePerValidityPeriod VALUES (new.id,0,0,0);
    INSERT INTO valueOfSales VALUES (new.id,0,0);
    INSERT INTO averageOptional VALUES (new.id,0,0);
END ;;
DELIMITER ;

-- trigger
DELIMITER ;;
CREATE TRIGGER newOptional
    AFTER INSERT ON `optionalProduct`
    FOR EACH ROW
BEGIN
    INSERT INTO bestSeller VALUES (new.id,0,0);
END ;;
DELIMITER ;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;