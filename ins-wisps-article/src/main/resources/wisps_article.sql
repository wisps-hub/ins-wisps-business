CREATE TABLE `d1`.`article_channel` (
                                        `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID（自增主键）',
                                        `channel_name` varchar(255) DEFAULT NULL COMMENT '名称',
                                        `level_code` tinyint(1) DEFAULT NULL COMMENT '级别',
                                        `uid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
                                        `oid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '组织ID',
                                        `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        PRIMARY KEY (`id`)
) COMMENT='文章频道表';

INSERT INTO d1.article_channel (id, channel_name, level_code, uid, oid, createtime, modifytime) VALUES (1, 'java', 1, 2, 0, '2025-10-21 14:09:30', '2025-10-21 14:09:30');
INSERT INTO d1.article_channel (id, channel_name, level_code, uid, oid, createtime, modifytime) VALUES (2, '阴阳术士', 1, 2, 0, '2025-10-21 14:09:30', '2025-10-21 14:09:30');
INSERT INTO d1.article_channel (id, channel_name, level_code, uid, oid, createtime, modifytime) VALUES (3, '钢铁卡巴内瑞', 1, 2, 0, '2025-10-21 14:09:30', '2025-10-21 14:09:30');
INSERT INTO d1.article_channel (id, channel_name, level_code, uid, oid, createtime, modifytime) VALUES (4, '三个方法', 1, 2, 0, '2025-10-21 14:09:30', '2025-10-21 14:09:30');

CREATE TABLE `d1`.`article` (
                                `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID（自增主键）',
                                `title` varchar(255) DEFAULT NULL COMMENT '名称',
                                `content` text DEFAULT NULL COMMENT '内容',
                                `channel_id` bigint unsigned NOT NULL DEFAULT 0 COMMENT '频道id',
                                `cover_type` tinyint(1) DEFAULT NULL COMMENT '封面类型 0-无图 1-单图 3-三图',
                                `images` text DEFAULT NULL COMMENT '封面url,多个逗号隔开',
                                `status` tinyint(1) DEFAULT NULL COMMENT '状态 0-草稿 1-发布待审 2-审核通过 3-审核不通过',
                                `pub_time` timestamp DEFAULT NULL COMMENT '创建时间',
                                `uid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
                                `oid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '组织ID',
                                `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                PRIMARY KEY (`id`)
) COMMENT='文章表';

INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (2, '钢铁', 'gggggggg', 2, 0, null, 1, '2025-10-22 19:37:36', 2, 0, '2025-10-22 09:35:54', '2025-10-22 12:15:19');
INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (3, '阴阳风水录', 'yyyyyyy', 3, 0, null, 2, '2025-10-08 19:37:43', 2, 0, '2025-10-22 09:35:54', '2025-10-22 12:15:19');
INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (4, '笋干辣条', 'ssssss', 4, 0, null, 3, '2025-10-19 19:37:56', 2, 0, '2025-10-22 09:36:38', '2025-10-22 12:15:19');
INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (5, 'x考试', 'xxxxxx', 1, 0, null, 0, '2025-10-19 19:38:02', 2, 0, '2025-10-22 09:36:38', '2025-10-22 12:15:19');
INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (6, '89898', '99999999', 1, 3, 'https://inews.gtimg.com/news_bt/OBkbmPLeWLy4IM4oUDGvOIqSDSZ9lYOtW3qSXCYh78KXcAA/1000,https://inews.gtimg.com/news_bt/OBkbmPLeWLy4IM4oUDGvOIqSDSZ9lYOtW3qSXCYh78KXcAA/1000', 2, '2025-10-04 19:38:07', 2, 0, '2025-10-22 09:39:15', '2025-10-22 13:25:58');
INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (7, '密码学', 'mmmmmmmmm', 1, 0, null, 3, '2025-10-10 19:38:14', 2, 0, '2025-10-22 09:39:15', '2025-10-22 12:15:19');
INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (8, 'java精选', 'jjjjjjj', 2, 0, null, 2, '2025-10-12 19:38:21', 2, 0, '2025-10-22 09:39:15', '2025-10-22 12:15:19');
INSERT INTO d1.article (id, title, content, channel_id, cover_type, images, status, pub_time, uid, oid, createtime, modifytime) VALUES (10, '唐三彩', 'tttttt', 3, 0, null, 2, '2025-10-14 19:38:33', 2, 0, '2025-10-22 09:39:16', '2025-10-22 12:15:19');


CREATE TABLE `d1`.`article_read` (
                                     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID（自增主键）',
                                     `article_id` bigint unsigned NOT NULL DEFAULT 0 COMMENT '文章id',
                                     `uid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
                                     `oid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '组织ID',
                                     `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`)
) COMMENT='文章阅读表';

CREATE TABLE `d1`.`article_comment` (
                                        `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID（自增主键）',
                                        `article_id` bigint unsigned NOT NULL DEFAULT 0 COMMENT '文章id',
                                        `pid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '父评论id',
                                        `content` varchar(2048) DEFAULT NULL COMMENT '评论内容',
                                        `level` int unsigned NOT NULL DEFAULT 0 COMMENT '评论层级',
                                        `uid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
                                        `oid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '组织ID',
                                        `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                        `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                        PRIMARY KEY (`id`)
) COMMENT='评论表';

CREATE TABLE `d1`.`article_like` (
                                     `id` bigint unsigned NOT NULL AUTO_INCREMENT COMMENT 'ID（自增主键）',
                                     `business_id` bigint unsigned NOT NULL DEFAULT 0 COMMENT '业务id',
                                     `scene` tinyint(1) DEFAULT NULL COMMENT '场景 0-文章点赞 1-评论点赞',
                                     `uid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '用户ID',
                                     `oid` bigint unsigned NOT NULL DEFAULT 0 COMMENT '组织ID',
                                     `createtime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                     `modifytime` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间',
                                     PRIMARY KEY (`id`)
) COMMENT='点赞表';

