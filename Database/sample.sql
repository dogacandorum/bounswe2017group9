-- MySQL dump 10.13  Distrib 5.7.20, for Linux (x86_64)
--
-- Host: localhost    Database: concerter_db
-- ------------------------------------------------------
-- Server version	5.7.20-0ubuntu0.17.10.1

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
-- Table structure for table `Artists`
--

DROP TABLE IF EXISTS `Artists`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Artists` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=51 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Artists`
--

LOCK TABLES `Artists` WRITE;
/*!40000 ALTER TABLE `Artists` DISABLE KEYS */;
INSERT INTO `Artists` VALUES (45,'Kursat Basar'),(46,'Test Artist'),(47,'Test Artist'),(48,'Pentagram'),(49,''),(50,'Hamza Bekir');
/*!40000 ALTER TABLE `Artists` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Attendees`
--

DROP TABLE IF EXISTS `Attendees`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Attendees` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `concert_id` int(11) NOT NULL,
  `status` int(11) NOT NULL,
  `user_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Attendees_Concerts` (`concert_id`),
  KEY `FK_Attendees_Users` (`user_id`),
  CONSTRAINT `FK_Attendees_Concerts` FOREIGN KEY (`concert_id`) REFERENCES `Concerts` (`id`),
  CONSTRAINT `FK_Attendees_Users` FOREIGN KEY (`user_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Attendees`
--

LOCK TABLES `Attendees` WRITE;
/*!40000 ALTER TABLE `Attendees` DISABLE KEYS */;
INSERT INTO `Attendees` VALUES (4,24,1,54,NULL);
/*!40000 ALTER TABLE `Attendees` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Comments`
--

DROP TABLE IF EXISTS `Comments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comments` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `commented_by` int(11) NOT NULL,
  `concert_id` int(11) NOT NULL,
  `up_votes` int(11) NOT NULL DEFAULT '0',
  `down_votes` int(11) NOT NULL DEFAULT '0',
  `comment` varchar(100) NOT NULL,
  `date_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `category` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Comments_Users` (`commented_by`),
  CONSTRAINT `FK_Comments_Users` FOREIGN KEY (`commented_by`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comments`
--

LOCK TABLES `Comments` WRITE;
/*!40000 ALTER TABLE `Comments` DISABLE KEYS */;
INSERT INTO `Comments` VALUES (1,54,22,0,0,'ehehe','2017-12-26 23:34:59',1);
/*!40000 ALTER TABLE `Comments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Comments_categories`
--

DROP TABLE IF EXISTS `Comments_categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Comments_categories` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `category` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Comments_categories`
--

LOCK TABLES `Comments_categories` WRITE;
/*!40000 ALTER TABLE `Comments_categories` DISABLE KEYS */;
INSERT INTO `Comments_categories` VALUES (1,'Costume'),(2,'Music'),(3,'Place'),(4,'Foods');
/*!40000 ALTER TABLE `Comments_categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Concerts`
--

DROP TABLE IF EXISTS `Concerts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Concerts` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `created_by` int(11) NOT NULL,
  `artist` int(11) DEFAULT NULL,
  `location` int(11) NOT NULL,
  `date_time` datetime DEFAULT NULL,
  `min_price` int(11) NOT NULL,
  `max_price` int(11) NOT NULL,
  `rate` int(11) NOT NULL DEFAULT '0',
  `voter_amount` int(11) NOT NULL DEFAULT '0',
  `image_path` varchar(500) DEFAULT NULL,
  `ticket` varchar(500) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Concerts_Users` (`created_by`),
  KEY `FK_Concerts_Location` (`location`),
  KEY `FK_Concerts_Artists` (`artist`),
  CONSTRAINT `FK_Concerts_Artists` FOREIGN KEY (`artist`) REFERENCES `Artists` (`id`),
  CONSTRAINT `FK_Concerts_Location` FOREIGN KEY (`location`) REFERENCES `Locations` (`id`),
  CONSTRAINT `FK_Concerts_Users` FOREIGN KEY (`created_by`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Concerts`
--

LOCK TABLES `Concerts` WRITE;
/*!40000 ALTER TABLE `Concerts` DISABLE KEYS */;
INSERT INTO `Concerts` VALUES (24,'Loc Test',54,50,43,'2017-12-29 13:00:00',10,120,0,0,'https://cdn.londonandpartners.com/visit/london-organisations/alexandra-palace/92923-640x360-alexandra-palace-gig-640.jpg','http://www.biletix.com/etkinlik-grup/134359460/TURKIYE/tr');
/*!40000 ALTER TABLE `Concerts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Locations`
--

DROP TABLE IF EXISTS `Locations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Locations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `longitude` double DEFAULT NULL,
  `latitude` double DEFAULT NULL,
  `city` varchar(100) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Locations`
--

LOCK TABLES `Locations` WRITE;
/*!40000 ALTER TABLE `Locations` DISABLE KEYS */;
INSERT INTO `Locations` VALUES (38,45.12312,63.43432,'Trump Kultur Merkezi','Trump Kultur Merkezi'),(39,45.12312,63.43432,'Beykoz','Beykoz'),(40,45.12312,63.43432,'Istanbul','Istanbul'),(41,45.12312,63.43432,'Zorlu PSM','Zorlu PSM'),(42,45.12312,63.43432,'null','null'),(43,28.978551400000015,41.0349374,'Hüseyina?a Mahallesi, Balo Sokak No:22','Hüseyina?a Mahallesi, Balo Sokak No:22');
/*!40000 ALTER TABLE `Locations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `MusicalInterests`
--

DROP TABLE IF EXISTS `MusicalInterests`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MusicalInterests` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(100) DEFAULT NULL,
  `user_id` int(11) NOT NULL,
  `musicalInterestId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `MusicalInterests`
--

LOCK TABLES `MusicalInterests` WRITE;
/*!40000 ALTER TABLE `MusicalInterests` DISABLE KEYS */;
/*!40000 ALTER TABLE `MusicalInterests` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notification_type`
--

DROP TABLE IF EXISTS `Notification_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notification_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notification_type`
--

LOCK TABLES `Notification_type` WRITE;
/*!40000 ALTER TABLE `Notification_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `Notification_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Notifications`
--

DROP TABLE IF EXISTS `Notifications`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Notifications` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `content` varchar(200) NOT NULL,
  `target_id` int(11) NOT NULL,
  `concert_id` int(11) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Notifications_Users` (`target_id`),
  CONSTRAINT `FK_Notifications_Users` FOREIGN KEY (`target_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Notifications`
--

LOCK TABLES `Notifications` WRITE;
/*!40000 ALTER TABLE `Notifications` DISABLE KEYS */;
INSERT INTO `Notifications` VALUES (4,'test is attending to Loc Test.',48,24),(5,'test is attending to Loc Test.',51,24);
/*!40000 ALTER TABLE `Notifications` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Relations`
--

DROP TABLE IF EXISTS `Relations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Relations` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `follower_id` int(11) NOT NULL,
  `following_id` int(11) NOT NULL,
  `created_at` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Relations_Users_Following` (`follower_id`),
  CONSTRAINT `FK_Relations_Users_Follower` FOREIGN KEY (`follower_id`) REFERENCES `Users` (`id`),
  CONSTRAINT `FK_Relations_Users_Following` FOREIGN KEY (`follower_id`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Relations`
--

LOCK TABLES `Relations` WRITE;
/*!40000 ALTER TABLE `Relations` DISABLE KEYS */;
INSERT INTO `Relations` VALUES (2,48,54,NULL),(3,51,54,NULL);
/*!40000 ALTER TABLE `Relations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Report_types`
--

DROP TABLE IF EXISTS `Report_types`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Report_types` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Report_types`
--

LOCK TABLES `Report_types` WRITE;
/*!40000 ALTER TABLE `Report_types` DISABLE KEYS */;
/*!40000 ALTER TABLE `Report_types` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Reports`
--

DROP TABLE IF EXISTS `Reports`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Reports` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `concert_id` int(11) NOT NULL,
  `reported_by` int(11) NOT NULL,
  `report_type` int(11) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_Reports_Concerts` (`concert_id`),
  KEY `FK_Reports_Users` (`reported_by`),
  KEY `FK_Reports_Report_type` (`report_type`),
  CONSTRAINT `FK_Reports_Concerts` FOREIGN KEY (`concert_id`) REFERENCES `Concerts` (`id`),
  CONSTRAINT `FK_Reports_Report_type` FOREIGN KEY (`report_type`) REFERENCES `Report_types` (`id`),
  CONSTRAINT `FK_Reports_Users` FOREIGN KEY (`reported_by`) REFERENCES `Users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Reports`
--

LOCK TABLES `Reports` WRITE;
/*!40000 ALTER TABLE `Reports` DISABLE KEYS */;
/*!40000 ALTER TABLE `Reports` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `SemanticTags`
--

DROP TABLE IF EXISTS `SemanticTags`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SemanticTags` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `label` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `concert_id` int(11) NOT NULL,
  `semanticTagId` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `SemanticTags`
--

LOCK TABLES `SemanticTags` WRITE;
/*!40000 ALTER TABLE `SemanticTags` DISABLE KEYS */;
/*!40000 ALTER TABLE `SemanticTags` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Users`
--

DROP TABLE IF EXISTS `Users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Users` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(256) NOT NULL,
  `password` varchar(256) DEFAULT NULL,
  `followers` int(11) DEFAULT '0',
  `followings` int(11) DEFAULT '0',
  `photo_path` varchar(255) NOT NULL DEFAULT '',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `last_login` datetime DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `spotify_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=55 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Users`
--

LOCK TABLES `Users` WRITE;
/*!40000 ALTER TABLE `Users` DISABLE KEYS */;
INSERT INTO `Users` VALUES (48,'Fatih Güven','fatih.guven@hotmail.com',NULL,0,0,'https://scontent.xx.fbcdn.net/v/t1.0-1/p200x200/12042817_10206845907799008_1170828127403682503_n.jpg?oh=0690ae3394ceb9d99b87b10dc5063031&oe=5A9B890B','2017-12-04 20:22:53','2017-12-04 20:22:53','2017-12-04 20:22:53',NULL,'11128938007'),(51,'Test User','tuser@gmail.com','654321',0,0,'https://www.randomlists.com/img/people/arnold_schwarzenegger.jpg','2017-12-06 00:05:57','2017-12-06 00:05:57','2017-12-06 00:05:57','tuser',NULL),(54,'test','test@test.com','121212',0,0,'https://i1.wp.com/visboo.com/img/29042010/66120.jpg','2017-12-26 23:34:23','2017-12-26 23:34:23','2017-12-26 23:34:23','test',NULL);
/*!40000 ALTER TABLE `Users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2017-12-27 23:52:27
