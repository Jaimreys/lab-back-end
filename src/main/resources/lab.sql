
SET FOREIGN_KEY_CHECKS=0;

-- ----------------------------
-- Table structure for exception_record
-- ----------------------------
DROP TABLE IF EXISTS `exception_record`;
CREATE TABLE `exception_record` (
                                    `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                    `code` int unsigned NOT NULL COMMENT '错误代码',
                                    `msg` varchar(5000) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '错误信息',
                                    `clazz` varchar(500) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '异常的类型',
                                    `location_class` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报出异常的类',
                                    `location_method` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报出异常的方法',
                                    `location_row_number` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '报出异常的行号',
                                    `time` datetime NOT NULL COMMENT '异常抛出时间',
                                    PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=452 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统抛出的异常记录';

-- ----------------------------
-- Table structure for leave_application
-- ----------------------------
DROP TABLE IF EXISTS `leave_application`;
CREATE TABLE `leave_application` (
                                     `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                     `proposer_username` bigint unsigned NOT NULL COMMENT '请假人的用户名',
                                     `start_time` datetime(6) NOT NULL COMMENT '假期开始时间',
                                     `end_time` datetime(6) NOT NULL COMMENT '假期结束时间',
                                     `reason` varchar(10000) DEFAULT NULL COMMENT '请假原因',
                                     `state` varchar(10) NOT NULL COMMENT '请假申请的状态',
                                     `checker_username` bigint DEFAULT NULL COMMENT '审核人的用户名',
                                     `disapproved_reason` varchar(10000) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '不批准原因',
                                     `check_time` datetime(6) DEFAULT NULL COMMENT '审核时间',
                                     PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=22 DEFAULT CHARSET=utf8 COMMENT='请假申请';

-- ----------------------------
-- Table structure for menu
-- ----------------------------
DROP TABLE IF EXISTS `menu`;
CREATE TABLE `menu` (
                        `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                        `icon` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT 'element-ui的图标名称',
                        `description` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '描述',
                        `path` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '路由路径',
                        `parent_menu_id` int unsigned DEFAULT NULL COMMENT '上级菜单的id',
                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单';

-- ----------------------------
-- Table structure for qrtz_blob_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_blob_triggers`;
CREATE TABLE `qrtz_blob_triggers` (
                                      `SCHED_NAME` varchar(120) NOT NULL,
                                      `TRIGGER_NAME` varchar(200) NOT NULL,
                                      `TRIGGER_GROUP` varchar(200) NOT NULL,
                                      `BLOB_DATA` blob,
                                      PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                      CONSTRAINT `qrtz_blob_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_calendars
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_calendars`;
CREATE TABLE `qrtz_calendars` (
                                  `SCHED_NAME` varchar(120) NOT NULL,
                                  `CALENDAR_NAME` varchar(200) NOT NULL,
                                  `CALENDAR` blob NOT NULL,
                                  PRIMARY KEY (`SCHED_NAME`,`CALENDAR_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_cron_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_cron_triggers`;
CREATE TABLE `qrtz_cron_triggers` (
                                      `SCHED_NAME` varchar(120) NOT NULL,
                                      `TRIGGER_NAME` varchar(200) NOT NULL,
                                      `TRIGGER_GROUP` varchar(200) NOT NULL,
                                      `CRON_EXPRESSION` varchar(200) NOT NULL,
                                      `TIME_ZONE_ID` varchar(80) DEFAULT NULL,
                                      PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                      CONSTRAINT `qrtz_cron_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_fired_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_fired_triggers`;
CREATE TABLE `qrtz_fired_triggers` (
                                       `SCHED_NAME` varchar(120) NOT NULL,
                                       `ENTRY_ID` varchar(95) NOT NULL,
                                       `TRIGGER_NAME` varchar(200) NOT NULL,
                                       `TRIGGER_GROUP` varchar(200) NOT NULL,
                                       `INSTANCE_NAME` varchar(200) NOT NULL,
                                       `FIRED_TIME` bigint NOT NULL,
                                       `SCHED_TIME` bigint NOT NULL,
                                       `PRIORITY` int NOT NULL,
                                       `STATE` varchar(16) NOT NULL,
                                       `JOB_NAME` varchar(200) DEFAULT NULL,
                                       `JOB_GROUP` varchar(200) DEFAULT NULL,
                                       `IS_NONCONCURRENT` varchar(1) DEFAULT NULL,
                                       `REQUESTS_RECOVERY` varchar(1) DEFAULT NULL,
                                       PRIMARY KEY (`SCHED_NAME`,`ENTRY_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_job_details
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_job_details`;
CREATE TABLE `qrtz_job_details` (
                                    `SCHED_NAME` varchar(120) NOT NULL,
                                    `JOB_NAME` varchar(200) NOT NULL,
                                    `JOB_GROUP` varchar(200) NOT NULL,
                                    `DESCRIPTION` varchar(250) DEFAULT NULL,
                                    `JOB_CLASS_NAME` varchar(250) NOT NULL,
                                    `IS_DURABLE` varchar(1) NOT NULL,
                                    `IS_NONCONCURRENT` varchar(1) NOT NULL,
                                    `IS_UPDATE_DATA` varchar(1) NOT NULL,
                                    `REQUESTS_RECOVERY` varchar(1) NOT NULL,
                                    `JOB_DATA` blob,
                                    PRIMARY KEY (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_locks
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_locks`;
CREATE TABLE `qrtz_locks` (
                              `SCHED_NAME` varchar(120) NOT NULL,
                              `LOCK_NAME` varchar(40) NOT NULL,
                              PRIMARY KEY (`SCHED_NAME`,`LOCK_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_paused_trigger_grps
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_paused_trigger_grps`;
CREATE TABLE `qrtz_paused_trigger_grps` (
                                            `SCHED_NAME` varchar(120) NOT NULL,
                                            `TRIGGER_GROUP` varchar(200) NOT NULL,
                                            PRIMARY KEY (`SCHED_NAME`,`TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_scheduler_state
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_scheduler_state`;
CREATE TABLE `qrtz_scheduler_state` (
                                        `SCHED_NAME` varchar(120) NOT NULL,
                                        `INSTANCE_NAME` varchar(200) NOT NULL,
                                        `LAST_CHECKIN_TIME` bigint NOT NULL,
                                        `CHECKIN_INTERVAL` bigint NOT NULL,
                                        PRIMARY KEY (`SCHED_NAME`,`INSTANCE_NAME`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simple_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simple_triggers`;
CREATE TABLE `qrtz_simple_triggers` (
                                        `SCHED_NAME` varchar(120) NOT NULL,
                                        `TRIGGER_NAME` varchar(200) NOT NULL,
                                        `TRIGGER_GROUP` varchar(200) NOT NULL,
                                        `REPEAT_COUNT` bigint NOT NULL,
                                        `REPEAT_INTERVAL` bigint NOT NULL,
                                        `TIMES_TRIGGERED` bigint NOT NULL,
                                        PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                        CONSTRAINT `qrtz_simple_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_simprop_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_simprop_triggers`;
CREATE TABLE `qrtz_simprop_triggers` (
                                         `SCHED_NAME` varchar(120) NOT NULL,
                                         `TRIGGER_NAME` varchar(200) NOT NULL,
                                         `TRIGGER_GROUP` varchar(200) NOT NULL,
                                         `STR_PROP_1` varchar(512) DEFAULT NULL,
                                         `STR_PROP_2` varchar(512) DEFAULT NULL,
                                         `STR_PROP_3` varchar(512) DEFAULT NULL,
                                         `INT_PROP_1` int DEFAULT NULL,
                                         `INT_PROP_2` int DEFAULT NULL,
                                         `LONG_PROP_1` bigint DEFAULT NULL,
                                         `LONG_PROP_2` bigint DEFAULT NULL,
                                         `DEC_PROP_1` decimal(13,4) DEFAULT NULL,
                                         `DEC_PROP_2` decimal(13,4) DEFAULT NULL,
                                         `BOOL_PROP_1` varchar(1) DEFAULT NULL,
                                         `BOOL_PROP_2` varchar(1) DEFAULT NULL,
                                         PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                         CONSTRAINT `qrtz_simprop_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`) REFERENCES `qrtz_triggers` (`SCHED_NAME`, `TRIGGER_NAME`, `TRIGGER_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for qrtz_triggers
-- ----------------------------
DROP TABLE IF EXISTS `qrtz_triggers`;
CREATE TABLE `qrtz_triggers` (
                                 `SCHED_NAME` varchar(120) NOT NULL,
                                 `TRIGGER_NAME` varchar(200) NOT NULL,
                                 `TRIGGER_GROUP` varchar(200) NOT NULL,
                                 `JOB_NAME` varchar(200) NOT NULL,
                                 `JOB_GROUP` varchar(200) NOT NULL,
                                 `DESCRIPTION` varchar(250) DEFAULT NULL,
                                 `NEXT_FIRE_TIME` bigint DEFAULT NULL,
                                 `PREV_FIRE_TIME` bigint DEFAULT NULL,
                                 `PRIORITY` int DEFAULT NULL,
                                 `TRIGGER_STATE` varchar(16) NOT NULL,
                                 `TRIGGER_TYPE` varchar(8) NOT NULL,
                                 `START_TIME` bigint NOT NULL,
                                 `END_TIME` bigint DEFAULT NULL,
                                 `CALENDAR_NAME` varchar(200) DEFAULT NULL,
                                 `MISFIRE_INSTR` smallint DEFAULT NULL,
                                 `JOB_DATA` blob,
                                 PRIMARY KEY (`SCHED_NAME`,`TRIGGER_NAME`,`TRIGGER_GROUP`),
                                 KEY `SCHED_NAME` (`SCHED_NAME`,`JOB_NAME`,`JOB_GROUP`),
                                 CONSTRAINT `qrtz_triggers_ibfk_1` FOREIGN KEY (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`) REFERENCES `qrtz_job_details` (`SCHED_NAME`, `JOB_NAME`, `JOB_GROUP`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for role_menu
-- ----------------------------
DROP TABLE IF EXISTS `role_menu`;
CREATE TABLE `role_menu` (
                             `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                             `role` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '角色',
                             `menu_id` int NOT NULL COMMENT '菜单id',
                             PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='菜单-角色关系表';

-- ----------------------------
-- Table structure for student_state_record
-- ----------------------------
DROP TABLE IF EXISTS `student_state_record`;
CREATE TABLE `student_state_record` (
                                        `id` int unsigned NOT NULL AUTO_INCREMENT COMMENT 'id',
                                        `username` bigint NOT NULL COMMENT '学生id',
                                        `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '学生姓名',
                                        `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '状态',
                                        `state_start_date` date DEFAULT NULL COMMENT '状态开始日期',
                                        `state_start_time` time NOT NULL COMMENT '状态开始时间（这个长度原来就是毫秒的长度）',
                                        `state_duration` int DEFAULT NULL COMMENT '状态持续时间（单位：秒）',
                                        PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='学生状态记录表';

-- ----------------------------
-- Table structure for system_user
-- ----------------------------
DROP TABLE IF EXISTS `system_user`;
CREATE TABLE `system_user` (
                               `username` bigint unsigned NOT NULL COMMENT '账号，学号或工号',
                               `real_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '姓名',
                               `password` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '密码，长度8-16位字母或数字',
                               `role` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '用户角色，学生、老师、管理员',
                               `state` varchar(10) CHARACTER SET utf8 COLLATE utf8_general_ci DEFAULT NULL COMMENT '状态',
                               PRIMARY KEY (`username`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC COMMENT='系统用户';
