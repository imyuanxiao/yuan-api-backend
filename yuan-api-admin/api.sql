CREATE DATABASE IF NOT EXISTS `yuan_api` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci */;

USE yuan_api;

CREATE TABLE IF NOT EXISTS `interface` (
                           `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                           `name` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '名称',
                           `description` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '描述',
                           `path` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '接口地址',
                           `url` varchar(512) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求主机',
                           `request_param` json DEFAULT NULL COMMENT '请求参数',
                           `request_param_remark` json DEFAULT NULL COMMENT '请求参数说明（名称、是否必填、类型、说明）',
                           `response_param_remark` json DEFAULT NULL COMMENT '响应参数说明（名称、类型、说明）',
                           `request_header` json DEFAULT NULL COMMENT '请求头',
                           `response_header` json DEFAULT NULL COMMENT '响应头',
                           `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '接口状态（0-关闭，1-开启）',
                           `method` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '请求类型',
                           `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                           `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                           `user_id` int(11) NOT NULL COMMENT '创建人ID',
                           `is_deleted` tinyint(1) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0-未删除，1-已删除）',
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='接口信息表';


CREATE TABLE IF NOT EXISTS `user` (
                      `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                      `username` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '用户名',
                      `password` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密码（经过加密）',
                      `phone` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '手机号',
                      `email` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '邮箱',
                      `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '用户状态（0-正常，1-禁用）',
                      `role` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT 'user' COMMENT '用户角色（admin/user）',
                      `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                      `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                      `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0-未删除，1-已删除）',
                      PRIMARY KEY (`id`),
                      UNIQUE KEY `uk` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户表（保存敏感信息）';

CREATE TABLE IF NOT EXISTS  `user_interface` (
                                `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                                `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                `interface_id` bigint(20) NOT NULL COMMENT '接口ID',
                                `access_key` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '访问凭证',
                                `secret_key` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '密钥',
                                `left_num` int(11) NOT NULL COMMENT '剩余调用次数',
                                `total_num` int(11) NOT NULL COMMENT '总调用次数',
                                `status` tinyint(4) NOT NULL DEFAULT '0' COMMENT '接口状态（0-正常，1-禁用）',
                                `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT 'created time',
                                `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0-未删除，1-已删除）',
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户_接口关系表';

CREATE TABLE IF NOT EXISTS  `user_interface_history` (
                                        `id` bigint(20) NOT NULL COMMENT '主键',
                                        `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                                        `interface_id` bigint(20) NOT NULL COMMENT '接口ID',
                                        `result` tinyint(4) NOT NULL COMMENT '调用结果（0-成功，1-失败）',
                                        `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0-未删除，1-已删除）',
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户_接口调用历史表';

CREATE TABLE IF NOT EXISTS  `user_profile` (
                              `id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '主键',
                              `user_id` bigint(20) NOT NULL COMMENT '用户ID',
                              `nick_name` varchar(256) COLLATE utf8mb4_unicode_ci NOT NULL COMMENT '昵称',
                              `avatar` varchar(256) COLLATE utf8mb4_unicode_ci DEFAULT NULL COMMENT '头像',
                              `updated_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                              `created_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                              `is_deleted` tinyint(4) NOT NULL DEFAULT '0' COMMENT '逻辑删除（0-未删除，1-已删除）',
                              PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci COMMENT='用户资料表（保存非敏感信息）';

INSERT INTO `interface` (`name`, `description`, `path`, `url`, `request_param`, `request_param_remark`, `response_param_remark`, `request_header`, `response_header`, `status`, `method`, `user_id`) VALUES

('Get User Info', 'Retrieve user information', '/user/info', 'https://api.example.com', 
'{"userId": 123}', 
'[{"name": "userId", "required": true, "type": "long", "description": "User ID"}]', 
'[{"name": "name", "type": "string", "description": "user name"}, {"name": "email", "type": "string", "description": "User email"}]', 
'{"Authorization": "Bearer <token>"}', '{"Content-Type": "application/json"}', 1, 'GET', 1),

('Create User', 'Create a new user', '/user/create', 'https://api.example.com', 
'{"name": "John", "email": "john@example.com"}', 
'[{"name": "name", "required": true, "type": "string", "description": "user name"}, {"name": "email", "required": true, "type": "string", "description": "user email"}]', 
'null', '{"Authorization": "Bearer <token>"}', '{"Content-Type": "application/json"}', 1, 'POST', 1),

('Update User', 'Update user information', '/user/update', 'https://api.example.com', 
'{"userId": 123, "name": "John Doe"}', 
'[{"name": "userId", "required": true, "type": "long", "description": "User ID"}, {"name": "name", "required": true, "type": "string", "description": "Updated user name"}]', 'null', '{"Authorization": "Bearer <token>"}', '{"Content-Type": "application/json"}', 1, 'PUT', 1),

('Delete User', 'Delete a user', '/user/delete', 'https://api.example.com',
 '{"userId": 123}', '[{"name": "userId", "required": true, "type": "long", "description": "User ID to delete"}]',
 'null', '{"Authorization": "Bearer <token>"}', '{"Content-Type": "application/json"}', 1, 'DELETE', 1),

('List Users', 'Retrieve list of users', '/user/list', 'https://api.example.com',
 'null', 'null', '[{"name": "users", "type": "array", "description": "List of user objects"}]',
 '{"Authorization": "Bearer <token>"}', '{"Content-Type": "application/json"}', 1, 'GET', 1),

('Change Password', 'Change user password', '/user/change-password',
 'https://api.example.com', '{"userId": 123, "newPassword": "newpass123"}', '[{"name": "userId", "required": true, "type": "long", "description": "User ID"}, {"name": "newPassword", "required": true, "type": "string", "description": "New password"}]', 'null',
 '{"Authorization": "Bearer <token>"}', '{"Content-Type": "application/json"}', 1, 'PUT', 1);