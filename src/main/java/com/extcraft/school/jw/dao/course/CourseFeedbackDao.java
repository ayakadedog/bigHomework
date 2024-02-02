package com.extcraft.school.jw.dao.course;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;

/**
 * 反馈类
 */
@Dao
public class CourseFeedbackDao {
//    create table course_feedback
//            (
//                    id         bigint auto_increment comment '反馈ID'
//                    primary key,
//                    student_id bigint      not null comment '学生ID',
//                    course_id  bigint      not null comment '课程ID',
//                    feedback   varchar(50) null comment '反馈内容',
//    constraint id
//    unique (id)
//)
//    comment '课程反馈表';
    public static void init() {
        String createCourseFeedbackTableSql = "CREATE TABLE IF NOT EXISTS course_feedback (" +
                "id BIGINT AUTO_INCREMENT COMMENT '反馈ID' primary key, " +
                "student_id BIGINT NOT NULL COMMENT '学生ID', " +
                "course_id BIGINT NOT NULL COMMENT '课程ID', " +
                "feedback VARCHAR(50) NULL COMMENT '反馈内容', " +
                "CONSTRAINT id UNIQUE (id) " +
                ") COMMENT '课程反馈表';";

        DBWrapper.execute(createCourseFeedbackTableSql);
    }

    public static String queryFeedback(String courseId,String studentId){
        String sql = "SELECT feedback\n" +
                "FROM course_feedback\n" +
                "WHERE student_id = " + studentId + "\n" +
                "  AND course_id = " + courseId + "\n" +
                "ORDER BY id DESC;";

        return DBWrapper.executeQuery(sql,resultSet -> {
            String feedback = "";
            try {
                if (resultSet.next()) {
                    feedback = resultSet.getString("feedback");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                return feedback;
            }
            return feedback;
        });

    }

    public static void addFeedback(String userId, String courseId, String feedback) {
        String sql = "INSERT INTO course_feedback (student_id, course_id, feedback)\n" +
                "VALUES\n" +
                "    ( " + userId + ", "+courseId+", '"+ feedback +"。');";
        DBWrapper.execute(sql);
    }
}
