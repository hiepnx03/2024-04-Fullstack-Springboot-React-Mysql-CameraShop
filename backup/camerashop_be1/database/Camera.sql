-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.36 - MySQL Community Server - GPL
-- Server OS:                    Win64
-- HeidiSQL Version:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for camerashop
CREATE DATABASE IF NOT EXISTS `camerashop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `camerashop`;

-- Dumping structure for table camerashop.cart_item
CREATE TABLE IF NOT EXISTS `cart_item` (
  `id_cart` int NOT NULL AUTO_INCREMENT,
  `quantity` int DEFAULT NULL,
  `id_product` int NOT NULL,
  `id_user` int NOT NULL,
  PRIMARY KEY (`id_cart`),
  KEY `FKpo098ad9k03gvvmwh3b9499bt` (`id_product`),
  KEY `FKikbqppvx730nfh1pu7av14wr3` (`id_user`),
  CONSTRAINT `FKikbqppvx730nfh1pu7av14wr3` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
  CONSTRAINT `FKpo098ad9k03gvvmwh3b9499bt` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.category
CREATE TABLE IF NOT EXISTS `category` (
  `id_category` int NOT NULL AUTO_INCREMENT,
  `category_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_category`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.delivery
CREATE TABLE IF NOT EXISTS `delivery` (
  `id_delivery` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fee_delivery` double DEFAULT NULL,
  `name_delivery` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_delivery`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.favorite_product
CREATE TABLE IF NOT EXISTS `favorite_product` (
  `id_favorite_product` int NOT NULL AUTO_INCREMENT,
  `id_product` int NOT NULL,
  `id_user` int NOT NULL,
  PRIMARY KEY (`id_favorite_product`),
  KEY `FK5ev2lmkbhnx862vi7y1ff4ale` (`id_product`),
  KEY `FK8hux1ftp33binuebaj82axitu` (`id_user`),
  CONSTRAINT `FK5ev2lmkbhnx862vi7y1ff4ale` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`),
  CONSTRAINT `FK8hux1ftp33binuebaj82axitu` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.feedback
CREATE TABLE IF NOT EXISTS `feedback` (
  `id_feedback` int NOT NULL AUTO_INCREMENT,
  `comment` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_created` date DEFAULT NULL,
  `is_readed` bit(1) DEFAULT NULL,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_user` int NOT NULL,
  PRIMARY KEY (`id_feedback`),
  KEY `FKiymqqp517nmqvubr9ye5rlrf1` (`id_user`),
  CONSTRAINT `FKiymqqp517nmqvubr9ye5rlrf1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.image
CREATE TABLE IF NOT EXISTS `image` (
  `id_image` int NOT NULL AUTO_INCREMENT,
  `data_image` longtext COLLATE utf8mb4_unicode_ci,
  `is_thumbnail` bit(1) DEFAULT NULL,
  `name_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `url_image` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `id_product` int NOT NULL,
  PRIMARY KEY (`id_image`),
  KEY `FK4sv36nv2o35hndsjxdcria3jf` (`id_product`),
  CONSTRAINT `FK4sv36nv2o35hndsjxdcria3jf` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id_order` int NOT NULL AUTO_INCREMENT,
  `date_created` date DEFAULT NULL,
  `delivery_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fee_delivery` double DEFAULT NULL,
  `fee_payment` double DEFAULT NULL,
  `full_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `note` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `status` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `total_price` double DEFAULT NULL,
  `total_price_product` double DEFAULT NULL,
  `id_delivery` int DEFAULT NULL,
  `id_payment` int DEFAULT NULL,
  `id_user` int NOT NULL,
  PRIMARY KEY (`id_order`),
  KEY `FK4cdgfu14t6vi93xhiugkyin2d` (`id_delivery`),
  KEY `FK5phng0rr9yex7v321tef65svq` (`id_payment`),
  KEY `FKp1jglhdt6fpf5plvbns0gp5ns` (`id_user`),
  CONSTRAINT `FK4cdgfu14t6vi93xhiugkyin2d` FOREIGN KEY (`id_delivery`) REFERENCES `delivery` (`id_delivery`),
  CONSTRAINT `FK5phng0rr9yex7v321tef65svq` FOREIGN KEY (`id_payment`) REFERENCES `payment` (`id_payment`),
  CONSTRAINT `FKp1jglhdt6fpf5plvbns0gp5ns` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.order_detail
CREATE TABLE IF NOT EXISTS `order_detail` (
  `id_order_detail` bigint NOT NULL AUTO_INCREMENT,
  `is_review` bit(1) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `id_order` int NOT NULL,
  `id_product` int NOT NULL,
  PRIMARY KEY (`id_order_detail`),
  KEY `FKsta0q8hk1lt2vdu92u4e5vr4a` (`id_order`),
  KEY `FKe7jb3j9qxkmw9p02g8uxdwbff` (`id_product`),
  CONSTRAINT `FKe7jb3j9qxkmw9p02g8uxdwbff` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`),
  CONSTRAINT `FKsta0q8hk1lt2vdu92u4e5vr4a` FOREIGN KEY (`id_order`) REFERENCES `orders` (`id_order`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.payment
CREATE TABLE IF NOT EXISTS `payment` (
  `id_payment` int NOT NULL AUTO_INCREMENT,
  `description` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `fee_payment` double DEFAULT NULL,
  `name_payment` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_payment`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.products
CREATE TABLE IF NOT EXISTS `products` (
  `id_product` int NOT NULL AUTO_INCREMENT,
  `avg_rating` double DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `discount_percent` int DEFAULT NULL,
  `list_price` double DEFAULT NULL,
  `product_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `sell_price` double DEFAULT NULL,
  `sold_quantity` int DEFAULT NULL,
  PRIMARY KEY (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.product_category
CREATE TABLE IF NOT EXISTS `product_category` (
  `id_product` int NOT NULL,
  `id_category` int NOT NULL,
  KEY `FK4y2ucymplqxycf58myn6abcv2` (`id_category`),
  KEY `FKgmq8cej1itivj3b6qtbon6r45` (`id_product`),
  CONSTRAINT `FK4y2ucymplqxycf58myn6abcv2` FOREIGN KEY (`id_category`) REFERENCES `category` (`id_category`),
  CONSTRAINT `FKgmq8cej1itivj3b6qtbon6r45` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.review
CREATE TABLE IF NOT EXISTS `review` (
  `id_review` bigint NOT NULL AUTO_INCREMENT,
  `content` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `rating_point` float DEFAULT NULL,
  `timestamp` datetime(6) DEFAULT NULL,
  `id_order_detail` bigint DEFAULT NULL,
  `id_product` int NOT NULL,
  `id_user` int NOT NULL,
  PRIMARY KEY (`id_review`),
  UNIQUE KEY `UK_3tpuisov8qyurqh23uu8ctmwp` (`id_order_detail`),
  KEY `FKjk9snebw0h3gwwpu6grtkn3vb` (`id_product`),
  KEY `FKebfdb78chwertfckv3kgu9anx` (`id_user`),
  CONSTRAINT `FKbflf4ds01tlvwpvvcxidwvj9d` FOREIGN KEY (`id_order_detail`) REFERENCES `order_detail` (`id_order_detail`),
  CONSTRAINT `FKebfdb78chwertfckv3kgu9anx` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`),
  CONSTRAINT `FKjk9snebw0h3gwwpu6grtkn3vb` FOREIGN KEY (`id_product`) REFERENCES `products` (`id_product`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.role
CREATE TABLE IF NOT EXISTS `role` (
  `id_role` int NOT NULL AUTO_INCREMENT,
  `name_role` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_role`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.user
CREATE TABLE IF NOT EXISTS `user` (
  `id_user` int NOT NULL AUTO_INCREMENT,
  `activation_code` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `avatar` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `date_of_birth` date DEFAULT NULL,
  `delivery_address` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `email` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `enabled` bit(1) DEFAULT NULL,
  `first_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `gender` char(1) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `last_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `password` varchar(512) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `phone_number` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `username` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

-- Dumping structure for table camerashop.user_role
CREATE TABLE IF NOT EXISTS `user_role` (
  `id_user` int NOT NULL,
  `id_role` int NOT NULL,
  KEY `FK2aam9nt2tv8vcfymi3jo9c314` (`id_role`),
  KEY `FKfhxaael2m459kbk8lv8smr5iv` (`id_user`),
  CONSTRAINT `FK2aam9nt2tv8vcfymi3jo9c314` FOREIGN KEY (`id_role`) REFERENCES `role` (`id_role`),
  CONSTRAINT `FKfhxaael2m459kbk8lv8smr5iv` FOREIGN KEY (`id_user`) REFERENCES `user` (`id_user`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- Data exporting was unselected.

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
