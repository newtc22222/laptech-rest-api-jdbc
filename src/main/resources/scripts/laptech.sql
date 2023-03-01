-- phpMyAdmin SQL Dump
-- version 4.9.11
-- https://www.phpmyadmin.net/
--
-- Máy chủ: localhost:3306
-- Thời gian đã tạo: Th3 01, 2023 lúc 08:51 PM
-- Phiên bản máy phục vụ: 10.3.37-MariaDB-cll-lve
-- Phiên bản PHP: 7.4.33

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `youtube2_laptech`
--
CREATE DATABASE IF NOT EXISTS `youtube2_laptech` DEFAULT CHARACTER SET latin1 COLLATE latin1_swedish_ci;
USE `youtube2_laptech`;

DELIMITER $$
--
-- Thủ tục
--
DROP PROCEDURE IF EXISTS `sp_CheckExistAddress`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistBanner`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistBrand`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistCart`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistCategory`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistComment`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistDiscount`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistFeedback`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistImport`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistInvoice`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistLabel`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistProduct`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistProductImage`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistProductUnit`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistRole`$$
$$

DROP PROCEDURE IF EXISTS `sp_CheckExistUser`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAddressWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllAddress`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllBanner`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllBrand`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllCategory`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllComment`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllDiscount`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllFeedback`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllImport`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllInvoice`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllLabel`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllLogSystem`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllProduct`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllProductImage`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllProductUnit`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllRefreshToken`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllRole`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountAllUser`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountBannerWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountBrandWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountCategoryWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountCommentWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountDiscountWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountFeedbackWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountImportWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountInvoiceWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountLabelWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountLogSystemWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountProductImageWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountProductUnitWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountProductWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountRefreshTokenInDate`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountRoleWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_CountUserWithCondition`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteAddress`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteBanner`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteBrand`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteCart`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteCategory`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteComment`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteDiscount`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteFeedback`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteImport`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteInvoice`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteLabel`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteProduct`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteProductAccessory`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteProductDiscount`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteProductImage`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteProductLabel`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteProductUnit`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteRole`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteUser`$$
$$

DROP PROCEDURE IF EXISTS `sp_DeleteUserRole`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAccessoryByProductId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAddressById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAddressByUserId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllAddress`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllBanners`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllBrands`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllCategories`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllComments`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllDiscounts`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllFeedbacks`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllImports`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllInvoices`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllLabels`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllLogSystems`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllProductImages`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllProducts`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllProductUnits`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllRoles`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindAllUsers`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindBannerByDate`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindBannerByDateRange`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindBannerByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindBannerById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindBrandByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindBrandById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindCartByUserId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindCategoryByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindCategoryById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindCommentByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindCommentById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindCommentByProductId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindCommentByRootCommentId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindDiscountByDateRange`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindDiscountByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindDiscountById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindDiscountByProductId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindDiscountOfProductUseInDate`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindFeedbackByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindFeedbackById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindFeedbackByProductId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindFeedbackByUserId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindImportByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindImportById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindImportByProductId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindImportProductByDate`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindImportProductByDateRange`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindInvoiceByDate`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindInvoiceByDateRange`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindInvoiceByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindInvoiceById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindInvoiceByOrderStatus`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindInvoiceByUserId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindLabelByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindLabelById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindLabelByProductId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindLogSystemByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductByBrandId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductByCategoryId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductByLabelId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductByPriceRange`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductByReleasedYear`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductImageByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductImageById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductImageByImageType`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductImageByProductId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductUnitByCartId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductUnitByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductUnitById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindProductUnitByInvoiceId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindRefreshTokenByCode`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindRoleByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindRoleById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindRoleByName`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindRoleByUserId`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindUserByFilter`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindUserById`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindUserByPhone`$$
$$

DROP PROCEDURE IF EXISTS `sp_FindUserByRoleName`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewAddress`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewBanner`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewBrand`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewCart`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewCategory`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewComment`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewDiscount`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewFeedback`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewImport`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewInvoice`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewLabel`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewProduct`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewProductImage`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewProductUnit`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewRole`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertNewUser`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertProductAccessory`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertProductDiscount`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertProductLabel`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertRefreshToken`$$
$$

DROP PROCEDURE IF EXISTS `sp_InsertUserRole`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateAddress`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateBanner`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateBrand`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateCart`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateCategory`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateComment`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateDiscount`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateFeedback`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateImport`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateInvoice`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateInvoiceOrderStatus`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateInvoicePaymentTypeAndPaidStatus`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateLabel`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateProduct`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateProductImage`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateProductImageUrlAndType`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateProductPrice`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateProductUnit`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateRole`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateUser`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateUserAccountStatus`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateUserInformation`$$
$$

DROP PROCEDURE IF EXISTS `sp_UpdateUserPassword`$$
$$

DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_address`
--

DROP TABLE IF EXISTS `tbl_address`;
CREATE TABLE `tbl_address` (
  `id` varchar(25) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `country` varchar(25) NOT NULL,
  `line1` varchar(25) NOT NULL,
  `line2` varchar(25) NOT NULL,
  `line3` varchar(25) NOT NULL,
  `street` varchar(100) NOT NULL,
  `is_default` tinyint(1) NOT NULL DEFAULT 0,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_address`
--

INSERT INTO `tbl_address` (`id`, `user_id`, `country`, `line1`, `line2`, `line3`, `street`, `is_default`, `created_date`, `modified_date`, `deleted_date`, `is_del`, `update_by`) VALUES
('1', 1, 'Việt Nam', 'tỉnh An Giang', 'Thị xã Tân Châu', 'phường Long Hưng', 'số 1 đường Lạc Long Quân, khóm Long Thạnh C', 1, '2023-02-11 00:00:00', '2023-02-11 00:00:00', NULL, 0, 'system'),
('2', 2, 'Việt Nam', 'Việt Nam', 'Thành phố Thủ Đức', 'phường Linh Trung', '64/1L, đường số 17', 1, '2023-02-11 00:00:00', '2023-02-11 00:00:00', NULL, 0, 'system'),
('3', 3, 'Việt Nam', 'Việt Nam', 'Thành phố Thủ Đức', 'phường Linh Trung', '64/1L, đường số 17', 1, '2023-02-11 00:00:00', '2023-02-11 00:00:00', NULL, 0, 'system');

--
-- Bẫy `tbl_address`
--
DROP TRIGGER IF EXISTS `after_address_delete`;
DELIMITER $$
CREATE TRIGGER `after_address_delete` AFTER DELETE ON `tbl_address` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_address', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_address_insert`;
DELIMITER $$
CREATE TRIGGER `after_address_insert` AFTER INSERT ON `tbl_address` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_address', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_address_update`;
DELIMITER $$
CREATE TRIGGER `after_address_update` AFTER UPDATE ON `tbl_address` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_address', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_address_update`;
DELIMITER $$
CREATE TRIGGER `before_address_update` BEFORE UPDATE ON `tbl_address` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_banner`
--

DROP TABLE IF EXISTS `tbl_banner`;
CREATE TABLE `tbl_banner` (
  `id` bigint(20) NOT NULL,
  `path` varchar(255) NOT NULL,
  `type` varchar(50) NOT NULL,
  `title` varchar(100) NOT NULL,
  `link_product` varchar(255) DEFAULT NULL,
  `used_date` date NOT NULL,
  `ended_date` date NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_banner`
--

INSERT INTO `tbl_banner` (`id`, `path`, `type`, `title`, `link_product`, `used_date`, `ended_date`, `created_date`, `modified_date`, `deleted_date`, `is_del`, `update_by`) VALUES
(0, 'bbb', 'aaa', 'aaa', NULL, '2023-02-09', '2023-02-10', '2023-02-09 19:31:47', '2023-02-09 20:33:53', NULL, 0, 'system'),
(1, 'bbb', 'aaa', 'aaa', NULL, '2023-02-09', '2023-02-10', '2023-02-09 19:31:47', '2023-02-09 20:33:53', NULL, 0, 'system'),
(2, 'https://i.pinimg.com/originals/81/0b/4c/810b4ccbb372f6585b370d3f623ab55a.jpg', 'advertise', 'laptop', 'aaa', '2023-02-09', '2023-02-09', '2023-02-09 19:31:47', '2023-02-09 19:31:47', NULL, 0, 'system'),
(3, 'https://www.infobahnindia.net/wp-content/uploads/2018/10/HP-Business-Notebooks-Banner.jpg', 'advertise', 'laptop', 'aaa', '2023-02-09', '2023-02-09', '2023-02-09 19:31:47', '2023-02-09 19:31:47', NULL, 0, 'system'),
(4, 'https://www.infobahnindia.net/wp-content/uploads/2018/10/Hp-mobile-Workstation-banner.jpg', 'advertise', 'laptop', 'aaa', '2023-02-09', '2023-02-09', '2023-02-09 19:31:47', '2023-02-09 19:31:47', NULL, 0, 'system'),
(5, 'https://images.fpt.shop/unsafe/fit-in/1200x300/filters:quality(90):fill(white)/fptshop.com.vn/Uploads/Originals/2021/3/31/637528312299597503_F-C1_1200x300.png', 'advertise', 'laptop', 'aaa', '2023-02-09', '2023-02-09', '2023-02-09 19:31:47', '2023-02-09 19:31:47', NULL, 0, 'system'),
(6, 'https://digitalterminal.in/images/Acer_Diwali_Web_banner_2_for_DT-1200x300_px.jpg', 'advertise', 'laptop', 'aaa', '2023-02-09', '2023-02-09', '2023-02-09 19:31:47', '2023-02-09 19:31:47', NULL, 0, 'system'),
(7, 'https://fptshop.com.vn/Uploads/Originals/2020/7/7/637297265802454422_C1.png', 'advertise', 'laptop', 'aaa', '2023-02-09', '2023-02-09', '2023-02-09 19:31:47', '2023-02-09 19:31:47', NULL, 0, 'system');

--
-- Bẫy `tbl_banner`
--
DROP TRIGGER IF EXISTS `after_banner_delete`;
DELIMITER $$
CREATE TRIGGER `after_banner_delete` AFTER DELETE ON `tbl_banner` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_banner', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_banner_insert`;
DELIMITER $$
CREATE TRIGGER `after_banner_insert` AFTER INSERT ON `tbl_banner` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_banner', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_banner_update`;
DELIMITER $$
CREATE TRIGGER `after_banner_update` AFTER UPDATE ON `tbl_banner` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_banner', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_banner_update`;
DELIMITER $$
CREATE TRIGGER `before_banner_update` BEFORE UPDATE ON `tbl_banner` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_brand`
--

DROP TABLE IF EXISTS `tbl_brand`;
CREATE TABLE `tbl_brand` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `country` varchar(100) NOT NULL,
  `establish_date` date DEFAULT NULL,
  `logo` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_brand`
--

INSERT INTO `tbl_brand` (`id`, `name`, `country`, `establish_date`, `logo`, `created_date`, `modified_date`, `deleted_date`, `is_del`, `update_by`) VALUES
(0, 'Logitech', 'Switzerland', '1981-02-10', 'http://localhost:8088/api/v1/files/2793341afd484cb491f7e395315e75b0.png', '2023-02-12 23:01:58', '2023-02-23 21:27:54', '2023-02-23 21:27:54', 1, 'system'),
(2, 'DELL', 'US', '1984-02-01', 'http://localhost:8088/api/v1/files/769fddd0ce904b38954d6771be397dae.png', '2023-02-12 23:03:24', '2023-02-18 01:15:11', NULL, 0, 'system'),
(3, 'ASUS', 'Taiwan', '1989-04-02', 'http://localhost:8088/api/v1/files/95f6a56bfbd345678f1c7102ac06507c.png', '2023-02-18 01:27:39', '2023-02-18 01:31:25', NULL, 0, 'system'),
(4, 'ACER', 'Taiwan', '1976-04-01', 'http://localhost:8088/api/v1/files/d1945e27e0a34161ae13bced3d777957.png', '2023-02-18 01:38:54', '2023-02-18 22:54:43', NULL, 0, 'system'),
(6, 'Gigabyte', 'Taiwan', '1986-01-01', 'http://localhost:8088/api/v1/files/f22a2eb5ed5442bea57a399e7f1f23e5.png', '2023-02-18 01:49:12', '2023-02-18 23:51:53', NULL, 0, 'system'),
(7, 'Logitech', 'Hong Kong', '1986-11-01', 'http://localhost:8088/api/v1/files/43c22942399f44949c75da84fa6c03ec.png', '2023-02-18 23:26:51', '2023-02-18 23:29:37', NULL, 0, 'system'),
(8, 'HP', 'USA', '1939-07-02', 'http://localhost:8088/api/v1/files/459fdee58332438aaf1fa90f4eda5840.png', '2023-02-18 23:43:25', '2023-02-19 00:29:30', NULL, 0, 'system'),
(9, 'MSI', 'Taiwan', '1986-08-04', 'http://localhost:8088/api/v1/files/df7058ed79cc47528ed1378fb3a9d770.png', '2023-02-18 23:49:57', '2023-02-18 23:49:57', NULL, 0, 'system'),
(10, 'Apple', 'USA', '1976-04-01', 'http://localhost:8088/api/v1/files/ec6a85d77b7d400cac3ac945dbe22e9e.png', '2023-02-18 23:55:52', '2023-02-18 23:55:52', NULL, 0, 'system'),
(12, 'Microsoft', 'USA', '1975-04-04', 'http://localhost:8088/api/v1/files/22020ae7578c4641a917a0a492d1f8c2.png', '2023-02-19 00:01:10', '2023-02-19 00:01:10', NULL, 0, 'system'),
(13, 'Google', 'US', '1998-04-09', 'http://localhost:8088/api/v1/files/c7d6a85274b64d05b31638e4519d2bfe.png', '2023-02-19 00:03:14', '2023-02-19 00:03:14', NULL, 0, 'system'),
(14, 'Samsung', 'Korea', '1938-03-01', 'http://localhost:8088/api/v1/files/eeb087efba7549d398820ec0ef7f3419.png', '2023-02-19 00:07:11', '2023-02-19 00:07:11', NULL, 0, 'system'),
(15, 'Toshiba', 'Japan', '1875-11-07', 'http://localhost:8088/api/v1/files/ce47d4f6e3444e45b6ff9287e7b06a66.png', '2023-02-19 00:10:44', '2023-02-19 00:10:44', NULL, 0, 'system');

--
-- Bẫy `tbl_brand`
--
DROP TRIGGER IF EXISTS `after_brand_delete`;
DELIMITER $$
CREATE TRIGGER `after_brand_delete` AFTER DELETE ON `tbl_brand` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_brand', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_brand_insert`;
DELIMITER $$
CREATE TRIGGER `after_brand_insert` AFTER INSERT ON `tbl_brand` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_brand', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_brand_update`;
DELIMITER $$
CREATE TRIGGER `after_brand_update` AFTER UPDATE ON `tbl_brand` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_brand', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_brand_update`;
DELIMITER $$
CREATE TRIGGER `before_brand_update` BEFORE UPDATE ON `tbl_brand` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_cart`
--

DROP TABLE IF EXISTS `tbl_cart`;
CREATE TABLE `tbl_cart` (
  `id` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `discount_id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_cart`
--
DROP TRIGGER IF EXISTS `after_cart_delete`;
DELIMITER $$
CREATE TRIGGER `after_cart_delete` AFTER DELETE ON `tbl_cart` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_cart', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_cart_insert`;
DELIMITER $$
CREATE TRIGGER `after_cart_insert` AFTER INSERT ON `tbl_cart` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_cart', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_cart_update`;
DELIMITER $$
CREATE TRIGGER `after_cart_update` AFTER UPDATE ON `tbl_cart` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_cart', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_cart_update`;
DELIMITER $$
CREATE TRIGGER `before_cart_update` BEFORE UPDATE ON `tbl_cart` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_category`
--

DROP TABLE IF EXISTS `tbl_category`;
CREATE TABLE `tbl_category` (
  `id` bigint(20) NOT NULL,
  `name` varchar(50) NOT NULL,
  `image` varchar(255) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_category`
--

INSERT INTO `tbl_category` (`id`, `name`, `image`, `description`, `created_date`, `modified_date`, `deleted_date`, `is_del`, `update_by`) VALUES
(1, 'Laptop', 'http://localhost:8088/api/v1/files/4599305e04cf4632baaa7fd01dc22802.png', 'Thiết bị dùng hằng ngày của con dân công nghệ bên cạnh cục gạch', '2023-02-19 01:01:02', '2023-02-19 01:10:21', NULL, 0, 'system'),
(2, 'Screen', 'http://localhost:8088/api/v1/files/d41436e8c40548d9af05e6e321b0e7cb.jpg', 'Màn hình máy tính dùng cực chill~!', '2023-02-19 01:14:26', '2023-02-19 01:18:02', NULL, 0, 'system');

--
-- Bẫy `tbl_category`
--
DROP TRIGGER IF EXISTS `after_category_delete`;
DELIMITER $$
CREATE TRIGGER `after_category_delete` AFTER DELETE ON `tbl_category` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_category', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_category_insert`;
DELIMITER $$
CREATE TRIGGER `after_category_insert` AFTER INSERT ON `tbl_category` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_category', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_category_update`;
DELIMITER $$
CREATE TRIGGER `after_category_update` AFTER UPDATE ON `tbl_category` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_category', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_category_update`;
DELIMITER $$
CREATE TRIGGER `before_category_update` BEFORE UPDATE ON `tbl_category` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_comment`
--

DROP TABLE IF EXISTS `tbl_comment`;
CREATE TABLE `tbl_comment` (
  `id` varchar(25) NOT NULL,
  `root_comment_id` varchar(25) DEFAULT NULL,
  `product_id` varchar(50) NOT NULL,
  `username` varchar(100) NOT NULL,
  `phone` char(15) NOT NULL,
  `content` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_comment`
--
DROP TRIGGER IF EXISTS `after_comment_delete`;
DELIMITER $$
CREATE TRIGGER `after_comment_delete` AFTER DELETE ON `tbl_comment` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_comment', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_comment_insert`;
DELIMITER $$
CREATE TRIGGER `after_comment_insert` AFTER INSERT ON `tbl_comment` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_comment', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_comment_update`;
DELIMITER $$
CREATE TRIGGER `after_comment_update` AFTER UPDATE ON `tbl_comment` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_comment', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_comment_update`;
DELIMITER $$
CREATE TRIGGER `before_comment_update` BEFORE UPDATE ON `tbl_comment` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_discount`
--

DROP TABLE IF EXISTS `tbl_discount`;
CREATE TABLE `tbl_discount` (
  `id` bigint(20) NOT NULL,
  `code` varchar(20) NOT NULL,
  `rate` float UNSIGNED NOT NULL,
  `applied_type` enum('PRODUCT','PURCHASE') NOT NULL DEFAULT 'PRODUCT',
  `max_amount` decimal(21,4) UNSIGNED NOT NULL DEFAULT 0.0000,
  `applied_date` datetime NOT NULL,
  `ended_date` datetime NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_discount`
--

INSERT INTO `tbl_discount` (`id`, `code`, `rate`, `applied_type`, `max_amount`, `applied_date`, `ended_date`, `created_date`, `modified_date`, `deleted_date`, `is_del`, `update_by`) VALUES
(1, 'BANOIDUNGNGAI', 0.08, 'PRODUCT', '120000.0000', '2023-02-18 07:30:00', '2023-02-28 22:00:00', '2023-02-19 01:30:58', '2023-02-19 01:30:58', NULL, 0, 'system');

--
-- Bẫy `tbl_discount`
--
DROP TRIGGER IF EXISTS `after_discount_delete`;
DELIMITER $$
CREATE TRIGGER `after_discount_delete` AFTER DELETE ON `tbl_discount` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_discount', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_discount_insert`;
DELIMITER $$
CREATE TRIGGER `after_discount_insert` AFTER INSERT ON `tbl_discount` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_discount', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_discount_update`;
DELIMITER $$
CREATE TRIGGER `after_discount_update` AFTER UPDATE ON `tbl_discount` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_discount', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_discount_update`;
DELIMITER $$
CREATE TRIGGER `before_discount_update` BEFORE UPDATE ON `tbl_discount` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_feedback`
--

DROP TABLE IF EXISTS `tbl_feedback`;
CREATE TABLE `tbl_feedback` (
  `id` varchar(25) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `content` varchar(255) NOT NULL,
  `rating_point` tinyint(3) UNSIGNED NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_feedback`
--
DROP TRIGGER IF EXISTS `after_feedback_delete`;
DELIMITER $$
CREATE TRIGGER `after_feedback_delete` AFTER DELETE ON `tbl_feedback` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_feedback', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_feedback_insert`;
DELIMITER $$
CREATE TRIGGER `after_feedback_insert` AFTER INSERT ON `tbl_feedback` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_feedback', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_feedback_update`;
DELIMITER $$
CREATE TRIGGER `after_feedback_update` AFTER UPDATE ON `tbl_feedback` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_feedback', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_feedback_update`;
DELIMITER $$
CREATE TRIGGER `before_feedback_update` BEFORE UPDATE ON `tbl_feedback` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_import`
--

DROP TABLE IF EXISTS `tbl_import`;
CREATE TABLE `tbl_import` (
  `id` varchar(25) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `quantity` bigint(20) UNSIGNED NOT NULL,
  `import_price` decimal(21,4) UNSIGNED NOT NULL DEFAULT 0.0000,
  `import_date` datetime NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_import`
--
DROP TRIGGER IF EXISTS `after_import_delete`;
DELIMITER $$
CREATE TRIGGER `after_import_delete` AFTER DELETE ON `tbl_import` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_import', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_import_insert`;
DELIMITER $$
CREATE TRIGGER `after_import_insert` AFTER INSERT ON `tbl_import` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_import', new.id, 'INSERT', new.modified_date, new.update_by);
  
UPDATE `laptech`.`tbl_product` 
SET 
    quantity_in_stock = quantity_in_stock + new.quantity
WHERE
    id = new.product_id;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_import_update`;
DELIMITER $$
CREATE TRIGGER `after_import_update` AFTER UPDATE ON `tbl_import` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_import', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_import_update`;
DELIMITER $$
CREATE TRIGGER `before_import_update` BEFORE UPDATE ON `tbl_import` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_invoice`
--

DROP TABLE IF EXISTS `tbl_invoice`;
CREATE TABLE `tbl_invoice` (
  `id` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `phone` char(15) NOT NULL,
  `payment_amount` double UNSIGNED NOT NULL,
  `ship_cost` double UNSIGNED NOT NULL,
  `discount_amount` double UNSIGNED DEFAULT 0,
  `tax` double UNSIGNED DEFAULT 0,
  `payment_total` double UNSIGNED NOT NULL,
  `payment_type` varchar(50) NOT NULL,
  `is_paid` tinyint(1) NOT NULL DEFAULT 0,
  `order_status` enum('PENDING','WAIT_CONFIRMED','PREPARED','SHIPPED','RECEIVED','CANCELED','FAILED','IGNORED') NOT NULL DEFAULT 'PENDING',
  `note` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_invoice`
--
DROP TRIGGER IF EXISTS `after_invoice_delete`;
DELIMITER $$
CREATE TRIGGER `after_invoice_delete` AFTER DELETE ON `tbl_invoice` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_invoice', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_invoice_insert`;
DELIMITER $$
CREATE TRIGGER `after_invoice_insert` AFTER INSERT ON `tbl_invoice` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_invoice', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_invoice_update`;
DELIMITER $$
CREATE TRIGGER `after_invoice_update` AFTER UPDATE ON `tbl_invoice` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_invoice', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_invoice_update`;
DELIMITER $$
CREATE TRIGGER `before_invoice_update` BEFORE UPDATE ON `tbl_invoice` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_label`
--

DROP TABLE IF EXISTS `tbl_label`;
CREATE TABLE `tbl_label` (
  `id` bigint(20) NOT NULL,
  `name` varchar(25) NOT NULL,
  `icon` varchar(255) NOT NULL,
  `title` varchar(50) NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_label`
--
DROP TRIGGER IF EXISTS `after_label_delete`;
DELIMITER $$
CREATE TRIGGER `after_label_delete` AFTER DELETE ON `tbl_label` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_label', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_label_insert`;
DELIMITER $$
CREATE TRIGGER `after_label_insert` AFTER INSERT ON `tbl_label` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_label', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_label_update`;
DELIMITER $$
CREATE TRIGGER `after_label_update` AFTER UPDATE ON `tbl_label` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_label', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_label_update`;
DELIMITER $$
CREATE TRIGGER `before_label_update` BEFORE UPDATE ON `tbl_label` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_log_system`
--

DROP TABLE IF EXISTS `tbl_log_system`;
CREATE TABLE `tbl_log_system` (
  `id` bigint(11) NOT NULL,
  `action_table` varchar(50) NOT NULL,
  `record_id` varchar(50) NOT NULL,
  `action_time` varchar(50) NOT NULL,
  `action_by` varchar(50) NOT NULL DEFAULT 'system',
  `action_name` varchar(50) NOT NULL DEFAULT 'UPDATE'
) ENGINE=InnoDB DEFAULT CHARSET=latin1 COLLATE=latin1_swedish_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_log_system`
--

INSERT INTO `tbl_log_system` (`id`, `action_table`, `record_id`, `action_time`, `action_by`, `action_name`) VALUES
(1, 'tbl_brand', '1', '2023-02-23 21:25:46', 'system', 'UPDATE'),
(2, 'tbl_brand', '1', '2023-02-23 21:27:12', 'system', 'DESTROY'),
(3, 'tbl_brand', '0', '2023-02-23 21:27:54', 'system', 'DELETE');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_product`
--

DROP TABLE IF EXISTS `tbl_product`;
CREATE TABLE `tbl_product` (
  `id` varchar(50) NOT NULL,
  `brand_id` bigint(20) NOT NULL,
  `category_id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `released_date` date NOT NULL,
  `quantity_in_stock` int(11) NOT NULL,
  `listed_price` double NOT NULL,
  `specifications` varchar(255) DEFAULT NULL,
  `description_detail` varchar(255) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Bẫy `tbl_product`
--
DROP TRIGGER IF EXISTS `after_product_delete`;
DELIMITER $$
CREATE TRIGGER `after_product_delete` AFTER DELETE ON `tbl_product` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_insert`;
DELIMITER $$
CREATE TRIGGER `after_product_insert` AFTER INSERT ON `tbl_product` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_update`;
DELIMITER $$
CREATE TRIGGER `after_product_update` AFTER UPDATE ON `tbl_product` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_product_update`;
DELIMITER $$
CREATE TRIGGER `before_product_update` BEFORE UPDATE ON `tbl_product` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_product_accessory`
--

DROP TABLE IF EXISTS `tbl_product_accessory`;
CREATE TABLE `tbl_product_accessory` (
  `product_id` varchar(50) NOT NULL,
  `accessory_id` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_product_accessory`
--
DROP TRIGGER IF EXISTS `after_product_accessory_delete`;
DELIMITER $$
CREATE TRIGGER `after_product_accessory_delete` AFTER DELETE ON `tbl_product_accessory` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_accessory', old.accessory_id, 'DESTROY', now(), 'system');
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_accessory_insert`;
DELIMITER $$
CREATE TRIGGER `after_product_accessory_insert` AFTER INSERT ON `tbl_product_accessory` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_accessory', new.accessory_id, 'ADD', now(), 'system');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_product_discount`
--

DROP TABLE IF EXISTS `tbl_product_discount`;
CREATE TABLE `tbl_product_discount` (
  `product_id` varchar(50) NOT NULL,
  `discount_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Bẫy `tbl_product_discount`
--
DROP TRIGGER IF EXISTS `after_product_discount_delete`;
DELIMITER $$
CREATE TRIGGER `after_product_discount_delete` AFTER DELETE ON `tbl_product_discount` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_discount', old.discount_id, 'DESTROY', now(), 'system');
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_discount_insert`;
DELIMITER $$
CREATE TRIGGER `after_product_discount_insert` AFTER INSERT ON `tbl_product_discount` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_discount', new.discount_id, 'ADD', now(), 'system');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_product_image`
--

DROP TABLE IF EXISTS `tbl_product_image`;
CREATE TABLE `tbl_product_image` (
  `id` varchar(50) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `feedback_id` varchar(25) NOT NULL,
  `url` varchar(255) NOT NULL,
  `type` enum('ADVERTISE','DETAIL','EXTRA','FEEDBACK') NOT NULL DEFAULT 'ADVERTISE',
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_product_image`
--
DROP TRIGGER IF EXISTS `after_product_image_delete`;
DELIMITER $$
CREATE TRIGGER `after_product_image_delete` AFTER DELETE ON `tbl_product_image` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_image', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_image_insert`;
DELIMITER $$
CREATE TRIGGER `after_product_image_insert` AFTER INSERT ON `tbl_product_image` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_image', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_image_update`;
DELIMITER $$
CREATE TRIGGER `after_product_image_update` AFTER UPDATE ON `tbl_product_image` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_image', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_product_image_update`;
DELIMITER $$
CREATE TRIGGER `before_product_image_update` BEFORE UPDATE ON `tbl_product_image` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_product_label`
--

DROP TABLE IF EXISTS `tbl_product_label`;
CREATE TABLE `tbl_product_label` (
  `product_id` varchar(50) NOT NULL,
  `label_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_product_label`
--
DROP TRIGGER IF EXISTS `after_product_label_delete`;
DELIMITER $$
CREATE TRIGGER `after_product_label_delete` AFTER DELETE ON `tbl_product_label` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_label', old.label_id, 'DESTROY', now(), 'system');
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_label_insert`;
DELIMITER $$
CREATE TRIGGER `after_product_label_insert` AFTER INSERT ON `tbl_product_label` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_label', new.label_id, 'ADD', now(), 'system');
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_product_unit`
--

DROP TABLE IF EXISTS `tbl_product_unit`;
CREATE TABLE `tbl_product_unit` (
  `id` varchar(25) NOT NULL,
  `product_id` varchar(50) NOT NULL,
  `cart_id` varchar(50) DEFAULT NULL,
  `invoice_id` varchar(50) DEFAULT NULL,
  `quantity` int(10) UNSIGNED NOT NULL,
  `price` double UNSIGNED NOT NULL,
  `discount_price` double UNSIGNED NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Bẫy `tbl_product_unit`
--
DROP TRIGGER IF EXISTS `after_product_unit_delete`;
DELIMITER $$
CREATE TRIGGER `after_product_unit_delete` AFTER DELETE ON `tbl_product_unit` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_unit', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_unit_insert`;
DELIMITER $$
CREATE TRIGGER `after_product_unit_insert` AFTER INSERT ON `tbl_product_unit` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_unit', new.id, 'INSERT', new.modified_date, new.update_by);
  
  	if new.invoice_id is not null then
		update tbl_product
		set tbl_product.quantity_in_stock = 
		if(quantity_in_stock - new.quantity >= 0, quantity_in_stock - new.quantity, quantity_in_stock)
        where tbl_product.id = new.product_id;
    end if;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_product_unit_update`;
DELIMITER $$
CREATE TRIGGER `after_product_unit_update` AFTER UPDATE ON `tbl_product_unit` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  declare range_diff int;
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_product_unit', new.id, in_action_name, new.modified_date, new.update_by);
  
    set range_diff = new.quantity - old.quantity;
    if new.invoice_id is not null then
		if range_diff = 0 then -- change from cart => invoice
			update tbl_product
			set tbl_product.quantity_in_stock = 
			if(quantity_in_stock - new.quantity >= 0, quantity_in_stock - new.quantity, quantity_in_stock)
			where tbl_product.id = new.product_id;
        else -- update invoice quantity (return | fix_quantity)
			update tbl_product 
			set tbl_product.quantity_in_stock = tbl_product.quantity_in_stock - range_diff
			where tbl_product.id = new.product_id;
        end if;
    end if;
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_product_unit_update`;
DELIMITER $$
CREATE TRIGGER `before_product_unit_update` BEFORE UPDATE ON `tbl_product_unit` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_refresh_token`
--

DROP TABLE IF EXISTS `tbl_refresh_token`;
CREATE TABLE `tbl_refresh_token` (
  `code` varchar(50) NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `expired_date` datetime NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Bẫy `tbl_refresh_token`
--
DROP TRIGGER IF EXISTS `tbl_refresh_token_BEFORE_INSERT`;
DELIMITER $$
CREATE TRIGGER `tbl_refresh_token_BEFORE_INSERT` BEFORE INSERT ON `tbl_refresh_token` FOR EACH ROW BEGIN
	set new.expired_date = adddate(now(), interval 2 day);
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_role`
--

DROP TABLE IF EXISTS `tbl_role`;
CREATE TABLE `tbl_role` (
  `id` int(11) NOT NULL,
  `name` varchar(25) NOT NULL,
  `description` varchar(100) DEFAULT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_role`
--

INSERT INTO `tbl_role` (`id`, `name`, `description`, `created_date`, `modified_date`, `deleted_date`, `is_del`, `update_by`) VALUES
(1, 'ADMIN', 'Administrator', '2023-02-20 21:29:15', '2023-02-20 21:29:15', NULL, 0, 'system'),
(2, 'MANAGER', 'User of admin system', '2023-02-20 21:29:15', '2023-02-20 21:29:15', NULL, 0, 'system'),
(3, 'USER', 'User of a client system', '2023-02-20 21:29:15', '2023-02-20 21:29:15', NULL, 0, 'system');

--
-- Bẫy `tbl_role`
--
DROP TRIGGER IF EXISTS `after_role_delete`;
DELIMITER $$
CREATE TRIGGER `after_role_delete` AFTER DELETE ON `tbl_role` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_role', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_role_insert`;
DELIMITER $$
CREATE TRIGGER `after_role_insert` AFTER INSERT ON `tbl_role` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_role', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_role_update`;
DELIMITER $$
CREATE TRIGGER `after_role_update` AFTER UPDATE ON `tbl_role` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_role', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_role_update`;
DELIMITER $$
CREATE TRIGGER `before_role_update` BEFORE UPDATE ON `tbl_role` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_user`
--

DROP TABLE IF EXISTS `tbl_user`;
CREATE TABLE `tbl_user` (
  `id` bigint(20) NOT NULL,
  `name` varchar(100) NOT NULL,
  `gender` enum('MALE','FEMALE','OTHER') NOT NULL DEFAULT 'MALE',
  `date_of_birth` date NOT NULL DEFAULT '2000-01-01',
  `phone` char(13) NOT NULL,
  `email` varchar(50) DEFAULT NULL,
  `password` varchar(100) NOT NULL,
  `is_active` tinyint(1) NOT NULL,
  `created_date` datetime NOT NULL DEFAULT current_timestamp(),
  `modified_date` datetime NOT NULL DEFAULT current_timestamp(),
  `deleted_date` datetime DEFAULT NULL,
  `is_del` tinyint(1) NOT NULL DEFAULT 0,
  `update_by` varchar(100) NOT NULL DEFAULT 'system'
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_vietnamese_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_user`
--

INSERT INTO `tbl_user` (`id`, `name`, `gender`, `date_of_birth`, `phone`, `email`, `password`, `is_active`, `created_date`, `modified_date`, `deleted_date`, `is_del`, `update_by`) VALUES
(1, 'Võ Nhật Phi', 'MALE', '2001-04-17', '+84947679570', NULL, '$2a$10$Qwl/CsI69E42Hz3y.AVoGuq8CDWOtl/ulo6yZPU5Kkkzb68NUDYF2', 1, '2023-02-20 21:27:28', '2023-02-20 21:27:28', NULL, 0, 'system');

--
-- Bẫy `tbl_user`
--
DROP TRIGGER IF EXISTS `after_user_delete`;
DELIMITER $$
CREATE TRIGGER `after_user_delete` AFTER DELETE ON `tbl_user` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_user', old.id, 'DESTROY', now(), old.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_user_insert`;
DELIMITER $$
CREATE TRIGGER `after_user_insert` AFTER INSERT ON `tbl_user` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_user', new.id, 'INSERT', new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_user_update`;
DELIMITER $$
CREATE TRIGGER `after_user_update` AFTER UPDATE ON `tbl_user` FOR EACH ROW BEGIN
  DECLARE in_action_name VARCHAR(10); 
  SET in_action_name = 'UPDATE';
  IF new.is_del = 1 THEN
    SET in_action_name = 'DELETE';
  END IF;
  IF new.is_del = 0 and old.is_del = 1 THEN
    SET in_action_name = 'RESTORE';
  END IF;
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_user', new.id, in_action_name, new.modified_date, new.update_by);
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `before_user_update`;
DELIMITER $$
CREATE TRIGGER `before_user_update` BEFORE UPDATE ON `tbl_user` FOR EACH ROW BEGIN
  set new.modified_date = now(); 
  IF new.is_del = 1 THEN
    set new.deleted_date = now();
  END IF;
END
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `tbl_user_role`
--

DROP TABLE IF EXISTS `tbl_user_role`;
CREATE TABLE `tbl_user_role` (
  `user_id` bigint(20) NOT NULL,
  `role_id` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_general_ci;

--
-- Đang đổ dữ liệu cho bảng `tbl_user_role`
--

INSERT INTO `tbl_user_role` (`user_id`, `role_id`) VALUES
(1, 1),
(1, 3);

--
-- Bẫy `tbl_user_role`
--
DROP TRIGGER IF EXISTS `after_user_role_delete`;
DELIMITER $$
CREATE TRIGGER `after_user_role_delete` AFTER DELETE ON `tbl_user_role` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_user_role', old.role_id, 'DESTROY', now(), 'system');
END
$$
DELIMITER ;
DROP TRIGGER IF EXISTS `after_user_role_insert`;
DELIMITER $$
CREATE TRIGGER `after_user_role_insert` AFTER INSERT ON `tbl_user_role` FOR EACH ROW BEGIN
  INSERT INTO tbl_log_system(action_table, record_id, action_name, action_time, action_by) 
  VALUES ('tbl_user_role', new.role_id, 'ADD', now(), 'system');
END
$$
DELIMITER ;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `tbl_address`
--
ALTER TABLE `tbl_address`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_banner`
--
ALTER TABLE `tbl_banner`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_brand`
--
ALTER TABLE `tbl_brand`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_cart`
--
ALTER TABLE `tbl_cart`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_category`
--
ALTER TABLE `tbl_category`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_comment`
--
ALTER TABLE `tbl_comment`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_discount`
--
ALTER TABLE `tbl_discount`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_feedback`
--
ALTER TABLE `tbl_feedback`
  ADD PRIMARY KEY (`id`,`product_id`,`user_id`);

--
-- Chỉ mục cho bảng `tbl_import`
--
ALTER TABLE `tbl_import`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_invoice`
--
ALTER TABLE `tbl_invoice`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_label`
--
ALTER TABLE `tbl_label`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_log_system`
--
ALTER TABLE `tbl_log_system`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_product`
--
ALTER TABLE `tbl_product`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_product_accessory`
--
ALTER TABLE `tbl_product_accessory`
  ADD PRIMARY KEY (`product_id`,`accessory_id`);

--
-- Chỉ mục cho bảng `tbl_product_discount`
--
ALTER TABLE `tbl_product_discount`
  ADD PRIMARY KEY (`product_id`,`discount_id`);

--
-- Chỉ mục cho bảng `tbl_product_image`
--
ALTER TABLE `tbl_product_image`
  ADD PRIMARY KEY (`id`);

--
-- Chỉ mục cho bảng `tbl_product_label`
--
ALTER TABLE `tbl_product_label`
  ADD PRIMARY KEY (`product_id`,`label_id`);

--
-- Chỉ mục cho bảng `tbl_product_unit`
--
ALTER TABLE `tbl_product_unit`
  ADD PRIMARY KEY (`id`,`product_id`);

--
-- Chỉ mục cho bảng `tbl_refresh_token`
--
ALTER TABLE `tbl_refresh_token`
  ADD PRIMARY KEY (`code`);

--
-- Chỉ mục cho bảng `tbl_role`
--
ALTER TABLE `tbl_role`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `name_UNIQUE` (`name`);

--
-- Chỉ mục cho bảng `tbl_user`
--
ALTER TABLE `tbl_user`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `phone_UNIQUE` (`phone`);

--
-- Chỉ mục cho bảng `tbl_user_role`
--
ALTER TABLE `tbl_user_role`
  ADD PRIMARY KEY (`user_id`,`role_id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `tbl_banner`
--
ALTER TABLE `tbl_banner`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `tbl_brand`
--
ALTER TABLE `tbl_brand`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=16;

--
-- AUTO_INCREMENT cho bảng `tbl_discount`
--
ALTER TABLE `tbl_discount`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT cho bảng `tbl_label`
--
ALTER TABLE `tbl_label`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `tbl_log_system`
--
ALTER TABLE `tbl_log_system`
  MODIFY `id` bigint(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tbl_role`
--
ALTER TABLE `tbl_role`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `tbl_user`
--
ALTER TABLE `tbl_user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
