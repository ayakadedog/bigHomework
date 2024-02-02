package com.extcraft.school.jw.dao.exam;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.util.DBWrapper;

/**
 * 考试报名表类
 */
@Dao
public class ExamEnrollmentsDao {
    public static void init() {
        String createExamEnrollmentsTableSql = "CREATE TABLE IF NOT EXISTS exam_enrollments (" +
                "user_id BIGINT NOT NULL COMMENT '学生ID', " +
                "paper_id BIGINT NOT NULL COMMENT '考卷ID', " +
                "PRIMARY KEY (user_id, paper_id)" +
                ") COMMENT '考试报名表';";
        DBWrapper.execute(createExamEnrollmentsTableSql);
    }


    public static int examRegister(Long userId,Long paperId){
        final String sql = "INSERT INTO exam_enrollments (user_id, paper_id) VALUES ("+ userId + "," + paperId + ");";
        return DBWrapper.executeUpdate(sql);
    }
}
