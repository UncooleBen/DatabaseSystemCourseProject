SET character_set_client = 'utf8';
SET character_set_connection = 'utf8';
SET character_set_database = 'utf8';
SET character_set_results = 'utf8';
SET character_set_server = 'utf8';

DROP DATABASE IF EXISTS `meethere`;

CREATE DATABASE `meethere`;

USE `meethere`;

DROP TABLE IF EXISTS `t_user`;

CREATE TABLE `t_user`
(
    `id`         INT(11) UNSIGNED          NOT NULL AUTO_INCREMENT,
    `username`   VARCHAR(20)               NOT NULL,
    `password`   VARCHAR(20)               NOT NULL,
    `name`       VARCHAR(20)               NOT NULL,
    `sex`        enum ('MALE','FEMALE','') NOT NULL,
    `permission` INT(11)                   NOT NULL,
    `tel`        VARCHAR(15) DEFAULT NULL,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `t_building`;

CREATE TABLE `t_building`
(
    `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `name`        VARCHAR(45),
    `description` VARCHAR(45),
    `price`       decimal(8, 2),
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `t_comment`;

CREATE TABLE `t_comment`
(
    `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `user_id`     INT(11) UNSIGNED,
    `building_id` INT(11) UNSIGNED,
    `date`        TIMESTAMP,
    `content`     TEXT,
    `verified`    TINYINT(1)       NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES t_user (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`building_id`) REFERENCES t_building (`id`) ON DELETE CASCADE
);

DROP TABLE IF EXISTS `t_news`;

CREATE TABLE `t_news`
(
    `id`            INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `title`         VARCHAR(45),
    `created`       TIMESTAMP,
    `last_modified` TIMESTAMP,
    `author`        VARCHAR(45),
    `detail`        TEXT,
    PRIMARY KEY (`id`)
);

DROP TABLE IF EXISTS `t_record`;

CREATE TABLE `t_record`
(
    `id`          INT(11) UNSIGNED NOT NULL AUTO_INCREMENT,
    `time`        TIMESTAMP,
    `start_date`  TIMESTAMP,
    `end_date`    TIMESTAMP,
    `user_id`     INT(11) UNSIGNED NOT NULL,
    `building_id` INT(11) UNSIGNED NOT NULL,
    `verified`    TINYINT(1)       NOT NULL DEFAULT '0',
    PRIMARY KEY (`id`),
    FOREIGN KEY (`user_id`) REFERENCES t_user (`id`) ON DELETE CASCADE,
    FOREIGN KEY (`building_id`) REFERENCES t_building (`id`) ON DELETE CASCADE
);

CREATE INDEX idx_comment_building ON `t_comment` (`building_id`);
