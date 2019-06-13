/*
SQLyog Professional v12.09 (64 bit)
MySQL - 5.7.18-log : Database - excel
*********************************************************************
*/


/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`excel` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE `excel`;

/*Table structure for table `user_info` */

DROP TABLE IF EXISTS `user_info`;

CREATE TABLE `user_info` (
	`user_id` varchar(32) NOT NULL COMMENT '用户身份证',
	`user_name` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名称',
	`user_sex` tinyint(2) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户性别，1：男，2：女',
	`user_born` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '出生日期',
	`user_phone` varchar(13) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户手机号',
	`user_email` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户邮箱',
	`user_qq` varchar(32) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '用户QQ号',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`create_time` timestamp NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`user_id`),
	key `idx_user_name` (`user_name`)
	) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

DROP TABLE IF EXISTS `graduate_info`;

CREATE TABLE `graduate_info` (
	`graduate_id` int(11) NOT NULL AUTO_INCREMENT COMMENT '毕业信息编号',
	`user_id` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户身份证',
	`graduate_school` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '毕业院校名称',
	`graduate_department` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '毕业院系',
	`graduate_major` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '毕业专业',
	`graduate_time` varchar(32) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '毕业时间',
	`graduate_year` tinyint(1) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '毕业学制',
	`graduate_degree` varchar(8) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '专业',
	`graduate_remark` varchar(32) COLLATE utf8mb4_unicode_ci COMMENT '备注',
	`update_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
	`create_time` timestamp NOT NULL COMMENT '创建时间',
	PRIMARY KEY (`graduate_id`),
	key `idx_user_id` (`user_id`),
	key `idx_graduate_name` (`graduate_school`)
	) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;