DROP TABLE IF EXISTS `cron_job`;

CREATE TABLE `cron_job` (
  `id` bigint(32) NOT NULL AUTO_INCREMENT,
  `job_cn_name` varchar(128) DEFAULT NULL COMMENT 'job的中文名称',
  `job_name` varchar(128) DEFAULT NULL COMMENT 'job的英文名称',
  `job_class` varchar(256) DEFAULT NULL COMMENT 'job执行的类',
  `cron` varchar(64) DEFAULT NULL COMMENT 'cron 执行表达式',
  `parameter` varchar(256) DEFAULT NULL COMMENT '参数',
  `comment` varchar(256) DEFAULT NULL COMMENT '备注',
  `success_mail` varchar(128) DEFAULT NULL COMMENT 'job执行成功发送的邮件',
  `fail_mail` varchar(128) DEFAULT NULL COMMENT 'job执行失败发送的邮件',
  `job_status` tinyint(2) DEFAULT 0 COMMENT '任务状态 0-禁用 1-启用',
  `create_time` timestamp not null DEFAULT current_timestamp comment '创建时间',
  `update_time` timestamp not null DEFAULT current_timestamp on update current_timestamp COMMENT '修改时间',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='定时任务配置表';

INSERT INTO `cron_job` VALUES (1, '周报报表', 'report', 'xyz.gabear.quartz.job.impl.DemoJob001', '0/20 * * * * ? ',
'{\r\n  \"id\": 84861,\r\n  \"node_type\": 1,\r\n  \"service_id\": 0,\r\n  \"option\": \"update\"\r\n}',
'周报报表', '88888888@qq.com', '88888888@qq.com', 1, '2018-10-28 17:58:19', '2018-10-28 19:59:41');
INSERT INTO `cron_job` VALUES (2, NULL, 'compute', 'xyz.gabear.quartz.job.back.DemoMutexJob002', '0/8 * * * * ? ',
'{   \"code\": 0,   \"data\": \"string\",   \"success\": true }',
'前端添加定时任务', '88888888@qq.com', '88888888@qq.com', 1, '2018-10-28 21:04:16', '2018-10-28 21:04:16');
