-- MySQL dump 10.13  Distrib 8.0.30, for Win64 (x86_64)
--
-- Host: 45.32.124.86    Database: laptech
-- ------------------------------------------------------
-- Server version	8.0.32

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
-- Table structure for table `tbl_address`
--

DROP TABLE IF EXISTS `tbl_address`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_address` (
  `id` varchar(25) NOT NULL,
  `user_id` bigint NOT NULL,
  `country` varchar(25) NOT NULL,
  `line1` varchar(25) NOT NULL,
  `line2` varchar(25) NOT NULL,
  `line3` varchar(25) NOT NULL,
  `street` varchar(100) NOT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT '0',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_address_insert` AFTER INSERT ON `tbl_address` FOR EACH ROW BEGIN
	declare in_action_description longtext;
	set in_action_description = concat('Add new addess: ', new.street, ', ', new.line3, ', ', new.line2, ', ', new.line1, ', ', new.country, ' for user has id=', new.user_id);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    SELECT 'tbl_address', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_address_update` BEFORE UPDATE ON `tbl_address` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_address_update` AFTER UPDATE ON `tbl_address` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
		set in_action_description = concat('Update from: ', old.street, ', ', old.line3, ', ', old.line2, ', ', old.line1, ', ', old.country,
			'\n-> ', new.street, ', ', new.line3, ', ', new.line2, ', ', new.line1, ', ', new.country, ' for user has id=', new.user_id);
		if new.is_default = 1 then 
			set in_action_description = concat(in_action_description, '\nNow this is new default addess');
		else  
			set in_action_description = concat(in_action_description, '\nNow this is normal addess');
		end if;
    IF new.is_del = 1 and old.is_del = 0 THEN
		set in_action_description = concat('Delete address:', new.street, ', ', new.line3, ', ', new.line2, ', ', new.line1, ', ', new.country, ' of user_id=', new.user_id);
        SET in_action_name = 'DELETE';
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
		set in_action_description = concat('Restore address:', new.street, ', ', new.line3, ', ', new.line2, ', ', new.line1, ', ', new.country, ' of user_id=', new.user_id);
        SET in_action_name = 'RESTORE';
    END IF;  
    
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    SELECT 'tbl_address', new.id, in_action_name, new.modified_date, new.update_by, in_action_description;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_address_delete` AFTER DELETE ON `tbl_address` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete address:', old.street, ', ', old.line3, ', ', old.line2, ', ', old.line1, ', ', old.country, ' of user_id=', old.user_id, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_address', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_banner`
--

DROP TABLE IF EXISTS `tbl_banner`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_banner` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `path` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `link_product` varchar(255) DEFAULT NULL,
  `used_date` date NOT NULL,
  `ended_date` date NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_banner_insert` AFTER INSERT ON `tbl_banner` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add new banner [', new.type, '], path: ', new.path, ', title: ', new.title, ', use in: ', new.used_date, ' - ', new.ended_date);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    SELECT 'tbl_banner', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_banner_update` BEFORE UPDATE ON `tbl_banner` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_banner_update` AFTER UPDATE ON `tbl_banner` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
	set in_action_description = concat('Update banner [', old.type, '], path: ', old.path, ', title: ', old.title, ', use in: ', old.used_date, ' - ', old.ended_date,
		'\n-> [', new.type, '], path: ', new.path, ', title: ', new.title, ', use in: ', new.used_date, ' - ', new.ended_date);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete banner type:', new.type);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore banner type:', new.type);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_banner', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_banner_delete` AFTER DELETE ON `tbl_banner` FOR EACH ROW BEGIN
    declare in_action_description longtext;
	set in_action_description = concat('Delete banner type:', old.type, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_banner', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_brand`
--

DROP TABLE IF EXISTS `tbl_brand`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_brand` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `establish_date` date DEFAULT NULL,
  `logo` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_brand_insert` AFTER INSERT ON `tbl_brand` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add brand ', new.name, ' - ', new.country, '[', new.establish_date, ']', ' logo:', new.logo);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_brand', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_brand_update` BEFORE UPDATE ON `tbl_brand` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_brand_update` AFTER UPDATE ON `tbl_brand` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update brand ', old.name, ' - ', old.country, '[', old.establish_date, ']', ' logo:', old.logo, 
		'\n->', new.name, ' - ', new.country, '[', new.establish_date, ']', ' logo:', new.logo);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete brand:', new.name);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore brand:', new.name);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_brand', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_brand_delete` AFTER DELETE ON `tbl_brand` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete brand:', old.name, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_brand', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_cart`
--

DROP TABLE IF EXISTS `tbl_cart`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_cart` (
  `id` varchar(50) NOT NULL,
  `user_id` bigint NOT NULL,
  `discount_id` bigint DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_cart_insert` AFTER INSERT ON `tbl_cart` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add new cart for user id=', new.user_id, ', discount_id=', new.discount_id);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_cart', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_cart_update` BEFORE UPDATE ON `tbl_cart` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_cart_update` AFTER UPDATE ON `tbl_cart` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update cart from: user_id=', old.user_id, ', discount_id=', old.discount_id, 
		' to user_id=', new.user_id, ', discount_id=', new.discount_id);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete cart id=', new.id);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore cart id=', new.id);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_cart', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_cart_delete` AFTER DELETE ON `tbl_cart` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete cart id=', old.id, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_cart', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_category`
--

DROP TABLE IF EXISTS `tbl_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_category` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  `image` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_category_insert` AFTER INSERT ON `tbl_category` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add category ', new.name, ' - image_link:', new.image, ', description:', new.description);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_category', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_category_update` BEFORE UPDATE ON `tbl_category` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_category_update` AFTER UPDATE ON `tbl_category` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update category ', old.name, ' - image_link:', old.image, ', description:', old.description,
		'\n->', new.name, ' - image_link:', new.image, ', description:', new.description);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete category:', new.name);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore category:', new.name);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_category', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_category_delete` AFTER DELETE ON `tbl_category` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete category: ', old.name,' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_category', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_comment`
--

DROP TABLE IF EXISTS `tbl_comment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_comment` (
  `id` varchar(25) NOT NULL,
  `root_comment_id` varchar(25) DEFAULT NULL,
  `product_id` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `phone` char(15) NOT NULL,
  `content` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_comment_insert` AFTER INSERT ON `tbl_comment` FOR EACH ROW BEGIN
    DECLARE in_action_description LONGTEXT;
    SET in_action_description = CONCAT('Add comment [root:', NEW.root_comment_id, '] for product_id=', NEW.product_id, ', user:', NEW.username, ' - phone:', NEW.phone, ', content:', NEW.content);
    
    INSERT INTO tbl_log_system (action_table, record_id, action_name, action_time, action_by, action_description)
    SELECT 'tbl_comment', NEW.id, 'INSERT', NEW.modified_date, NEW.update_by, in_action_description;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_comment_update` BEFORE UPDATE ON `tbl_comment` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_comment_update` AFTER UPDATE ON `tbl_comment` FOR EACH ROW BEGIN
    DECLARE in_action_description LONGTEXT;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    SET in_action_description = CONCAT('Update comment [root:', OLD.root_comment_id, '] for product_id=', OLD.product_id, ', user:', OLD.username, ' - phone:', OLD.phone, ', content:', OLD.content,
		'\n-> [root:', NEW.root_comment_id, '] for product_id=', NEW.product_id, ', user:', NEW.username, ' - phone:', NEW.phone, ', content:', NEW.content);

    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        SET in_action_description = CONCAT('Delete comment of product_id=', new.product_id);
    END IF;

    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        SET in_action_description = CONCAT('Restore comment of product_id=', new.product_id);
    END IF;

    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_comment', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_comment_delete` AFTER DELETE ON `tbl_comment` FOR EACH ROW BEGIN
    DECLARE in_action_description LONGTEXT;
    SET in_action_description = CONCAT('Delete comment on product_id=', old.product_id,' [Absolutely]');
    
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by)
    VALUES ('tbl_comment', old.id, 'DESTROY', now(), old.update_by);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_discount`
--

DROP TABLE IF EXISTS `tbl_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_discount` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `code` varchar(20) NOT NULL,
  `rate` float unsigned NOT NULL,
  `max_amount` decimal(21,4) unsigned NOT NULL DEFAULT '0.0000',
  `applied_type` enum('PRODUCT','PURCHASE') NOT NULL DEFAULT 'PRODUCT',
  `applied_date` datetime NOT NULL,
  `ended_date` datetime NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_discount_insert` AFTER INSERT ON `tbl_discount` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add discount [', new.code, '], ', new.rate, ' - ', new.max_amount, ', for:', new.applied_type, ', use:', new.applied_date, ' - ', new.ended_date);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_discount', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_discount_update` BEFORE UPDATE ON `tbl_discount` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_discount_update` AFTER UPDATE ON `tbl_discount` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update discount [', old.code, '], ', old.rate, ' - ', old.max_amount, ', for:', old.applied_type, ', use:', old.applied_date, ' - ', old.ended_date,
		'\n-> [', new.code, '], ', new.rate, ' - ', new.max_amount, ', for:', new.applied_type, ', use:', new.applied_date, ' - ', new.ended_date);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete discount [', new.code, ']');
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore discount [', new.code, ']');
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_discount', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_discount_delete` AFTER DELETE ON `tbl_discount` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete discount [', old.code, '] [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_discount', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_feedback`
--

DROP TABLE IF EXISTS `tbl_feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_feedback` (
  `id` varchar(25) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `user_id` bigint NOT NULL,
  `content` varchar(255) NOT NULL,
  `rating_point` tinyint unsigned NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`,`product_id`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_feedback_insert` AFTER INSERT ON `tbl_feedback` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add feedback in product_id=', new.product_id, ' (', new.rating_point ,') by user_id=', new.user_id, ', content:', new.content);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_feedback', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_feedback_update` BEFORE UPDATE ON `tbl_feedback` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_feedback_update` AFTER UPDATE ON `tbl_feedback` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update feedback of product_id=', old.product_id, ' (', old.rating_point ,') by user_id=', old.user_id, ', content:', old.content,
		'\n-> product_id=', new.product_id, ' (', new.rating_point ,') by user_id=', new.user_id, ', content:', new.content);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete feedback of product_id: ', new.product_id, ' - user_id:', new.user_id);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore feedback of product_id: ', new.product_id, ' - user_id:', new.user_id);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_feedback', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_feedback_delete` AFTER DELETE ON `tbl_feedback` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete feedback of product_id: ', old.product_id, ' - user_id:', old.user_id, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_feedback', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_import`
--

DROP TABLE IF EXISTS `tbl_import`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_import` (
  `id` varchar(25) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `quantity` bigint unsigned NOT NULL,
  `imported_price` decimal(21,4) unsigned NOT NULL DEFAULT '0.0000',
  `imported_date` datetime NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_import_insert` AFTER INSERT ON `tbl_import` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add Import ticket for product_id=', new.product_id, ' (', new.quantity, ') * ', new.imported_price, ', date:', new.imported_date);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_import', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
    
	SET SQL_SAFE_UPDATES=0;
	UPDATE `tbl_product` 
	SET 
		quantity_in_stock = quantity_in_stock + new.quantity
	WHERE
		id = new.product_id COLLATE utf8_general_ci;
	SET SQL_SAFE_UPDATES=1;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_import_update` BEFORE UPDATE ON `tbl_import` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_import_update` AFTER UPDATE ON `tbl_import` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update ticket for product_id=', old.product_id, ' (', old.quantity, ') * ', old.imported_price, ', date:', old.imported_date,
		'\n->', 'product_id=', new.product_id, ' (', new.quantity, ') * ', new.imported_price, ', date:', new.imported_date);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete ticket of product_id=', new.product_id);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore ticket of product_id=', new.product_id);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_import', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_import_delete` AFTER DELETE ON `tbl_import` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete ticket of product_id=', old.product_id, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_import', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_invoice`
--

DROP TABLE IF EXISTS `tbl_invoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_invoice` (
  `id` varchar(50) NOT NULL,
  `user_id` bigint NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` char(15) NOT NULL,
  `payment_amount` double unsigned NOT NULL,
  `ship_cost` double unsigned NOT NULL,
  `discount_amount` double unsigned DEFAULT '0',
  `tax` double unsigned DEFAULT '0',
  `payment_total` double unsigned NOT NULL,
  `payment_type` varchar(50) NOT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT '0',
  `order_status` enum('PENDING','WAIT_CONFIRMED','PREPARED','SHIPPED','RECEIVED','CANCELED','FAILED','IGNORED') NOT NULL DEFAULT 'PENDING',
  `note` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_invoice_insert` AFTER INSERT ON `tbl_invoice` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add invoice for user_id=', new.user_id, ', address:', new.address, ', phone:', new.phone, 
		', payment: ', new.payment_amount, ' - ', new.ship_cost, ' (ship) - ', new.discount_amount, ' (discount) - ', new.tax, ' (tax) =', new.payment_total,
        ', payment type:', new.payment_type, ' paid: [', new.is_paid, '], order status:', new.order_status, ', note:', new.note);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_invoice', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_invoice_update` BEFORE UPDATE ON `tbl_invoice` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_invoice_update` AFTER UPDATE ON `tbl_invoice` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update invoice for user_id=', old.user_id, ', address:', old.address, ', phone:', old.phone, 
		', payment: ', old.payment_amount, ' - ', old.ship_cost, ' (ship) - ', old.discount_amount, ' (discount) - ', old.tax, ' (tax) =', old.payment_total,
        ', payment type:', old.payment_type, ' paid: [', old.is_paid, '], order status:', old.order_status, ', note:', old.note,
        -- new
        '\n-> user_id=', new.user_id, ', address:', new.address, ', phone:', new.phone, 
		', payment: ', new.payment_amount, ' - ', new.ship_cost, ' (ship) - ', new.discount_amount, ' (discount) - ', new.tax, ' (tax) =', new.payment_total,
        ', payment type:', new.payment_type, ' paid: [', new.is_paid, '], order status:', new.order_status, ', note:', new.note);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete invoice of user_id=', new.user_id);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore invoice of user_id=', new.user_id);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_invoice', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_invoice_delete` AFTER DELETE ON `tbl_invoice` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete invoice of user_id=', old.user_id, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_invoice', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_label`
--

DROP TABLE IF EXISTS `tbl_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_label` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=47 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_label_insert` AFTER INSERT ON `tbl_label` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add label [', new.name, '], icon:', new.icon, ', title:', new.title, ', description:', new.description);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_label', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_label_update` BEFORE UPDATE ON `tbl_label` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_label_update` AFTER UPDATE ON `tbl_label` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update label [', old.name, '], icon:', old.icon, ', title:', old.title, ', description:', old.description,
		'\n-> label [', new.name, '], icon:', new.icon, ', title:', new.title, ', description:', new.description);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete label:', new.name);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore label:', new.name);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_label', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_label_delete` AFTER DELETE ON `tbl_label` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete label:', old.name, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_label', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_log_system`
--

DROP TABLE IF EXISTS `tbl_log_system`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_log_system` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `action_table` varchar(50) NOT NULL,
  `record_id` varchar(50) NOT NULL,
  `action_time` datetime NOT NULL,
  `action_by` varchar(100) NOT NULL DEFAULT 'system',
  `action_name` varchar(25) NOT NULL DEFAULT 'UPDATE',
  `action_description` longtext,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1192 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_product`
--

DROP TABLE IF EXISTS `tbl_product`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product` (
  `id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `brand_id` bigint NOT NULL,
  `category_id` bigint NOT NULL,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `released_date` date NOT NULL,
  `quantity_in_stock` int NOT NULL,
  `listed_price` double NOT NULL,
  `specifications` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci,
  `description_detail` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_insert` AFTER INSERT ON `tbl_product` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add product [', new.name, '], brand_id=', new.brand_id, ', category_id', new.category_id, 
		' (', new.released_date, '), listed price:', new.listed_price);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_product_update` BEFORE UPDATE ON `tbl_product` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_update` AFTER UPDATE ON `tbl_product` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    DECLARE in_action_name VARCHAR(10);
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update product [', old.name, '], brand_id=', old.brand_id, ', category_id', old.category_id, 
		' (', old.released_date, '), listed price:', old.listed_price,
        -- new
        '\n-> product [', new.name, '], brand_id=', new.brand_id, ', category_id', new.category_id, 
		' (', new.released_date, '), listed price:', new.listed_price);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete product[', new.name, '] of brand_id=', new.brand_id, ' - category_id=', new.category_id);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore product[', new.name, '] of brand_id=', new.brand_id, ' - category_id=', new.category_id);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_delete` AFTER DELETE ON `tbl_product` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete product[', old.name, '] of brand_id=', old.brand_id, ' - category_id=', old.category_id, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_product_accessory`
--

DROP TABLE IF EXISTS `tbl_product_accessory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_accessory` (
  `product_id` varchar(50) NOT NULL,
  `accessory_id` varchar(50) NOT NULL,
  PRIMARY KEY (`product_id`,`accessory_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_accessory_insert` AFTER INSERT ON `tbl_product_accessory` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Insert accessory [', new.accessory_id, '] into product [', new.product_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_accessory', new.accessory_id, 'ADD', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_accessory_delete` AFTER DELETE ON `tbl_product_accessory` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Remove accessory [', old.accessory_id, '] into product [', old.product_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_accessory', old.accessory_id, 'DESTROY', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_product_discount`
--

DROP TABLE IF EXISTS `tbl_product_discount`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_discount` (
  `product_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `discount_id` bigint NOT NULL,
  PRIMARY KEY (`product_id`,`discount_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_discount_insert` AFTER INSERT ON `tbl_product_discount` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Insert discount [', new.discount_id, '] into product [', new.product_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_discount', new.discount_id, 'ADD', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_discount_delete` AFTER DELETE ON `tbl_product_discount` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Remove discount [', old.discount_id, '] from product [', old.product_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_discount', old.discount_id, 'DESTROY', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_product_image`
--

DROP TABLE IF EXISTS `tbl_product_image`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_image` (
  `id` varchar(50) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `feedback_id` varchar(25) DEFAULT NULL,
  `url` varchar(255) NOT NULL,
  `type` enum('ADVERTISE','DETAIL','EXTRA','FEEDBACK') NOT NULL DEFAULT 'ADVERTISE',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_image_insert` AFTER INSERT ON `tbl_product_image` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add image for product_id=', new.product_id, ' [feedback_id:', new.feedback_id, '], url:', new.url, ', type:', new.type);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_image', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_product_image_update` BEFORE UPDATE ON `tbl_product_image` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_image_update` AFTER UPDATE ON `tbl_product_image` FOR EACH ROW BEGIN
    DECLARE in_action_name VARCHAR(10);
    declare in_action_description longtext;
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update image for product_id=', old.product_id, ' [feedback_id:', old.feedback_id, '], url:', old.url, ', type:', old.type,
		'\n-> image for product_id=', new.product_id, ' [feedback_id:', new.feedback_id, '], url:', new.url, ', type:', new.type);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete image of product_id=', new.product_id, ' - type', new.type);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore image of product_id=', new.product_id, ' - type', new.type);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_image', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_image_delete` AFTER DELETE ON `tbl_product_image` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete image of product_id=', old.product_id, ' - type', old.type, ' [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_image', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_product_label`
--

DROP TABLE IF EXISTS `tbl_product_label`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_label` (
  `product_id` varchar(50) NOT NULL,
  `label_id` bigint NOT NULL,
  PRIMARY KEY (`product_id`,`label_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_label_insert` AFTER INSERT ON `tbl_product_label` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Insert label [', new.label_id, '] into product [', new.product_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_label', new.label_id, 'ADD', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_label_delete` AFTER DELETE ON `tbl_product_label` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete label [', old.label_id, '] from product [', old.product_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_label', old.label_id, 'DESTROY', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_product_unit`
--

DROP TABLE IF EXISTS `tbl_product_unit`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_product_unit` (
  `id` varchar(25) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `product_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `cart_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci DEFAULT NULL,
  `invoice_id` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci DEFAULT NULL,
  `quantity` int unsigned NOT NULL,
  `price` double unsigned NOT NULL,
  `discount_price` double unsigned NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`,`product_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_unit_insert` AFTER INSERT ON `tbl_product_unit` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add unit of product_id=', new.product_id, ' [invoice:', new.invoice_id, ' | cart:', new.cart_id, '], value:', 
		new.quantity, ' * ', new.price, ' ~ ', new.discount_price);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_unit', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);

    if new.invoice_id is not null then
        update tbl_product
        set tbl_product.quantity_in_stock =
                if(quantity_in_stock - new.quantity >= 0, quantity_in_stock - new.quantity, quantity_in_stock)
        where tbl_product.id = new.product_id;
    end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_product_unit_update` BEFORE UPDATE ON `tbl_product_unit` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_unit_update` AFTER UPDATE ON `tbl_product_unit` FOR EACH ROW BEGIN
    DECLARE in_action_name VARCHAR(10);
    declare in_action_description longtext;
    
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update unit of product_id=', old.product_id, ' [invoice:', old.invoice_id, ' | cart:', old.cart_id, '], value:', 
		old.quantity, ' * ', old.price, ' ~ ', old.discount_price, 
        '\n-> unit of product_id=', new.product_id, ' [invoice:', new.invoice_id, ' | cart:', new.cart_id, '], value:', 
		new.quantity, ' * ', new.price, ' ~ ', new.discount_price);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete unit in [invoice_id:', new.invoice_id, ' | cart_id:', new.cart_id, ']');
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore unit in [invoice_id:', new.invoice_id, ' | cart_id:', new.cart_id, ']');
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_unit', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
    
    if new.invoice_id is not null then
        if new.quantity - old.quantity = 0 then -- change from cart => invoice
            update tbl_product
            set tbl_product.quantity_in_stock =
                    if(quantity_in_stock - new.quantity >= 0, quantity_in_stock - new.quantity, quantity_in_stock)
            where tbl_product.id = new.product_id;
        else -- update invoice quantity (return | fix_quantity)
            update tbl_product
            set tbl_product.quantity_in_stock = tbl_product.quantity_in_stock - new.quantity + old.quantity
            where tbl_product.id = new.product_id;
        end if;
    end if;
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_product_unit_delete` AFTER DELETE ON `tbl_product_unit` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete unit in [invoice_id:', old.invoice_id, ' | cart_id:', old.cart_id, '] [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_product_unit', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_refresh_token`
--

DROP TABLE IF EXISTS `tbl_refresh_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_refresh_token` (
  `code` varchar(50) NOT NULL,
  `user_id` bigint NOT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `expired_date` datetime NOT NULL,
  PRIMARY KEY (`code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `tbl_refresh_token_BEFORE_INSERT` BEFORE INSERT ON `tbl_refresh_token` FOR EACH ROW BEGIN

    set new.expired_date = adddate(now(), interval 2 day);

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_reset_password_token`
--

DROP TABLE IF EXISTS `tbl_reset_password_token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_reset_password_token` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `user_id` bigint NOT NULL,
  `token` varchar(100) NOT NULL,
  `expired_time` datetime NOT NULL,
  `created_date` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `tbl_role`
--

DROP TABLE IF EXISTS `tbl_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_role` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(25) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_role_insert` AFTER INSERT ON `tbl_role` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add role [', new.name, '] with desc:', new.description);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_role', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_role_update` BEFORE UPDATE ON `tbl_role` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_role_update` AFTER UPDATE ON `tbl_role` FOR EACH ROW BEGIN
    DECLARE in_action_name VARCHAR(10);
    declare in_action_description longtext;
    
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update role [', old.name, '] with desc:', old.description,
		'\n-> role [', new.name, '] with desc:', new.description);
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete role [', new.name, ']');
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore role [', new.name, ']');
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_role', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_role_delete` AFTER DELETE ON `tbl_role` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete role [', old.name, '] [Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_role', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL DEFAULT 'MALE',
  `date_of_birth` date NOT NULL DEFAULT '2000-01-01',
  `phone` char(13) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `email` varchar(50) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci DEFAULT NULL,
  `password` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL,
  `is_active` tinyint(1) NOT NULL DEFAULT '1',
  `created_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `modified_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT '0',
  `update_by` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_vietnamese_ci NOT NULL DEFAULT 'system',
  PRIMARY KEY (`id`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=25 DEFAULT CHARSET=utf8mb3 COLLATE=utf8mb3_vietnamese_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_user_insert` AFTER INSERT ON `tbl_user` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Add user:', new.name, ' - ', new.gender, ', dob:', new.date_of_birth, ' phone:', new.phone, ', email:', new.email);
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_user', new.id, 'INSERT', new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `before_user_update` BEFORE UPDATE ON `tbl_user` FOR EACH ROW BEGIN

    set new.modified_date = now();

    IF new.is_del = 1 THEN

        set new.deleted_date = now();

    END IF;

END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_user_update` AFTER UPDATE ON `tbl_user` FOR EACH ROW BEGIN
    DECLARE in_action_name VARCHAR(10);
    declare in_action_description longtext;
    SET in_action_name = 'UPDATE';
    set in_action_description = concat('Update user:', old.name, ' - ', old.gender, ', dob:', old.date_of_birth, ' phone:', old.phone, ', email:', old.email,
		'\n-> user:', new.name, ' - ', new.gender, ', dob:', new.date_of_birth, ' phone:', new.phone, ', email:', new.email);
		if new.is_active = 1 and old.is_active = 0 then 
			set in_action_description = concat(in_action_description, '\nActivate user!');
		end if;
		if new.is_active = 0 and old.is_active = 1 then 
			set in_action_description = concat(in_action_description, '\nBlock user!');
		end if;
    IF new.is_del = 1 and old.is_del = 0 THEN
        SET in_action_name = 'DELETE';
        set in_action_description = concat('Delete user:', new.name);
    END IF;
    IF new.is_del = 0 and old.is_del = 1 THEN
        SET in_action_name = 'RESTORE';
        set in_action_description = concat('Restore user:', user.name);
    END IF;
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_user', new.id, in_action_name, new.modified_date, new.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_user_delete` AFTER DELETE ON `tbl_user` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Delete user:', old.name, '[Absolutely]');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_user', old.id, 'DESTROY', now(), old.update_by, in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Table structure for table `tbl_user_role`
--

DROP TABLE IF EXISTS `tbl_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tbl_user_role` (
  `user_id` bigint NOT NULL,
  `role_id` int NOT NULL,
  PRIMARY KEY (`user_id`,`role_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_user_role_insert` AFTER INSERT ON `tbl_user_role` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Insert role [', new.role_id, '] into user [', new.user_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_user_role', new.role_id, 'ADD', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
/*!50003 CREATE*/ /*!50017 DEFINER=`root`@`%`*/ /*!50003 TRIGGER `after_user_role_delete` AFTER DELETE ON `tbl_user_role` FOR EACH ROW BEGIN
    declare in_action_description longtext;
    set in_action_description = concat('Remove role [', old.role_id, '] from user [', old.user_id, ']');
    INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by, action_description)
    VALUES ('tbl_user_role', old.role_id, 'DESTROY', now(), 'system', in_action_description);
END */;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;

--
-- Dumping events for database 'laptech'
--

--
-- Dumping routines for database 'laptech'
--
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistAddress` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistAddress`(IN in_user_id bigint(11), IN in_country varchar(100),

                                   IN in_line1 varchar(100), IN in_line2 varchar(100),

                                   IN in_line3 varchar(100), IN in_street varchar(100))
BEGIN

    select * from tbl_address

    where user_id=in_user_id 
		and country=in_country 
		and line1=in_line1 
		and line2=in_line2 
		and line3=in_line3 
		and street=in_street;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistBanner` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistBanner`(IN in_path varchar(255), IN in_type varchar(100),

                                  IN in_title varchar(100),

                                  IN in_link_product varchar(255), IN in_used_date date,

                                  IN in_ended_date date)
BEGIN

    select * from tbl_banner

    where path=in_path and type=in_type and title=in_title and link_product=in_link_product and used_date=in_used_date and ended_date=in_ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistBrand` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistBrand`(IN in_name varchar(50), IN in_country varchar(50),

                                 IN in_establish_date date, IN in_logo varchar(255))
BEGIN

    select * from tbl_brand

    where name=in_name and country=in_country and establish_date=in_establish_date and logo=in_logo;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistCart`(IN in_id varchar(25), IN in_user_id bigint(11))
BEGIN

    select * from tbl_cart

    where id=in_id and user_id=in_user_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistCategory`(IN in_name varchar(50), IN in_image varchar(255),

                                    IN in_description varchar(255))
BEGIN

    select * from tbl_category

    where name=in_name and image=in_image and description=in_description;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistComment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistComment`(IN in_root_comment_id varchar(25),

                                   IN in_product_id varchar(50),

                                   IN in_username varchar(100), IN in_phone char(15),

                                   IN in_content varchar(255))
BEGIN

    select * from tbl_comment

    where root_comment_id=in_root_comment_id and product_id=in_product_id and username=in_username and phone=in_phone and content=in_content;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistDiscount`(IN in_code varchar(20), IN in_rate float,

                                    IN in_applied_type enum ('PRODUCT', 'PURCHASE'),

                                    IN in_max_amount decimal(21, 4),

                                    IN in_applied_date datetime,

                                    IN in_ended_date datetime)
BEGIN

    select * from tbl_discount

    where code=in_code and rate=in_rate and applied_type=in_applied_type and max_amount=in_max_amount and applied_date=in_applied_date and ended_date=in_ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistFeedback`(IN in_content varchar(255),

                                    IN in_rating_point tinyint(3),

                                    IN in_product_id varchar(50), IN in_user_id bigint)
BEGIN

    select * from tbl_feedback

    where content=in_content and rating_point=in_rating_point and product_id=in_product_id and user_id=in_user_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistImport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistImport`(IN in_product_id varchar(50), IN in_quantity bigint,

                                  IN in_imported_price decimal(21, 4),

                                  IN in_imported_date datetime)
BEGIN

    select * from tbl_import

    where product_id=in_product_id and quantity=in_quantity and imported_price=in_imported_price and imported_date=in_imported_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistInvoice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistInvoice`(IN in_user_id bigint, IN in_address varchar(255),

                                   IN in_phone char(15),

                                   IN in_payment_amount decimal(21, 4),

                                   IN in_ship_cost decimal(21, 4),

                                   IN in_discount_amount decimal(21, 4),

                                   IN in_tax decimal(21, 4),

                                   IN in_payment_total decimal(21, 4),

                                   IN in_payment_type varchar(50),

                                   IN in_is_paid tinyint(1),

                                   IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED', 'IGNORED'),

                                   IN in_note varchar(255))
BEGIN

    select * from tbl_invoice

    where user_id=in_user_id and address=in_address and phone=in_phone and payment_amount=in_payment_amount and ship_cost=in_ship_cost and discount_amount=in_discount_amount and tax=in_tax

      and payment_total=in_payment_total and payment_type=in_payment_type and is_paid=in_is_paid and order_status=in_order_status and note=in_note;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistLabel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistLabel`(IN in_name varchar(25), IN in_icon varchar(255),

                                 IN in_title varchar(50),

                                 IN in_description varchar(255))
BEGIN

    select * from tbl_label

    where name=in_name and icon=in_icon and title=in_title and description=in_description;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistProduct` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistProduct`(IN in_brand_id bigint, IN in_category_id bigint,

                                   IN in_name varchar(100), IN in_released_date date,

                                   IN in_quantity_in_stock int,

                                   IN in_listed_price decimal(21, 4))
BEGIN

    select * from tbl_product

    where brand_id=in_brand_id and category_id=in_category_id and name=in_name and released_date=in_released_date and quantity_in_stock=in_quantity_in_stock and listed_price=in_listed_price;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistProductImage` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistProductImage`(IN in_product_id varchar(50),

                                        IN in_feedback_id bigint,

                                        IN in_url varchar(255),

                                        IN in_type enum ('ADVERTISE', 'DETAIL', 'EXTRA', 'FEEDBACK'))
BEGIN

    select * from tbl_product_image

    where product_id=in_product_id and feedback_id=in_feedback_id and url=in_url and type=in_type;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistProductUnit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistProductUnit`(IN in_cart_id varchar(50),

                                       IN in_invoice_id varchar(50),

                                       IN in_product_id varchar(50), IN in_quantity int,

                                       IN in_price decimal(21, 4),

                                       IN in_discount_price decimal(21, 4))
BEGIN

    select * from tbl_product_unit

    where cart_id=in_cart_id and invoice_id=in_invoice_id and product_id=in_product_id and quantity=in_quantity and price=in_price and discount_price=in_discount_price;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistRole`(IN in_name varchar(25), IN in_description varchar(100))
BEGIN

    select * from tbl_role

    where name=in_name and description=in_description;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CheckExistUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CheckExistUser`(IN in_name varchar(100),

                                IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),

                                IN in_date_of_birth date, IN in_phone char(15),

                                IN in_email varchar(100))
BEGIN

    select * from tbl_user

    where name=in_name and gender=in_gender and date_of_birth=in_date_of_birth and phone=in_phone and email=in_email;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAddressWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAddressWithCondition`(IN in_user_id bigint,

                                           IN in_country varchar(25),

                                           IN in_line1 varchar(25),

                                           IN in_line2 varchar(25),

                                           IN in_line3 varchar(25),

                                           IN in_street varchar(100),

                                           IN in_is_default tinyint(1),

                                           IN in_created_date date,

                                           IN in_modified_date date,

                                           IN in_deleted_date date,

                                           IN in_is_del tinyint(1),

                                           IN in_update_by varchar(100))
BEGIN

    set in_country = concat('%', in_country, '%');

    set in_line1 = concat('%', in_line1, '%');

    set in_line2 = concat('%', in_line2, '%');

    set in_line3 = concat('%', in_line3, '%');

    set in_street = concat('%', in_street, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_address WHERE ((in_user_id is null) or (user_id=in_user_id))

                                       and ((in_country is null) or (country like in_country))

                                       and ((in_line1 is null) or (line1 like in_line1))

                                       and ((in_line2 is null) or (line2 like in_line2))

                                       and ((in_line3 is null) or (line3 like in_line3))

                                       and ((in_street is null) or (street like in_street))

                                       and ((in_is_default is null) or (is_default=in_is_default))

                                       and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                       and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                       and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                       and ((in_is_del is null) or (is_del=in_is_del))

                                       and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllAddress` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllAddress`()
BEGIN

    SELECT COUNT(*) FROM tbl_address;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllBanner` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllBanner`()
BEGIN

    SELECT COUNT(*) FROM tbl_banner;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllBrand` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllBrand`()
BEGIN

    SELECT COUNT(*) FROM tbl_brand;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllCategory`()
BEGIN

    SELECT COUNT(*) FROM tbl_category;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllComment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllComment`()
BEGIN

    SELECT COUNT(*) FROM tbl_comment;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllDiscount`()
BEGIN

    SELECT COUNT(*) FROM tbl_discount;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllFeedback`()
BEGIN

    SELECT COUNT(*) FROM tbl_feedback;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllImport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllImport`()
BEGIN

    SELECT COUNT(*) FROM tbl_import;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllInvoice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllInvoice`()
BEGIN

    SELECT COUNT(*) FROM tbl_invoice;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllLabel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllLabel`()
BEGIN

    SELECT COUNT(*) FROM tbl_label;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllLogSystem` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllLogSystem`()
BEGIN

    SELECT COUNT(*) FROM tbl_log_system;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllProduct` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllProduct`()
BEGIN

    SELECT COUNT(*) FROM tbl_product;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllProductImage` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllProductImage`()
BEGIN

    SELECT COUNT(*) FROM tbl_product_image;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllProductUnit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllProductUnit`()
BEGIN

    SELECT COUNT(*) FROM tbl_product_unit;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllRefreshToken` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllRefreshToken`()
BEGIN

    SELECT COUNT(*) FROM tbl_refresh_token;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllRole`()
BEGIN

    SELECT COUNT(*) FROM tbl_role;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountAllUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountAllUser`()
BEGIN

    SELECT COUNT(*) FROM tbl_user;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountBannerWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountBannerWithCondition`(IN in_path varchar(255),

                                          IN in_type varchar(50),

                                          IN in_title varchar(100),

                                          IN in_link_product varchar(255),

                                          IN in_used_date date, IN in_ended_date date,

                                          IN in_created_date date,

                                          IN in_modified_date date,

                                          IN in_deleted_date date,

                                          IN in_is_del tinyint(1),

                                          IN in_update_by varchar(100))
BEGIN

    set in_path = concat('%', in_path, '%');

    set in_type = concat('%', in_type, '%');

    set in_title = concat('%', in_title, '%');

    set in_link_product = concat('%', in_link_product, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_banner WHERE ((in_path is null) or (path like in_path))

                                      and ((in_type is null) or (type like in_type))

                                      and ((in_title is null) or (title like in_title))

                                      and ((in_link_product is null) or (link_product like in_link_product))

                                      and ((in_used_date is null) or (used_date=in_used_date))

                                      and ((in_ended_date is null) or (ended_date=in_ended_date))

                                      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                      and ((in_is_del is null) or (is_del=in_is_del))

                                      and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountBrandWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountBrandWithCondition`(IN in_name varchar(100),

                                         IN in_country varchar(100),

                                         IN in_establish_year year,

                                         IN in_logo varchar(255),

                                         IN in_created_date date,

                                         IN in_modified_date date,

                                         IN in_deleted_date date,

                                         IN in_is_del tinyint(1),

                                         IN in_update_by varchar(100))
BEGIN

    set in_name = concat('%', in_name, '%');

    set in_country = concat('%', in_country, '%');

    set in_logo = concat('%', in_logo, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_brand WHERE ((in_name is null) or (name like in_name))

                                     and ((in_country is null) or (country like in_country))

                                     and ((in_establish_year is null) or (year(establish_date)=in_establish_year))

                                     and ((in_logo is null) or (logo like in_logo))

                                     and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                     and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                     and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                     and ((in_is_del is null) or (is_del=in_is_del))

                                     and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountCategoryWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountCategoryWithCondition`(IN in_name varchar(50),

                                            IN in_image varchar(255),

                                            IN in_description varchar(255),

                                            IN in_created_date date,

                                            IN in_modified_date date,

                                            IN in_deleted_date date,

                                            IN in_is_del tinyint(1),

                                            IN in_update_by varchar(100))
BEGIN

    set in_name = concat('%', in_name, '%');

    set in_image = concat('%', in_image, '%');

    set in_description = concat('%', in_description, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_category WHERE ((in_name is null) or (name like in_name))

                                        and ((in_image is null) or (image like in_image))

                                        and ((in_description is null) or (description like in_description))

                                        and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                        and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                        and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                        and ((in_is_del is null) or (is_del=in_is_del))

                                        and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountCommentWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountCommentWithCondition`(IN in_root_comment_id varchar(25),

                                           IN in_product_id varchar(50),

                                           IN in_username varchar(100),

                                           IN in_phone char(15),

                                           IN in_content varchar(255),

                                           IN in_created_date date,

                                           IN in_modified_date date,

                                           IN in_deleted_date date,

                                           IN in_is_del tinyint(1),

                                           IN in_update_by varchar(100))
BEGIN

    set in_username = concat('%', in_username, '%');

    set in_content = concat('%', in_content, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_comment WHERE ((in_root_comment_id is null) or (root_comment_id=in_root_comment_id))

                                       and ((in_product_id is null) or (product_id=in_product_id))

                                       and ((in_username is null) or (username like in_username))

                                       and ((in_phone is null) or (phone=in_phone))

                                       and ((in_content is null) or (content like in_content))

                                       and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                       and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                       and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                       and ((in_is_del is null) or (is_del=in_is_del))

                                       and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountDiscountWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountDiscountWithCondition`(IN in_code varchar(20), IN in_rate float,

                                            IN in_max_amount decimal(19, 4),

                                            IN in_applied_type enum ('PRODUCT', 'PURCHASE'),

                                            IN in_applied_date datetime,

                                            IN in_ended_date datetime,

                                            IN in_created_date date,

                                            IN in_modified_date date,

                                            IN in_deleted_date date,

                                            IN in_is_del tinyint(1),

                                            IN in_update_by varchar(100))
BEGIN

    set in_code = concat('%', in_code, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_discount WHERE ((in_code is null) or (code like in_code))

                                        and ((in_rate is null) or (rate=in_rate))

                                        and ((in_max_amount is null) or (max_amount=in_max_amount))

                                        and ((in_applied_type is null) or (applied_type=in_applied_type))

                                        and ((in_applied_date is null) or (applied_date=in_applied_date))

                                        and ((in_ended_date is null) or (ended_date=in_ended_date))

                                        and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                        and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                        and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                        and ((in_is_del is null) or (is_del=in_is_del))

                                        and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountFeedbackWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountFeedbackWithCondition`(IN in_product_id varchar(50),

                                            IN in_user_id bigint,

                                            IN in_content varchar(255),

                                            IN in_rating_point int,

                                            IN in_created_date date,

                                            IN in_modified_date date,

                                            IN in_deleted_date date,

                                            IN in_is_del tinyint(1),

                                            IN in_update_by varchar(100))
BEGIN

    set in_content = concat('%', in_content, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_feedback WHERE ((in_product_id is null) or (product_id=in_product_id))

                                        and ((in_user_id is null) or (user_id=in_user_id))

                                        and ((in_content is null) or (content like in_content))

                                        and ((in_rating_point is null) or (rating_point=in_rating_point))

                                        and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                        and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                        and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                        and ((in_is_del is null) or (is_del=in_is_del))

                                        and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountImportWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountImportWithCondition`(IN in_product_id varchar(50),

                                          IN in_quantity bigint,

                                          IN in_imported_price decimal(21, 4),

                                          IN in_imported_date date,

                                          IN in_created_date date,

                                          IN in_modified_date date,

                                          IN in_deleted_date date,

                                          IN in_is_del tinyint(1),

                                          IN in_update_by varchar(100))
BEGIN

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_import WHERE ((in_product_id is null) or (product_id=in_product_id))

                                      and ((in_quantity is null) or (quantity=in_quantity))

                                      and ((in_imported_price is null) or (imported_price=in_imported_price))

                                      and ((in_imported_date is null) or (cast(imported_date as date)=in_imported_date))

                                      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                      and ((in_is_del is null) or (is_del=in_is_del))

                                      and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountInvoiceWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountInvoiceWithCondition`(IN in_user_id bigint,

                                           IN in_address varchar(255),

                                           IN in_phone char(15),

                                           IN in_payment_amount decimal(21, 4),

                                           IN in_ship_cost decimal(21, 4),

                                           IN in_discount_amount decimal(21, 4),

                                           IN in_tax decimal(21, 4),

                                           IN in_payment_total decimal(21, 4),

                                           IN in_payment_type varchar(50),

                                           IN in_is_paid tinyint(1),

                                           IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'),

                                           IN in_note varchar(255),

                                           IN in_created_date date,

                                           IN in_modified_date date,

                                           IN in_deleted_date date,

                                           IN in_is_del tinyint(1),

                                           IN in_update_by varchar(100))
BEGIN

    set in_address = concat('%', in_address, '%');

    set in_payment_type = concat('%', in_payment_type, '%');

    set in_note = concat('%', in_note, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_invoice WHERE ((in_user_id is null) or (user_id=in_user_id))

                                       and ((in_address is null) or (address like in_address))

                                       and ((in_phone is null) or (phone=in_phone))

                                       and ((in_payment_amount is null) or (payment_amount=in_payment_amount))

                                       and ((in_ship_cost is null) or (ship_cost=in_ship_cost))

                                       and ((in_discount_amount is null) or (discount_amount=in_discount_amount))

                                       and ((in_tax is null) or (tax=in_tax))

                                       and ((in_payment_total is null) or (payment_total=in_payment_total))

                                       and ((in_payment_type is null) or (payment_type like in_payment_type))

                                       and ((in_is_paid is null) or (is_paid=in_is_paid))

                                       and ((in_order_status is null) or (order_status=in_order_status))

                                       and ((in_note is null) or (note like in_note))

                                       and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                       and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                       and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                       and ((in_is_del is null) or (is_del=in_is_del))

                                       and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountLabelWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountLabelWithCondition`(IN in_name varchar(25),

                                         IN in_icon varchar(255),

                                         IN in_title varchar(50),

                                         IN in_description varchar(255),

                                         IN in_created_date date,

                                         IN in_modified_date date,

                                         IN in_deleted_date date,

                                         IN in_is_del tinyint(1),

                                         IN in_update_by varchar(100))
BEGIN

    set in_name = concat('%', in_name, '%');

    set in_icon = concat('%', in_icon, '%');

    set in_title = concat('%', in_title, '%');

    set in_description = concat('%', in_description, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_label WHERE ((in_name is null) or (name like in_name))

                                     and ((in_icon is null) or (icon like in_icon))

                                     and ((in_title is null) or (title like in_title))

                                     and ((in_description is null) or (description like in_description))

                                     and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                     and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                     and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                     and ((in_is_del is null) or (is_del=in_is_del))

                                     and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountLogSystemWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountLogSystemWithCondition`(
											 IN in_action_table varchar(50),

                                             IN in_action_date date,

                                             IN in_action_by varchar(100),

                                             IN in_action_name varchar(25),
                                             IN in_action_description longtext)
BEGIN

    set in_action_table = concat('%', in_action_table, '%');

    set in_action_by = concat('%', in_action_by, '%');

    set in_action_name = concat('%', in_action_name, '%');
    set in_action_description = concat('%', in_action_description, '%');



    SELECT COUNT(*) FROM tbl_log_system

    WHERE ((in_action_table is null) or (action_table like in_action_table))

      and ((in_action_date is null) or (cast(action_time as date) = in_action_date))

      and ((in_action_by is null) or (action_by like in_action_by))

      and ((in_action_name is null) or (action_name like in_action_name))
      and ((in_action_description is null) or (action_description like in_action_description));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountProductImageWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountProductImageWithCondition`(IN in_product_id varchar(50),

                                                IN in_feedback_id varchar(25),

                                                IN in_url varchar(255),

                                                IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),

                                                IN in_created_date date,

                                                IN in_modified_date date,

                                                IN in_deleted_date date,

                                                IN in_is_del tinyint(1),

                                                IN in_update_by varchar(100))
BEGIN

    set in_url = concat('%', in_url, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_product_image WHERE ((in_product_id is null) or (product_id=in_product_id))

                                             and ((in_feedback_id is null) or (feedback_id=in_feedback_id))

                                             and ((in_url is null) or (url like in_url))

                                             and ((in_type is null) or (type=in_type))

                                             and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                             and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                             and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                             and ((in_is_del is null) or (is_del=in_is_del))

                                             and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountProductUnitWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountProductUnitWithCondition`(IN in_product_id varchar(50),

                                               IN in_cart_id varchar(50),

                                               IN in_invoice_id varchar(50),

                                               IN in_quantity int,

                                               IN in_price decimal(21, 4),

                                               IN in_discount_price decimal(21, 4),

                                               IN in_created_date date,

                                               IN in_modified_date date,

                                               IN in_deleted_date date,

                                               IN in_is_del tinyint(1),

                                               IN in_update_by varchar(100))
BEGIN

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_product_unit WHERE ((in_product_id is null) or (product_id=in_product_id))

                                            and ((in_cart_id is null) or (cart_id=in_cart_id))

                                            and ((in_invoice_id is null) or (invoice_id=in_invoice_id))

                                            and ((in_quantity is null) or (quantity=in_quantity))

                                            and ((in_price is null) or (price=in_price))

                                            and ((in_discount_price is null) or (discount_price=in_discount_price))

                                            and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                            and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                            and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                            and ((in_is_del is null) or (is_del=in_is_del))

                                            and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountProductWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountProductWithCondition`(IN in_brand_id bigint,

                                           IN in_category_id bigint,

                                           IN in_name varchar(100),

                                           IN in_released_date date,

                                           IN in_quantity_in_stock int,

                                           IN in_listed_price decimal(21, 4),

                                           IN in_created_date date,

                                           IN in_modified_date date,

                                           IN in_deleted_date date,

                                           IN in_is_del tinyint(1),

                                           IN in_update_by varchar(100))
BEGIN

    set in_name = concat('%', in_name, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_product WHERE ((in_brand_id is null) or (brand_id=in_brand_id))

                                       and ((in_category_id is null) or (category_id=in_category_id))

                                       and ((in_name is null) or (name like in_name))

                                       and ((in_released_date is null) or (released_date=in_released_date))

                                       and ((in_quantity_in_stock is null) or (quantity_in_stock=in_quantity_in_stock))

                                       and ((in_listed_price is null) or (listed_price=in_listed_price))

                                       and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                       and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                       and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                       and ((in_is_del is null) or (is_del=in_is_del))

                                       and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountRefreshTokenInDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountRefreshTokenInDate`(IN in_date date)
BEGIN

    SELECT COUNT(*) FROM tbl_refresh_token WHERE CAST(created_date AS date) = in_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountRoleWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountRoleWithCondition`(IN in_name varchar(25),

                                        IN in_description varchar(100),

                                        IN in_created_date datetime,

                                        IN in_modified_date datetime,

                                        IN in_deleted_date datetime,

                                        IN in_is_del tinyint(1),

                                        IN in_update_by varchar(100))
BEGIN

    set in_name = concat('%', in_name, '%');

    set in_description = concat('%', in_description, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_role WHERE ((in_name is null) or (name like in_name))

                                    and ((in_description is null) or (description like in_description))

                                    and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                    and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                    and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                    and ((in_is_del is null) or (is_del=in_is_del))

                                    and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_CountUserWithCondition` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_CountUserWithCondition`(IN in_name varchar(100),

                                        IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),

                                        IN in_date_of_birth date, IN in_phone char(15),

                                        IN in_email varchar(50),

                                        IN in_is_active tinyint(1),

                                        IN in_created_date date,

                                        IN in_modified_date date,

                                        IN in_deleted_date date,

                                        IN in_is_del tinyint(1),

                                        IN in_update_by varchar(100))
BEGIN

    set in_name = concat('%', in_name, '%');

    set in_email = concat('%', in_email, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT COUNT(*) FROM tbl_user WHERE ((in_name is null) or (name like in_name))

                                    and ((in_gender is null) or (gender=in_gender))

                                    and ((in_date_of_birth is null) or (date_of_birth=in_date_of_birth))

                                    and ((in_phone is null) or (phone=in_phone))

                                    and ((in_email is null) or (email like in_email))

                                    and ((in_is_active is null) or (is_active=in_is_active))

                                    and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

                                    and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

                                    and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

                                    and ((in_is_del is null) or (is_del=in_is_del))

                                    and ((in_update_by is null) or (update_by like in_update_by));

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteAddress` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteAddress`(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_address SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteBanner` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteBanner`(IN in_id bigint, IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_banner SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteBrand` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteBrand`(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;

    UPDATE tbl_brand SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteCart`(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_cart SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteCategory`(IN in_id bigint, IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_category SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteComment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteComment`(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_comment SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteDiscount`(IN in_id bigint, IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_discount SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteFeedback`(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_feedback SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteImport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteImport`(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_import SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteInvoice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteInvoice`(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_invoice SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteLabel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteLabel`(IN in_id bigint, IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_label SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteProduct` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteProduct`(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN

	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;
    UPDATE tbl_product SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteProductAccessory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteProductAccessory`(IN in_product_id varchar(255), IN in_accessory_id varchar(255))
BEGIN

    DELETE FROM tbl_product_accessory WHERE product_id=in_product_id and accessory_id=in_accessory_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteProductDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteProductDiscount`(IN in_product_id varchar(255), IN in_discount_id bigint)
BEGIN

    DELETE FROM tbl_product_discount WHERE product_id=in_product_id and discount_id=in_discount_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteProductImage` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteProductImage`(IN in_id varchar(50), IN in_update_by varchar(100))
BEGIN
	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;

    UPDATE tbl_product_image SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteProductLabel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteProductLabel`(IN in_product_id varchar(255), IN in_label_id bigint)
BEGIN

    DELETE FROM tbl_product_label WHERE product_id=in_product_id and label_id=in_label_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteProductUnit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteProductUnit`(IN in_id varchar(25), IN in_update_by varchar(100))
BEGIN
	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;

    UPDATE tbl_product_unit SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteRole`(IN in_id int, IN in_update_by varchar(100))
BEGIN
	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;

    UPDATE tbl_role SET is_del=true, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteUser`(IN in_id bigint, IN in_update_by varchar(100))
BEGIN
	IF in_update_by IS NULL 
    THEN 
		SET in_update_by = 'system'; 
    END IF;

    UPDATE tbl_user SET is_del=true, update_by=in_update_by WHERE id=in_id and is_active=0;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_DeleteUserRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_DeleteUserRole`(IN in_user_id bigint, IN in_role_id bigint)
BEGIN

    DELETE FROM tbl_user_role WHERE user_id=in_user_id and role_id=in_role_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAccessoryByProductId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAccessoryByProductId`(IN in_product_id varchar(50))
BEGIN

	SELECT p.* 
	FROM laptech.tbl_product_accessory pa 
	join laptech.tbl_product p 
	on pa.accessory_id collate utf8_general_ci = p.id 
	WHERE pa.product_id = in_product_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAddressById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAddressById`(IN in_id varchar(25))
BEGIN

    SELECT * FROM tbl_address WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAddressByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAddressByUserId`(IN in_user_id bigint)
BEGIN

    SELECT * FROM tbl_address WHERE user_id=in_user_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllAddress` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllAddress`(IN in_sort_by varchar(25),

                                IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_address where is_del=false;

    end if;



    SELECT * FROM tbl_address

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'user_id' THEN user_id

                    WHEN 'country' THEN country

                    WHEN 'line1' THEN line1

                    WHEN 'line2' THEN line2

                    WHEN 'line3' THEN line3

                    WHEN 'street' THEN street

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'user_id' THEN user_id

                    WHEN 'country' THEN country

                    WHEN 'line1' THEN line1

                    WHEN 'line2' THEN line2

                    WHEN 'line3' THEN line3

                    WHEN 'street' THEN street

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllBanners` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllBanners`(IN in_sort_by varchar(25),

                                IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_banner where is_del=false;

    end if;



    SELECT * FROM tbl_banner

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'path' THEN path

                    WHEN 'type' THEN type

                    WHEN 'title' THEN title

                    WHEN 'link_product' THEN link_product

                    WHEN 'used_date' THEN used_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'path' THEN path

                    WHEN 'type' THEN type

                    WHEN 'title' THEN title

                    WHEN 'link_product' THEN link_product

                    WHEN 'used_date' THEN used_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllBrands` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllBrands`(IN in_sort_by varchar(25),

                               IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                               IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_brand where is_del=false;

    end if;



    SELECT * FROM tbl_brand

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'country' THEN country

                    WHEN 'establish_date' THEN establish_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'country' THEN country

                    WHEN 'establish_date' THEN establish_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllCategories` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllCategories`(IN in_sort_by varchar(25),

                                   IN in_sort_dir enum ('ASC', 'DESC'),

                                   IN in_offset int, IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_category where is_del=false;

    end if;



    SELECT * FROM tbl_category

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllComments` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllComments`(IN in_sort_by varchar(25),

                                 IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                 IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_comment where is_del=false;

    end if;



    SELECT * FROM tbl_comment

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'root_comment_id' THEN root_comment_id

                    WHEN 'product_id' THEN product_id

                    WHEN 'username' THEN username

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'root_comment_id' THEN root_comment_id

                    WHEN 'product_id' THEN product_id

                    WHEN 'username' THEN username

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllDiscounts` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllDiscounts`(IN in_sort_by varchar(25),

                                  IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                  IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_discount where is_del=false;

    end if;



    SELECT * FROM tbl_discount

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'code' THEN code

                    WHEN 'rate' THEN rate

                    WHEN 'max_amount' THEN max_amount

                    WHEN 'applied_type' THEN applied_type

                    WHEN 'applied_date' THEN applied_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'code' THEN code

                    WHEN 'rate' THEN rate

                    WHEN 'max_amount' THEN max_amount

                    WHEN 'applied_type' THEN applied_type

                    WHEN 'applied_date' THEN applied_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllFeedbacks` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllFeedbacks`(IN in_sort_by varchar(25),

                                  IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                  IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_feedback where is_del=false;

    end if;



    SELECT * FROM tbl_feedback

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'user_id' THEN user_id

                    WHEN 'rating_point' THEN rating_point

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'user_id' THEN user_id

                    WHEN 'rating_point' THEN rating_point

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllImports` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllImports`(IN in_sort_by varchar(25),

                                IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_import where is_del=false;

    end if;



    SELECT * FROM tbl_import

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'imported_price' THEN imported_price

                    WHEN 'imported_date' THEN imported_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'imported_price' THEN imported_price

                    WHEN 'imported_date' THEN imported_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllInvoices` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllInvoices`(IN in_sort_by varchar(25),

                                 IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                 IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_invoice where is_del=false;

    end if;



    SELECT * FROM tbl_invoice

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'user_id' THEN user_id

                    WHEN 'address' THEN address

                    WHEN 'phone' THEN phone

                    WHEN 'payment_amount' THEN payment_amount

                    WHEN 'ship_cost' THEN ship_cost

                    WHEN 'discount_amount' THEN discount_amount

                    WHEN 'tax' THEN tax

                    WHEN 'payment_total' THEN payment_total

                    WHEN 'payment_type' THEN payment_type

                    WHEN 'is_paid' THEN is_paid

                    WHEN 'order_status' THEN order_status

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'user_id' THEN user_id

                    WHEN 'address' THEN address

                    WHEN 'phone' THEN phone

                    WHEN 'payment_amount' THEN payment_amount

                    WHEN 'ship_cost' THEN ship_cost

                    WHEN 'discount_amount' THEN discount_amount

                    WHEN 'tax' THEN tax

                    WHEN 'payment_total' THEN payment_total

                    WHEN 'payment_type' THEN payment_type

                    WHEN 'is_paid' THEN is_paid

                    WHEN 'order_status' THEN order_status

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllLabels` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllLabels`(IN in_sort_by varchar(25),

                               IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                               IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_label where is_del=false;

    end if;



    SELECT * FROM tbl_label

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'icon' THEN icon

                    WHEN 'title' THEN title

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'icon' THEN icon

                    WHEN 'title' THEN title

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllLogSystems` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllLogSystems`(IN in_sort_by varchar(25),

                                   IN in_sort_dir enum ('ASC', 'DESC'),

                                   IN in_offset int, IN in_count int)
BEGIN

    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_log_system;

    end if;



    SELECT * FROM tbl_log_system

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'action_table' THEN action_table

                    WHEN 'action_date' THEN action_time

                    WHEN 'action_by' THEN action_by

                    WHEN 'action_name' THEN action_name

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'action_table' THEN action_table

                    WHEN 'action_date' THEN action_time

                    WHEN 'action_by' THEN action_by

                    WHEN 'action_name' THEN action_name

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllProductImages` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllProductImages`(IN in_sort_by varchar(25),

                                      IN in_sort_dir enum ('ASC', 'DESC'),

                                      IN in_offset int, IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_product_image where is_del=false;

    end if;



    SELECT * FROM tbl_product_image

    WHERE is_del = false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'feedback_id' THEN feedback_id

                    WHEN 'type' THEN type

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'feedback_id' THEN feedback_id

                    WHEN 'type' THEN type

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllProducts` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllProducts`(IN in_sort_by varchar(25),

                                 IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                                 IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_product where is_del=false;

    end if;



    SELECT * FROM tbl_product

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'brand_id' THEN brand_id

                    WHEN 'category_id' THEN category_id

                    WHEN 'name' THEN name

                    WHEN 'released_date' THEN released_date

                    WHEN 'quantity_in_stock' THEN quantity_in_stock

                    WHEN 'listed_price' THEN listed_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'brand_id' THEN brand_id

                    WHEN 'category_id' THEN category_id

                    WHEN 'name' THEN name

                    WHEN 'released_date' THEN released_date

                    WHEN 'quantity_in_stock' THEN quantity_in_stock

                    WHEN 'listed_price' THEN listed_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllProductUnits` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllProductUnits`(IN in_sort_by varchar(25),

                                     IN in_sort_dir enum ('ASC', 'DESC'),

                                     IN in_offset int, IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_product_unit where is_del=false;

    end if;



    SELECT * FROM tbl_product_unit

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'invoice_id' THEN invoice_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'price' THEN price

                    WHEN 'discount_price' THEN discount_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'invoice_id' THEN invoice_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'price' THEN price

                    WHEN 'discount_price' THEN discount_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllRoles` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllRoles`(IN in_sort_by varchar(25),

                              IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                              IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_role where is_del=false;

    end if;



    SELECT * FROM tbl_role

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindAllUsers` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindAllUsers`(IN in_sort_by varchar(25),

                              IN in_sort_dir enum ('ASC', 'DESC'), IN in_offset int,

                              IN in_count int)
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    if in_offset is null then

        set in_offset = 0;

    end if;

    if in_count is null then

        select count(*) into in_count from tbl_user where is_del=false;

    end if;



    SELECT * FROM tbl_user

    WHERE is_del=false

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'gender' THEN gender

                    WHEN 'date_of_birth' THEN date_of_birth

                    WHEN 'email' THEN email

                    WHEN 'is_active' THEN is_active

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'gender' THEN gender

                    WHEN 'date_of_birth' THEN date_of_birth

                    WHEN 'email' THEN email

                    WHEN 'is_active' THEN is_active

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC

    LIMIT in_offset, in_count;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindBannerByDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindBannerByDate`(IN in_date date)
BEGIN

    select * from tbl_banner where in_date between used_date and ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindBannerByDateRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindBannerByDateRange`(IN in_started_date date, IN in_ended_date date)
BEGIN

    select * from tbl_banner where ended_date >= in_started_date or used_date <= in_ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindBannerByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindBannerByFilter`(IN in_path varchar(255), IN in_type varchar(50),

                                    IN in_title varchar(100),

                                    IN in_link_product varchar(255),

                                    IN in_used_date date, IN in_ended_date date,

                                    IN in_created_date date, IN in_modified_date date,

                                    IN in_deleted_date date, IN in_is_del tinyint(1),

                                    IN in_update_by varchar(100),

                                    IN in_sort_by varchar(25),

                                    IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_path = concat('%', in_path, '%');

    set in_type = concat('%', in_type, '%');

    set in_title = concat('%', in_title, '%');

    set in_link_product = concat('%', in_link_product, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_banner

    WHERE ((in_path is null) or (path like in_path))

      and ((in_type is null) or (type like in_type))

      and ((in_title is null) or (title like in_title))

      and ((in_link_product is null) or (link_product like in_link_product))

      and ((in_used_date is null) or (used_date=in_used_date))

      and ((in_ended_date is null) or (ended_date=in_ended_date))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'path' THEN path

                    WHEN 'type' THEN type

                    WHEN 'title' THEN title

                    WHEN 'link_product' THEN link_product

                    WHEN 'used_date' THEN used_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'path' THEN path

                    WHEN 'type' THEN type

                    WHEN 'title' THEN title

                    WHEN 'link_product' THEN link_product

                    WHEN 'used_date' THEN used_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindBannerById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindBannerById`(IN in_id bigint)
BEGIN

    SELECT * FROM tbl_banner WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindBrandByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindBrandByFilter`(IN in_name varchar(100), IN in_country varchar(100),

                                   IN in_establish_year year, IN in_logo varchar(255),

                                   IN in_created_date datetime,

                                   IN in_modified_date datetime,

                                   IN in_deleted_date datetime, IN in_is_del tinyint(1),

                                   IN in_update_by varchar(100),

                                   IN in_sort_by varchar(25),

                                   IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_name = concat('%', in_name, '%');

    set in_country = concat('%', in_country, '%');

    set in_logo = concat('%', in_logo, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_brand

    WHERE ((in_name IS NULL) or (name like in_name))

      and ((in_country IS NULL) or (country like in_country))

      and ((in_establish_year IS NULL) or (year(establish_date)=in_establish_year))

      and ((in_logo IS NULL) or (logo like in_logo))

      and ((in_created_date IS NULL) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date IS NULL) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date IS NULL) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del IS NULL) or (is_del=in_is_del))

      and ((in_update_by IS NULL) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'country' THEN country

                    WHEN 'establish_date' THEN establish_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'country' THEN country

                    WHEN 'establish_date' THEN establish_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindBrandById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindBrandById`(IN in_id bigint)
BEGIN

    SELECT * FROM tbl_brand WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCartById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCartById`(IN in_id varchar(50))
BEGIN

    SELECT * FROM tbl_cart WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCartByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCartByUserId`(IN in_user_id bigint)
BEGIN

    SELECT * FROM tbl_cart WHERE user_id=in_user_id and is_del = 0;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCategoryByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCategoryByFilter`(IN in_name varchar(50), IN in_image varchar(255),

                                      IN in_description varchar(255),

                                      IN in_created_date date, IN in_modified_date date,

                                      IN in_deleted_date date, IN in_is_del tinyint(1),

                                      IN in_update_by varchar(100),

                                      IN in_sort_by varchar(25),

                                      IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_name = concat('%', in_name, '%');

    set in_image = concat('%', in_image, '%');

    set in_description = concat('%', in_description, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_category

    WHERE ((in_name is null) or (name like in_name))

      and ((in_image is null) or (image like in_image))

      and ((in_description is null) or (description like in_description))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCategoryById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCategoryById`(IN in_id bigint)
BEGIN

    SELECT * FROM tbl_category WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCommentByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCommentByFilter`(IN in_root_comment_id varchar(25),

                                     IN in_product_id varchar(50),

                                     IN in_username varchar(100), IN in_phone char(15),

                                     IN in_content varchar(255),

                                     IN in_created_date date, IN in_modified_date date,

                                     IN in_deleted_date date, IN in_is_del tinyint(1),

                                     IN in_update_by varchar(100),

                                     IN in_sort_by varchar(25),

                                     IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_username = concat('%', in_username, '%');

    set in_content = concat('%', in_content, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_comment

    WHERE ((in_root_comment_id is null) or (root_comment_id=in_root_comment_id))

      and ((in_product_id is null) or (product_id=in_product_id))

      and ((in_username is null) or (username like in_username))

      and ((in_phone is null) or (phone=in_phone))

      and ((in_content is null) or (content like in_content))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'root_comment_id' THEN root_comment_id

                    WHEN 'product_id' THEN product_id

                    WHEN 'username' THEN username

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'root_comment_id' THEN root_comment_id

                    WHEN 'product_id' THEN product_id

                    WHEN 'username' THEN username

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCommentById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCommentById`(IN in_id varchar(25))
BEGIN

    SELECT * FROM tbl_comment WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCommentByProductId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCommentByProductId`(IN in_product_id varchar(50))
BEGIN

    SELECT * FROM tbl_comment WHERE product_id=in_product_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCommentByRootCommentId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCommentByRootCommentId`(IN in_root_comment_id varchar(25))
BEGIN

    SELECT * FROM tbl_comment WHERE root_comment_id=in_root_comment_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindCommentByUserPhone` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindCommentByUserPhone`(IN in_phone char(15))
BEGIN

    select * from tbl_comment where phone=in_phone;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindDiscountByDateRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindDiscountByDateRange`(IN in_applied_date date, IN in_ended_date date)
BEGIN

    select * from tbl_discount where ended_date >= in_applied_date or applied_date <= in_ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindDiscountByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindDiscountByFilter`(IN in_code varchar(20), IN in_rate float,

                                      IN in_max_amount decimal(21, 4),

                                      IN in_applied_type enum ('PRODUCT', 'PURCHASE'),

                                      IN in_applied_date datetime,

                                      IN in_ended_date datetime,

                                      IN in_created_date date, IN in_modified_date date,

                                      IN in_deleted_date date, IN in_is_del tinyint(1),

                                      IN in_update_by varchar(100),

                                      IN in_sort_by varchar(25),

                                      IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_code = concat('%', in_code, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_discount

    WHERE ((in_code is null) or (code like in_code))

      and ((in_rate is null) or (rate=in_rate))

      and ((in_max_amount is null) or (max_amount=in_max_amount))

      and ((in_applied_type is null) or (applied_type=in_applied_type))

      and ((in_applied_date is null) or (applied_date=in_applied_date))

      and ((in_ended_date is null) or (ended_date=in_ended_date))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'code' THEN code

                    WHEN 'rate' THEN rate

                    WHEN 'max_amount' THEN max_amount

                    WHEN 'applied_type' THEN applied_type

                    WHEN 'applied_date' THEN applied_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'code' THEN code

                    WHEN 'rate' THEN rate

                    WHEN 'max_amount' THEN max_amount

                    WHEN 'applied_type' THEN applied_type

                    WHEN 'applied_date' THEN applied_date

                    WHEN 'ended_date' THEN ended_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindDiscountById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindDiscountById`(IN in_id bigint)
BEGIN

    SELECT * FROM tbl_discount WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindDiscountByProductId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindDiscountByProductId`(IN in_product_id varchar(50))
BEGIN

    select *

    from tbl_discount

    where id in

          ( select discount_id from tbl_product_discount where product_id = in_product_id);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindDiscountOfProductUseInDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindDiscountOfProductUseInDate`(IN in_product_id varchar(50), IN in_date date)
BEGIN

    select *

    from tbl_discount

    where id in

          ( select discount_id from tbl_product_discount where product_id = in_product_id)

      and in_date between applied_date and ended_date

    order by max_amount desc;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindFeedbackByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindFeedbackByFilter`(IN in_product_id varchar(50),

                                      IN in_user_id bigint, IN in_content varchar(255),

                                      IN in_rating_point int, IN in_created_date date,

                                      IN in_modified_date date, IN in_deleted_date date,

                                      IN in_is_del tinyint(1),

                                      IN in_update_by varchar(100),

                                      IN in_sort_by varchar(25),

                                      IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_content = concat('%', in_content, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_feedback

    WHERE ((in_product_id is null) or (product_id=in_product_id))

      and ((in_user_id is null) or (user_id=in_user_id))

      and ((in_content is null) or (content like in_content))

      and ((in_rating_point is null) or (rating_point=in_rating_point))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'user_id' THEN user_id

                    WHEN 'rating_point' THEN rating_point

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'user_id' THEN user_id

                    WHEN 'rating_point' THEN rating_point

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindFeedbackById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindFeedbackById`(IN in_id varchar(25))
BEGIN

    SELECT * FROM tbl_feedback WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindFeedbackByProductId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindFeedbackByProductId`(IN in_product_id varchar(50))
BEGIN

    SELECT * FROM tbl_feedback WHERE product_id=in_product_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindFeedbackByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindFeedbackByUserId`(IN in_user_id bigint)
BEGIN

    SELECT * FROM tbl_feedback WHERE user_id=in_user_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindImportByDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindImportByDate`(IN in_date date)
BEGIN

    select * from tbl_import where cast(imported_date as date) = in_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindImportByDateRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindImportByDateRange`(IN in_started_date date, IN in_ended_date date)
BEGIN

    select * from tbl_import where imported_date between in_started_date and in_ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindImportByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindImportByFilter`(IN in_product_id varchar(50), IN in_quantity bigint,

                                    IN in_imported_price decimal(21, 4),

                                    IN in_imported_date date, IN in_created_date date,

                                    IN in_modified_date date, IN in_deleted_date date,

                                    IN in_is_del tinyint(1),

                                    IN in_update_by varchar(100),

                                    IN in_sort_by varchar(25),

                                    IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_import

    WHERE ((in_product_id is null) or (product_id=in_product_id))

      and ((in_quantity is null) or (quantity=in_quantity))

      and ((in_imported_price is null) or (imported_price=in_imported_price))

      and ((in_imported_date is null) or (cast(imported_date as date)=in_imported_date))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'imported_price' THEN imported_price

                    WHEN 'imported_date' THEN imported_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'imported_price' THEN imported_price

                    WHEN 'imported_date' THEN imported_date

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindImportById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindImportById`(IN in_id varchar(25))
BEGIN

    SELECT * FROM tbl_import WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindImportByProductId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindImportByProductId`(IN in_product_id varchar(50))
BEGIN

    SELECT * FROM tbl_import WHERE product_id=in_product_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindImportProductByDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindImportProductByDate`(IN in_date date)
BEGIN

    select * from tbl_import where cast(imported_date as date) = in_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindImportProductByDateRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindImportProductByDateRange`(IN in_started_date date, IN in_ended_date date)
BEGIN

    select * from tbl_import where imported_date between in_started_date and in_ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindInvoiceByDate` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindInvoiceByDate`(IN in_date date)
BEGIN

    select * from tbl_invoice where cast(created_date as date) = in_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindInvoiceByDateRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindInvoiceByDateRange`(IN in_started_date date, IN in_ended_date date)
BEGIN

    select * from tbl_invoice where created_date between in_started_date and in_ended_date;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindInvoiceByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindInvoiceByFilter`(IN in_user_id bigint, IN in_address varchar(255),

                                     IN in_phone char(15),

                                     IN in_payment_amount decimal(21, 4),

                                     IN in_ship_cost decimal(21, 4),

                                     IN in_discount_amount decimal(21, 4),

                                     IN in_tax decimal(21, 4),

                                     IN in_payment_total decimal(21, 4),

                                     IN in_payment_type varchar(50),

                                     IN in_is_paid tinyint(1),

                                     IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'),

                                     IN in_note varchar(255), IN in_created_date date,

                                     IN in_modified_date date, IN in_deleted_date date,

                                     IN in_is_del tinyint(1),

                                     IN in_update_by varchar(100),

                                     IN in_sort_by varchar(25),

                                     IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_address = concat('%', in_address, '%');

    set in_payment_type = concat('%', in_payment_type, '%');

    set in_note = concat('%', in_note, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_invoice

    WHERE ((in_user_id is null) or (user_id=in_user_id))

      and ((in_address is null) or (address like in_address))

      and ((in_phone is null) or (phone=in_phone))

      and ((in_payment_amount is null) or (payment_amount=in_payment_amount))

      and ((in_ship_cost is null) or (ship_cost=in_ship_cost))

      and ((in_discount_amount is null) or (discount_amount=in_discount_amount))

      and ((in_tax is null) or (tax=in_tax))

      and ((in_payment_total is null) or (payment_total=in_payment_total))

      and ((in_payment_type is null) or (payment_type like in_payment_type))

      and ((in_is_paid is null) or (is_paid=in_is_paid))

      and ((in_order_status is null) or (order_status=in_order_status))

      and ((in_note is null) or (note like in_note))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'user_id' THEN user_id

                    WHEN 'address' THEN address

                    WHEN 'phone' THEN phone

                    WHEN 'payment_amount' THEN payment_amount

                    WHEN 'ship_cost' THEN ship_cost

                    WHEN 'discount_amount' THEN discount_amount

                    WHEN 'tax' THEN tax

                    WHEN 'payment_total' THEN payment_total

                    WHEN 'payment_type' THEN payment_type

                    WHEN 'is_paid' THEN is_paid

                    WHEN 'order_status' THEN order_status

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'user_id' THEN user_id

                    WHEN 'address' THEN address

                    WHEN 'phone' THEN phone

                    WHEN 'payment_amount' THEN payment_amount

                    WHEN 'ship_cost' THEN ship_cost

                    WHEN 'discount_amount' THEN discount_amount

                    WHEN 'tax' THEN tax

                    WHEN 'payment_total' THEN payment_total

                    WHEN 'payment_type' THEN payment_type

                    WHEN 'is_paid' THEN is_paid

                    WHEN 'order_status' THEN order_status

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindInvoiceById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindInvoiceById`(IN in_id varchar(50))
BEGIN

    SELECT * FROM tbl_invoice WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindInvoiceByOrderStatus` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindInvoiceByOrderStatus`(IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED'))
BEGIN

    SELECT * FROM tbl_invoice WHERE order_status=in_order_status;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindInvoiceByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindInvoiceByUserId`(IN in_user_id bigint)
BEGIN

    SELECT * FROM tbl_invoice WHERE user_id=in_user_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindLabelByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindLabelByFilter`(IN in_name varchar(25), IN in_icon varchar(255),

                                   IN in_title varchar(50),

                                   IN in_description varchar(255),

                                   IN in_created_date date, IN in_modified_date date,

                                   IN in_deleted_date date, IN in_is_del tinyint(1),

                                   IN in_update_by varchar(100),

                                   IN in_sort_by varchar(25),

                                   IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_name = concat('%', in_name, '%');

    set in_icon = concat('%', in_icon, '%');

    set in_title = concat('%', in_title, '%');

    set in_description = concat('%', in_description, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_label

    WHERE ((in_name is null) or (name like in_name))

      and ((in_icon is null) or (icon like in_icon))

      and ((in_title is null) or (title like in_title))

      and ((in_description is null) or (description like in_description))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'icon' THEN icon

                    WHEN 'title' THEN title

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'icon' THEN icon

                    WHEN 'title' THEN title

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindLabelById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindLabelById`(IN in_id bigint)
BEGIN

    SELECT * FROM tbl_label WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindLabelByProductId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindLabelByProductId`(IN in_product_id varchar(50))
BEGIN

    select * from tbl_label where id in (

        select label_id from tbl_product_label where product_id = in_product_id

    );

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindLogSystemByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindLogSystemByFilter`(IN in_action_table varchar(50),

                                       IN in_action_date date,

                                       IN in_action_by varchar(100),

                                       IN in_action_name varchar(25),
                                       IN in_action_description longtext,

                                       IN in_sort_by varchar(25),

                                       IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN

    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_action_table = concat('%', in_action_table, '%');

    set in_action_by = concat('%', in_action_by, '%');

    set in_action_name = concat('%', in_action_name, '%');
    set in_action_description = concat('%', in_action_description, '%');



    SELECT * FROM tbl_log_system

    WHERE ((in_action_table is null) or (action_table like in_action_table))

      and ((in_action_date is null) or (cast(action_time as date) = in_action_date))

      and ((in_action_by is null) or (action_by like in_action_by))

	  and ((in_action_name is null) or (action_name like in_action_name))
      and ((in_action_description is null) or (action_description like in_action_description))
      

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'action_table' THEN action_table

                    WHEN 'action_time' THEN action_time

                    WHEN 'action_by' THEN action_by

                    WHEN 'action_name' THEN action_name
                    WHEN 'action_description' THEN action_description

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'action_table' THEN action_table

                    WHEN 'action_date' THEN action_time

                    WHEN 'action_by' THEN action_by

                    WHEN 'action_name' THEN action_name
					WHEN 'action_description' THEN action_description

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductByBrandId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductByBrandId`(IN in_brand_id bigint)
BEGIN

    SELECT * FROM tbl_product WHERE brand_id=in_brand_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductByCategoryId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductByCategoryId`(IN in_category_id bigint)
BEGIN

    SELECT * FROM tbl_product WHERE category_id=in_category_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductByFilter`(IN in_brand_id bigint, IN in_category_id bigint,

                                     IN in_name varchar(100), IN in_released_date date,

                                     IN in_quantity_in_stock int,

                                     IN in_listed_price decimal(21, 4),

                                     IN in_created_date date, IN in_modified_date date,

                                     IN in_deleted_date date, IN in_is_del tinyint(1),

                                     IN in_update_by varchar(100),

                                     IN in_sort_by varchar(25),

                                     IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_name = concat('%', in_name, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_product

    WHERE ((in_brand_id is null) or (brand_id=in_brand_id))

      and ((in_category_id is null) or (category_id=in_category_id))

      and ((in_name is null) or (name like in_name))

      and ((in_released_date is null) or (released_date=in_released_date))

      and ((in_quantity_in_stock is null) or (quantity_in_stock=in_quantity_in_stock))

      and ((in_listed_price is null) or (listed_price=in_listed_price))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'brand_id' THEN brand_id

                    WHEN 'category_id' THEN category_id

                    WHEN 'name' THEN name

                    WHEN 'released_date' THEN released_date

                    WHEN 'quantity_in_stock' THEN quantity_in_stock

                    WHEN 'listed_price' THEN listed_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'brand_id' THEN brand_id

                    WHEN 'category_id' THEN category_id

                    WHEN 'name' THEN name

                    WHEN 'released_date' THEN released_date

                    WHEN 'quantity_in_stock' THEN quantity_in_stock

                    WHEN 'listed_price' THEN listed_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductById`(IN in_id varchar(50))
BEGIN

    SELECT * FROM tbl_product WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductByLabelId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductByLabelId`(IN in_label_id bigint(11))
BEGIN

    select * from tbl_product where id in (

        select product_id from tbl_product_label where label_id = in_label_id

    );

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductByPriceRange` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductByPriceRange`(IN start_price double, IN end_price double)
BEGIN

    select * from tbl_product where listed_price between start_price and end_price;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductByReleasedYear` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductByReleasedYear`(IN in_year year)
BEGIN

    select * from tbl_product where year(released_date) = in_year;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductImageByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductImageByFilter`(IN in_product_id varchar(50),

                                          IN in_feedback_id varchar(25),

                                          IN in_url varchar(255),

                                          IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),

                                          IN in_created_date date,

                                          IN in_modified_date date,

                                          IN in_deleted_date date,

                                          IN in_is_del tinyint(1),

                                          IN in_update_by varchar(100),

                                          IN in_sort_by varchar(25),

                                          IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_url = concat('%', in_url, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_product_image

    WHERE ((in_product_id is null) or (product_id=in_product_id))

      and ((in_feedback_id is null) or (feedback_id=in_feedback_id))

      and ((in_url is null) or (url like in_url))

      and ((in_type is null) or (type=in_type))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'feedback_id' THEN feedback_id

                    WHEN 'type' THEN type

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'feedback_id' THEN feedback_id

                    WHEN 'type' THEN type

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductImageById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductImageById`(IN in_id varchar(50))
BEGIN

    SELECT * FROM tbl_product_image WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductImageByImageType` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductImageByImageType`(IN in_image_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'))
BEGIN

    SELECT * FROM tbl_product_image WHERE type=in_image_type;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductImageByProductId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductImageByProductId`(IN in_product_id varchar(50))
BEGIN

    SELECT * FROM tbl_product_image WHERE product_id=in_product_id and is_del=false;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductUnitByCartId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductUnitByCartId`(IN in_cart_id varchar(50))
BEGIN

    SELECT * FROM tbl_product_unit WHERE cart_id=in_cart_id and is_del != 1;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductUnitByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductUnitByFilter`(IN in_product_id varchar(50),

                                         IN in_cart_id varchar(50),

                                         IN in_invoice_id varchar(50),

                                         IN in_quantity int, IN in_price decimal(21, 4),

                                         IN in_discount_price decimal(21, 4),

                                         IN in_created_date date,

                                         IN in_modified_date date,

                                         IN in_deleted_date date,

                                         IN in_is_del tinyint(1),

                                         IN in_update_by varchar(100),

                                         IN in_sort_by varchar(25),

                                         IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_product_unit

    WHERE ((in_product_id is null) or (product_id=in_product_id))

      and ((in_cart_id is null) or (cart_id=in_cart_id))

      and ((in_invoice_id is null) or (invoice_id=in_invoice_id))

      and ((in_quantity is null) or (quantity=in_quantity))

      and ((in_price is null) or (price=in_price))

      and ((in_discount_price is null) or (discount_price=in_discount_price))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'invoice_id' THEN invoice_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'price' THEN price

                    WHEN 'discount_price' THEN discount_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'product_id' THEN product_id

                    WHEN 'invoice_id' THEN invoice_id

                    WHEN 'quantity' THEN quantity

                    WHEN 'price' THEN price

                    WHEN 'discount_price' THEN discount_price

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductUnitById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductUnitById`(IN in_id varchar(25))
BEGIN

    SELECT * FROM tbl_product_unit WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindProductUnitByInvoiceId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindProductUnitByInvoiceId`(IN in_invoice_id varchar(50))
BEGIN

    SELECT * FROM tbl_product_unit WHERE invoice_id=in_invoice_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindRefreshTokenByCode` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindRefreshTokenByCode`(IN in_code varchar(50))
BEGIN

    SELECT * FROM tbl_refresh_token WHERE code = in_code;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindResetPasswordTokenByToken` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindResetPasswordTokenByToken`(IN in_token varchar(100))
BEGIN
	select * from tbl_reset_password_token where token = in_token;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindRoleByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindRoleByFilter`(IN in_name varchar(25),

                                  IN in_description varchar(100),

                                  IN in_created_date date, IN in_modified_date date,

                                  IN in_deleted_date date, IN in_is_del tinyint(1),

                                  IN in_update_by varchar(100),

                                  IN in_sort_by varchar(25),

                                  IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_name = concat('%', in_name, '%');

    set in_description = concat('%', in_description, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_role

    WHERE ((in_name is null) or (name like in_name))

      and ((in_description is null) or (description like in_description))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindRoleById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindRoleById`(IN in_id int)
BEGIN

    SELECT * FROM tbl_role WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindRoleByName` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindRoleByName`(IN in_name varchar(25))
BEGIN

    SELECT * FROM tbl_role WHERE name like in_name;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindRoleByUserId` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindRoleByUserId`(IN in_user_id bigint(11))
BEGIN

    select * from tbl_role where id in (

        select role_id from tbl_user_role where user_id = in_user_id

    );

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindUserByFilter` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindUserByFilter`(IN in_name varchar(100),

                                  IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),

                                  IN in_date_of_birth date, IN in_phone char(15),

                                  IN in_email varchar(50), IN in_is_active tinyint(1),

                                  IN in_created_date date, IN in_modified_date date,

                                  IN in_deleted_date date, IN in_is_del tinyint(1),

                                  IN in_update_by varchar(100),

                                  IN in_sort_by varchar(25),

                                  IN in_sort_dir enum ('ASC', 'DESC'))
BEGIN



    if in_sort_dir is null then

        set in_sort_dir = 'ASC';

    end if;



    set in_name = concat('%', in_name, '%');

    set in_email = concat('%', in_email, '%');

    set in_update_by = concat('%', in_update_by, '%');



    SELECT * FROM tbl_user

    WHERE ((in_name is null) or (name like in_name))

      and ((in_gender is null) or (gender=in_gender))

      and ((in_date_of_birth is null) or (date_of_birth=in_date_of_birth))

      and ((in_phone is null) or (phone=in_phone))

      and ((in_email is null) or (email like in_email))

      and ((in_is_active is null) or (is_active=in_is_active))

      and ((in_created_date is null) or (cast(created_date as date)=in_created_date))

      and ((in_modified_date is null) or (cast(modified_date as date)=in_modified_date))

      and ((in_deleted_date is null) or (cast(deleted_date as date)=in_deleted_date))

      and ((in_is_del is null) or (is_del=in_is_del))

      and ((in_update_by is null) or (update_by like in_update_by))

    ORDER BY

        CASE in_sort_dir

            WHEN 'ASC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'gender' THEN gender

                    WHEN 'date_of_birth' THEN date_of_birth

                    WHEN 'email' THEN email

                    WHEN 'is_active' THEN is_active

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END,

        CASE in_sort_dir

            WHEN 'DESC' THEN

                CASE LOWER(in_sort_by)

                    WHEN 'name' THEN name

                    WHEN 'gender' THEN gender

                    WHEN 'date_of_birth' THEN date_of_birth

                    WHEN 'email' THEN email

                    WHEN 'is_active' THEN is_active

                    WHEN 'created_date' THEN created_date

                    WHEN 'modified_date' THEN modified_date

                    WHEN 'deleted_date' THEN deleted_date

                    END

            END DESC;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindUserById` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindUserById`(IN in_id bigint)
BEGIN

    SELECT * FROM tbl_user WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindUserByPhone` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindUserByPhone`(IN in_phone char(15))
BEGIN

    SELECT * FROM tbl_user WHERE phone=in_phone;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindUserByResetPasswordToken` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindUserByResetPasswordToken`(IN in_token varchar(100))
BEGIN
	select u.* 
    from tbl_user u, tbl_reset_password_token rpt
    where rpt.token = in_token and rpt.user_id = u.id
    order by rpt.created_date desc
    limit 1;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_FindUserByRoleName` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_FindUserByRoleName`(IN in_role_name varchar(25))
BEGIN

    set in_role_name = concat('%', in_role_name, '%');



    select * from tbl_user where id in (

        select ur.user_id

        from tbl_user_role ur, tbl_role r

        where r.name like in_role_name

          and ur.role_id = r.id

    );

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewAddress` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewAddress`(IN in_id varchar(25), IN in_user_id bigint,

                                  IN in_country varchar(25), IN in_line1 varchar(25),

                                  IN in_line2 varchar(25), IN in_line3 varchar(25),

                                  IN in_street varchar(100),

                                  IN in_is_default tinyint(1),

                                  IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_address (id, user_id, country, line1, line2, line3, street, is_default, update_by) VALUES (in_id, in_user_id, in_country, in_line1, in_line2, in_line3, in_street, in_is_default, in_update_by);SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewBanner` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewBanner`(IN in_path varchar(255), IN in_type varchar(50),

                                 IN in_title varchar(100),

                                 IN in_link_product varchar(255), IN in_used_date date,

                                 IN in_ended_date date, IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_banner (path, type, title, link_product, used_date, ended_date, update_by) VALUES (in_path, in_type, in_title, in_link_product, in_used_date, in_ended_date, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewBrand` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewBrand`(IN in_name varchar(100), IN in_country varchar(100),

                                IN in_establish_date date, IN in_logo varchar(255),

                                IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_brand (name, country, establish_date, logo, update_by) VALUES (in_name, in_country, in_establish_date, in_logo, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewCart`(IN in_id varchar(50), IN in_user_id bigint,

                               IN in_discount_id bigint, IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_cart (id, user_id, discount_id, update_by) VALUES (in_id, in_user_id, in_discount_id, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewCategory`(IN in_name varchar(50), IN in_image varchar(255),

                                   IN in_description varchar(255),

                                   IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_category (name, image, description, update_by) VALUES (in_name, in_image, in_description, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewComment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewComment`(IN in_id varchar(25),

                                  IN in_root_comment_id varchar(25),

                                  IN in_product_id varchar(50),

                                  IN in_username varchar(100), IN in_phone char(15),

                                  IN in_content varchar(255),

                                  IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_comment (id, root_comment_id, product_id, username, phone, content, update_by) VALUES (in_id, in_root_comment_id, in_product_id, in_username, in_phone, in_content, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewDiscount`(IN in_code varchar(20), IN in_rate float, IN in_applied_type enum('PRODUCT','PURCHASE'), IN in_max_amount decimal(21,4), IN in_applied_date datetime, IN in_ended_date datetime, IN in_update_by varchar(100))
BEGIN
INSERT INTO tbl_discount (code, rate, max_amount, applied_type, applied_date, ended_date, update_by) VALUES (in_code, in_rate, in_max_amount, in_applied_type, in_applied_date, in_ended_date, in_update_by); SELECT LAST_INSERT_ID();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewFeedback`(IN in_id varchar(25), IN in_product_id varchar(50),

                                   IN in_user_id bigint, IN in_content varchar(255),

                                   IN in_rating_point int, IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_feedback (id, product_id, user_id, content, rating_point, update_by) VALUES (in_id, in_product_id, in_user_id, in_content, in_rating_point, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewImport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewImport`(IN in_id varchar(25), IN in_product_id varchar(50),

                                 IN in_quantity bigint,

                                 IN in_imported_price decimal(21, 4),

                                 IN in_imported_date datetime,

                                 IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_import (id, product_id, quantity, imported_price, imported_date, update_by) VALUES (in_id, in_product_id, in_quantity, in_imported_price, in_imported_date, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewInvoice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewInvoice`(IN in_id varchar(50), IN in_user_id bigint,

                                  IN in_address varchar(255), IN in_phone char(15),

                                  IN in_payment_amount decimal(21, 4),

                                  IN in_ship_cost decimal(21, 4),

                                  IN in_discount_amount decimal(21, 4),

                                  IN in_tax decimal(21, 4),

                                  IN in_payment_total decimal(21, 4),

                                  IN in_payment_type varchar(50),

                                  IN in_is_paid tinyint(1),

                                  IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED', 'IGNORED'),

                                  IN in_note varchar(255), IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_invoice (id, user_id, address, phone, payment_amount, ship_cost, discount_amount, tax, payment_total, payment_type, is_paid, order_status, note, update_by) VALUES (in_id, in_user_id, in_address, in_phone, in_payment_amount, in_ship_cost, in_discount_amount, in_tax, in_payment_total, in_payment_type, in_is_paid, in_order_status, in_note, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewLabel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewLabel`(IN in_name varchar(25), IN in_icon varchar(255),

                                IN in_title varchar(50), IN in_description varchar(255),

                                IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_label (name, icon, title, description, update_by) VALUES (in_name, in_icon, in_title, in_description, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewProduct` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewProduct`(IN in_id varchar(50), IN in_brand_id bigint,

                                  IN in_category_id bigint, IN in_name varchar(100),

                                  IN in_released_date date, IN in_quantity_in_stock int,

                                  IN in_listed_price decimal(19, 4),

                                  IN in_specifications text,

                                  IN in_description_detail text,

                                  IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_product (id, brand_id, category_id, name, released_date, quantity_in_stock, listed_price, specifications, description_detail, update_by) VALUES (in_id, in_brand_id, in_category_id, in_name, in_released_date, in_quantity_in_stock, in_listed_price, in_specifications, in_description_detail, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewProductImage` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewProductImage`(IN in_id varchar(50),

                                       IN in_product_id varchar(50),

                                       IN in_feedback_id varchar(25),

                                       IN in_url varchar(255),

                                       IN in_type enum ('ADVERTISE', 'DETAIL', 'EXTRA', 'FEEDBACK'),

                                       IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_product_image (id, product_id, feedback_id, url, type, update_by) VALUES (in_id, in_product_id, in_feedback_id, in_url, in_type, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewProductUnit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewProductUnit`(IN in_id varchar(25),

                                      IN in_product_id varchar(50),

                                      IN in_cart_id varchar(50),

                                      IN in_invoice_id varchar(50), IN in_quantity int,

                                      IN in_price decimal(19, 4),

                                      IN in_discount_price decimal(19, 4),

                                      IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_product_unit (id, product_id, cart_id, invoice_id, quantity, price, discount_price, update_by) VALUES (in_id, in_product_id, in_cart_id, in_invoice_id, in_quantity, in_price, in_discount_price, in_update_by); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewRole`(IN in_name varchar(25), IN in_description varchar(100),

                               IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_role (name, description, update_by) 
    VALUES (in_name, in_description, in_update_by); 
    SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertNewUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertNewUser`(IN in_name varchar(100),

                               IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),

                               IN in_date_of_birth date, IN in_phone char(15),

                               IN in_email varchar(50), IN in_password varchar(100),

                               IN in_update_by varchar(100))
BEGIN

    INSERT INTO tbl_user (name, gender, date_of_birth, phone, email, password, update_by) 
    VALUES (in_name, in_gender, in_date_of_birth, in_phone, in_email, in_password, in_update_by);
    SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertProductAccessory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertProductAccessory`(IN in_product_id varchar(255), IN in_accessory_id varchar(255))
BEGIN

    INSERT INTO tbl_product_accessory (product_id, accessory_id) VALUES (in_product_id, in_accessory_id); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertProductDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertProductDiscount`(IN in_product_id varchar(255), IN in_discount_id bigint)
BEGIN

    INSERT INTO tbl_product_discount (product_id, discount_id) VALUES (in_product_id, in_discount_id); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertProductLabel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertProductLabel`(IN in_product_id varchar(255), IN in_label_id bigint)
BEGIN

    INSERT INTO tbl_product_label (product_id, label_id) VALUES (in_product_id, in_label_id); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertRefreshToken` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertRefreshToken`(IN in_code varchar(50), IN in_user_id bigint(11))
BEGIN

    INSERT INTO tbl_refresh_token(code, user_id) VALUES (in_code, in_user_id);

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertResetPasswordToken` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertResetPasswordToken`(
		IN in_user_id bigint, 
		IN in_token varchar(100),
		IN in_expired_time datetime
)
BEGIN
	insert into tbl_reset_password_token(user_id, token, expired_time, created_date)
    select in_user_id, in_token, in_expired_time, now();
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_InsertUserRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_InsertUserRole`(IN in_user_id bigint, IN in_role_id bigint)
BEGIN

    INSERT INTO tbl_user_role (user_id, role_id) VALUES (in_user_id, in_role_id); SELECT LAST_INSERT_ID();

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateAddress` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateAddress`(IN in_id varchar(25), IN in_user_id bigint,

                               IN in_country varchar(25), IN in_line1 varchar(25),

                               IN in_line2 varchar(25), IN in_line3 varchar(25),

                               IN in_street varchar(100), IN in_is_default tinyint(1),

                               IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_address SET user_id=in_user_id, country=in_country, line1=in_line1, line2=in_line2, line3=in_line3, street=in_street, is_default=in_is_default, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateBanner` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateBanner`(IN in_id bigint, IN in_path varchar(255),

                              IN in_type varchar(50), IN in_title varchar(100),

                              IN in_link_product varchar(255), IN in_used_date date,

                              IN in_ended_date date, IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_banner SET path=in_path, type=in_type, title=in_title, link_product=in_link_product, used_date=in_used_date, ended_date=in_ended_date, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateBrand` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateBrand`(IN in_id bigint, IN in_name varchar(100),

                             IN in_country varchar(100), IN in_establish_date date,

                             IN in_logo varchar(255), IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_brand SET name=in_name, country=in_country, establish_date=in_establish_date, logo=in_logo, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateCart` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateCart`(IN in_id varchar(50), IN in_user_id bigint,

                            IN in_discount_id bigint, IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_cart SET user_id=in_user_id, discount_id=in_discount_id, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateCategory` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateCategory`(IN in_id bigint, IN in_name varchar(50),

                                IN in_image varchar(255),

                                IN in_description varchar(255),

                                IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_category SET name=in_name, image=in_image, description=in_description, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateComment` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateComment`(IN in_id varchar(25), IN in_root_comment_id varchar(25),

                               IN in_product_id varchar(50),

                               IN in_username varchar(100), IN in_phone char(15),

                               IN in_content varchar(255), IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_comment SET root_comment_id=in_root_comment_id, product_id=in_product_id, username=in_username, phone=in_phone, content=in_content, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateDiscount` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateDiscount`(IN in_id bigint(20), IN in_code varchar(20), IN in_rate float, IN in_applied_type enum('PRODUCT','PURCHASE'), IN in_max_amount decimal(21,4), IN in_applied_date datetime, IN in_ended_date datetime, IN in_update_by varchar(100))
BEGIN
UPDATE tbl_discount SET code=in_code, rate=in_rate, max_amount=in_max_amount, applied_type=in_applied_type, applied_date=in_applied_date, ended_date=in_ended_date, update_by=in_update_by WHERE id=in_id;
END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateFeedback` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateFeedback`(IN in_id varchar(25), IN in_product_id varchar(50),

                                IN in_user_id bigint, IN in_content varchar(255),

                                IN in_rating_point int, IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_feedback SET product_id=in_product_id, user_id=in_user_id, content=in_content, rating_point=in_rating_point, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateImport` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateImport`(IN in_id varchar(25), IN in_product_id varchar(50),

                              IN in_quantity bigint,

                              IN in_imported_price decimal(19, 4),

                              IN in_imported_date datetime,

                              IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_import SET product_id=in_product_id, quantity=in_quantity, imported_price=in_imported_price, imported_date=in_imported_date, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateInvoice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateInvoice`(IN in_id varchar(50), IN in_user_id bigint,

                               IN in_address varchar(255), IN in_phone char(15),

                               IN in_payment_amount decimal(19, 4),

                               IN in_ship_cost decimal(19, 4),

                               IN in_discount_amount decimal(19, 4),

                               IN in_tax decimal(19, 4),

                               IN in_payment_total decimal(19, 4),

                               IN in_payment_type varchar(50), IN in_is_paid tinyint(1),

                               IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED', 'IGNORED'),

                               IN in_note varchar(255), IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_invoice SET user_id=in_user_id, address=in_address, phone=in_phone, payment_amount=in_payment_amount, ship_cost=in_ship_cost, discount_amount=in_discount_amount, tax=in_tax, payment_total=in_payment_total, payment_type=in_payment_type, is_paid=in_is_paid, order_status=in_order_status, note=in_note, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateInvoiceOrderStatus` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateInvoiceOrderStatus`(IN in_id varchar(50),

                                          IN in_order_status enum ('PENDING', 'WAIT_CONFIRMED', 'PREPARED', 'SHIPPED', 'RECEIVED', 'CANCELED', 'FAILED', 'IGNORED'),

                                          IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_invoice SET order_status=in_order_status, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateInvoicePaymentTypeAndPaidStatus` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateInvoicePaymentTypeAndPaidStatus`(IN in_id varchar(50),

                                                       IN in_payment_type varchar(50),

                                                       IN in_is_paid tinyint(1),

                                                       IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_invoice SET payment_type=in_payment_type, is_paid=in_is_paid, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateLabel` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateLabel`(IN in_id bigint, IN in_name varchar(25),

                             IN in_icon varchar(255), IN in_title varchar(50),

                             IN in_description varchar(255),

                             IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_label SET name=in_name, icon=in_icon, title=in_title, description=in_description, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateProduct` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateProduct`(IN in_id varchar(50), IN in_brand_id bigint,

                               IN in_category_id bigint, IN in_name varchar(100),

                               IN in_released_date date, IN in_quantity_in_stock int,

                               IN in_listed_price decimal(19, 4),

                               IN in_specifications text, IN in_description_detail text,

                               IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_product SET brand_id=in_brand_id, category_id=in_category_id, name=in_name, released_date=in_released_date, quantity_in_stock=in_quantity_in_stock, listed_price=in_listed_price, specifications=in_specifications, description_detail=in_description_detail, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateProductImage` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateProductImage`(IN in_id varchar(50), IN in_product_id varchar(50),

                                    IN in_feedback_id varchar(25),

                                    IN in_url varchar(255),

                                    IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),

                                    IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_product_image SET product_id=in_product_id, feedback_id=in_feedback_id, url=in_url, type=in_type, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateProductImageUrlAndType` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateProductImageUrlAndType`(IN in_id varchar(50),

                                              IN in_url varchar(255),

                                              IN in_type enum ('ADVERTISE', 'DETAIL', 'ADDITIONAL', 'FEEDBACK'),

                                              IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_product_image SET url=in_url, type=in_type, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateProductPrice` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateProductPrice`(IN in_id varchar(50),

                                    IN in_listed_price decimal(19, 4),

                                    IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_product SET listed_price=in_listed_price, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateProductUnit` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateProductUnit`(IN in_id varchar(25), IN in_product_id varchar(50),

                                   IN in_cart_id varchar(50),

                                   IN in_invoice_id varchar(50), IN in_quantity int,

                                   IN in_price decimal(21, 4),

                                   IN in_discount_price decimal(21, 4),

                                   IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_product_unit SET product_id=in_product_id, cart_id=in_cart_id, invoice_id=in_invoice_id, quantity=in_quantity, price=in_price, discount_price=in_discount_price, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateProductUnitProperties` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateProductUnitProperties`(IN in_id varchar(25), IN in_quantity int,

                                             IN in_price decimal(21, 4),

                                             IN in_discount_price decimal(21, 4),

                                             IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_product_unit SET quantity=in_quantity, price=in_price, discount_price=in_discount_price, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateRole` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateRole`(IN in_id int, IN in_name varchar(25),

                            IN in_description varchar(100),

                            IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_role SET name=in_name, description=in_description, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateUser` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateUser`(IN in_id bigint, IN in_name varchar(100),

                            IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),

                            IN in_date_of_birth date, IN in_phone char(15),

                            IN in_email varchar(50), IN in_password varchar(100),

                            IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_user SET name=in_name, gender=in_gender, date_of_birth=in_date_of_birth, phone=in_phone, email=in_email, password=in_password, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateUserAccountStatus` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateUserAccountStatus`(IN in_id bigint, IN in_active tinyint(1),

                                         IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_user SET is_active=in_active, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateUserInformation` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateUserInformation`(IN in_id bigint, IN in_name varchar(100),

                                       IN in_gender enum ('MALE', 'FEMALE', 'OTHER'),

                                       IN in_date_of_birth date,

                                       IN in_email varchar(50),

                                       IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_user SET name=in_name, gender=in_gender, date_of_birth=in_date_of_birth, email=in_email, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!50003 DROP PROCEDURE IF EXISTS `sp_UpdateUserPassword` */;
/*!50003 SET @saved_cs_client      = @@character_set_client */ ;
/*!50003 SET @saved_cs_results     = @@character_set_results */ ;
/*!50003 SET @saved_col_connection = @@collation_connection */ ;
/*!50003 SET character_set_client  = utf8mb4 */ ;
/*!50003 SET character_set_results = utf8mb4 */ ;
/*!50003 SET collation_connection  = utf8mb4_0900_ai_ci */ ;
/*!50003 SET @saved_sql_mode       = @@sql_mode */ ;
/*!50003 SET sql_mode              = 'STRICT_TRANS_TABLES,NO_ENGINE_SUBSTITUTION' */ ;
DELIMITER ;;
CREATE DEFINER=`root`@`%` PROCEDURE `sp_UpdateUserPassword`(IN in_id bigint, IN in_password varchar(100),

                                    IN in_update_by varchar(100))
BEGIN

    UPDATE tbl_user SET password=in_password, update_by=in_update_by WHERE id=in_id;

END ;;
DELIMITER ;
/*!50003 SET sql_mode              = @saved_sql_mode */ ;
/*!50003 SET character_set_client  = @saved_cs_client */ ;
/*!50003 SET character_set_results = @saved_cs_results */ ;
/*!50003 SET collation_connection  = @saved_col_connection */ ;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-13  2:16:00
