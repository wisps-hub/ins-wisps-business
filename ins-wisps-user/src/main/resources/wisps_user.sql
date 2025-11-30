CREATE TABLE `d1`.`org_user` (
     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT '用户ID（自增主键）',
     `nick_name` varchar(255) DEFAULT NULL COMMENT '用户昵称',
     `password_encrypt` varchar(255) DEFAULT NULL COMMENT '密码哈希',
     `state` varchar(64) DEFAULT NULL COMMENT '用户状态（ACTIVE，FROZEN）',
     `invite_code` varchar(255) DEFAULT NULL COMMENT '邀请码',
     `mobile` varchar(20) DEFAULT NULL COMMENT '手机号码',
     `email` varchar(20) DEFAULT NULL COMMENT '邮箱',
     `inviter_id` varchar(255) DEFAULT NULL COMMENT '邀请人用户ID',
     `last_login_time` timestamp DEFAULT NULL COMMENT '最后登录时间',
     `avatar_url` varchar(255) DEFAULT NULL COMMENT '用户头像URL',
     `block_chain_url` varchar(255) DEFAULT NULL COMMENT '区块链地址',
     `block_chain_platform` varchar(255) DEFAULT NULL COMMENT '区块链平台',
     `certification` tinyint(1) DEFAULT NULL COMMENT '实名认证状态（TRUE或FALSE）',
     `real_name` varchar(255) DEFAULT NULL COMMENT '真实姓名',
     `id_card_no` varchar(255) CHARACTER SET utf8mb4  DEFAULT NULL COMMENT '身份证no',
     `user_role` varchar(128) DEFAULT NULL COMMENT '用户角色',
     `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
     `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
     PRIMARY KEY (`id`)
) COMMENT='用户信息表';

CREATE TABLE `d1`.`org_permission` (
                                       `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID（自增主键）',
                                       `path` varchar(255) DEFAULT NULL COMMENT '路径',
                                       `title` varchar(255) DEFAULT NULL COMMENT '标题',
                                       `grade` tinyint(1) NOT NULL DEFAULT 0 COMMENT '层级',
                                       `perm_type` tinyint(1) DEFAULT NULL COMMENT '类型0-页面 1-按钮',
                                       `pid` bigint NOT NULL DEFAULT 0 COMMENT '父级id',
                                       `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                       `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                       PRIMARY KEY (`id`)
) COMMENT='权限表';

INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (1, '/home', '首页', 1, 0, 0);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (2, '/user', '用户管理', 1, 1, 0);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (3, '/permession', '权限管理', 1, 1, 0);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (4, '/article', '文章管理', 1, 1, 0);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (5, '/approve', '审核管理', 1, 1, 0);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (6, '/publish', '发布管理', 1, 1, 0);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (7, '/setting', '系统设置', 1, 1, 0);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (8, '/user/list', '用户列表', 2, 0, 2);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (9, '/role/list', '角色列表', 2, 0, 3);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (10, '/permession/list', '权限列表', 2, 0, 3);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (11, '/article/list', '文章列表', 2, 0, 4);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (12, '/article/list', '新建文章', 2, 0, 4);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (13, '/article/channel', '文章分类', 2, 0, 4);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (14, '/publish/unPublish', '待发布', 2, 0, 6);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (15, '/publish/published', '已发布', 2, 0, 6);
INSERT INTO d1.org_permission (id, path, title, grade, perm_type, pid) VALUES (16, '/publish/offline', '已下线', 2, 0, 6);

CREATE TABLE `d1`.`org_role` (
    `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID（自增主键）',
    `title` varchar(255) DEFAULT NULL COMMENT '标题',
    `role_type` tinyint(1) DEFAULT NULL COMMENT '角色类型0-默认 1-自定义',
    `perms` varchar(512) DEFAULT NULL COMMENT '权限标题 ,号拼接字符串',
    `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
    `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
    PRIMARY KEY (`id`)
) COMMENT='角色表';

INSERT INTO d1.org_role (id, title, role_type, perms) VALUES (1, '超级管理员', '0', '1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16');
INSERT INTO d1.org_role (id, title, role_type, perms) VALUES (2, '管理员', '1', '1,2,3,4,5,6,8,9,10,11,12,13,14,15,16');
INSERT INTO d1.org_role (id, title, role_type, perms) VALUES (3, '普通用户', '1', '1,4,6,11,12,13,14,15,16');