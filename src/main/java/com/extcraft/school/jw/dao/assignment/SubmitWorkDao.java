package com.extcraft.school.jw.dao.assignment;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.util.DBWrapper;

/**
 * 提交作业类
 */
@Dao
public class SubmitWorkDao {
//    create table submit_work
//            (
//                    id              bigint auto_increment comment '提交ID'
//                    primary key,
//                    assignment_id   bigint       not null comment '作业ID',
//                    user_id         bigint       not null comment '学生ID',
//                    submission_date datetime     not null comment '提交日期',
//                    answer          varchar(255) null comment '文件'
//            )
//    comment '作业提交表' collate = utf8_bin;
public static void init() {
    String createSubmitWorkTableSql = "CREATE TABLE IF NOT EXISTS submit_work (" +
            "id BIGINT AUTO_INCREMENT COMMENT '提交ID' primary key, " +
            "assignment_id BIGINT NOT NULL COMMENT '作业ID', " +
            "user_id BIGINT NOT NULL COMMENT '学生ID', " +
            "status tinyint default 0 not null comment '0-待批阅 1-已批阅', " +
            "score varchar(128) null comment '分数', " +
            "feedback text  null comment '反馈', " +
            "submission_date DATETIME default CURRENT_TIMESTAMP NOT NULL COMMENT '提交日期', " +
            "answer VARCHAR(255) NULL COMMENT '文件' " +
            ") COMMENT '作业提交表' COLLATE = utf8_bin;";
        DBWrapper.execute(createSubmitWorkTableSql);
}
}
