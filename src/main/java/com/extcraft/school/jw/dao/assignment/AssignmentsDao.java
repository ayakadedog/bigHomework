package com.extcraft.school.jw.dao.assignment;
import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.entity.assignment.AssignmentsDTO;
import com.extcraft.school.jw.entity.assignment.SubmitWork;
import com.extcraft.school.jw.entity.assignment.SubmitWorkDTO;
import com.extcraft.school.jw.util.DBWrapper;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;



/**
 * 作业类
 */
@Dao
public class AssignmentsDao {
    public static void init() {
//        create table assignments
//                (
//                        id          bigint auto_increment comment '作业ID'
//        primary key,
//        course_id   bigint                             not null comment '课程ID',
//                user_id     bigint                             not null comment '用户ID',
//                description text                               not null comment '描述',
//                due_date    date                               not null comment '截止日期',
//                create_time datetime default CURRENT_TIMESTAMP not null comment '创建时间',
//                update_time datetime default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '更新时间',
//                is_delete   tinyint  default 0                 not null comment '是否删除'
//)

        //创建作业表语句
        String createAssignmentsTableSql = "CREATE TABLE IF NOT EXISTS assignments (" +
                "id BIGINT AUTO_INCREMENT COMMENT '作业ID' primary key, " +
                "course_id BIGINT NOT NULL COMMENT '课程ID', " +
                "user_id BIGINT NOT NULL COMMENT '用户ID', " +
                "submission_id bigint null comment '提交ID'," +
                "title varchar(128)  not null comment '作业标题'," +
                "description TEXT NOT NULL COMMENT '作业描述', " +
                "due_date DATETIME NOT NULL COMMENT '截止日期', " +
                "create_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL COMMENT '创建时间', " +
                "update_time DATETIME DEFAULT CURRENT_TIMESTAMP NOT NULL ON UPDATE CURRENT_TIMESTAMP COMMENT '更新时间', " +
                "is_delete TINYINT DEFAULT 0 NOT NULL COMMENT '是否删除' " +
                ") COMMENT '作业表' COLLATE = utf8_bin;";
        DBWrapper.execute(createAssignmentsTableSql);
        //初始化数据
        String INSERT_SQL = "INSERT ignore INTO assignments (id, course_id, user_id, submission_id, title, description, due_date, create_time, update_time, is_delete)" +
                " VALUES (1,1,1,null,'小学数学题','1+1等于多少','2023-12-30',default,default,0)";
        DBWrapper.executeUpdate(INSERT_SQL);
    }


    /**
     * 发布作业
     */
    public static int publishAssignments(Assignments assignment) {
        final String publishAssignmentsSql = "insert into assignments (course_id,user_id,title,description,due_date) \n" +
                "values(" + assignment.getCourseId() + "," + assignment.getUserId() + ",'" + assignment.getTitle() + "','" + assignment.getDescription() + "','" + assignment.getDueDate() + "');";
        return DBWrapper.executeUpdate(publishAssignmentsSql);
    }

    /**
     * 老师查看某门课程的作业（根据课程id查询作业）
     */
    public static List<Assignments> checkAssignment(Long courseId) {
        final String sql = "SELECT * FROM `assignments` WHERE `course_id`= " + courseId + ";";
        List<Assignments> assignmentsList = new ArrayList<>();
        return DBWrapper.executeQuery(sql, resultSet -> {
            try {
                while (resultSet.next()) {
                    assignmentsList.add(Assignments.builder()
                            .id(resultSet.getLong("id"))
                            .courseId(resultSet.getLong("course_id"))
                            .userId(resultSet.getLong("user_id"))
                            .submissionId(resultSet.getLong("submission_id"))
                            .title(resultSet.getString("title"))
                            .description(resultSet.getString("description"))
                            .dueDate(resultSet.getString("due_date"))
                            .createTime(resultSet.getDate("create_time"))
                            .updateTime(resultSet.getDate("update_time"))
                            .build());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return assignmentsList;
        });
    }

    /**
     * 查看改作业提交数量
     */
    public static int queryAssignmentSubmissionCount(Long assignmentId) {
        final String sql = "SELECT COUNT(*) FROM `submit_work` WHERE `assignment_id`= " + assignmentId + ";";
        return DBWrapper.executeQuery(sql, resultSet -> {
            try {
                // 移动到查询结果的第一行
                if (resultSet.next()) {
                    // 获取第一列的值
                    return resultSet.getInt(1);
                } else {
                    // 没有结果
                    return 0; // 或者你可以抛出一个异常或者返回其他默认值
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * 根据作业id查询作业
     */
    public static List<Assignments> queryByAssignmentId(Long assignmentId) {
        final String sql = "SELECT * FROM `assignments` WHERE `id`= " + assignmentId + ";";
        List<Assignments> assignmentsList = new ArrayList<>();
        return DBWrapper.executeQuery(sql, resultSet -> {
            try {
                while (resultSet.next()) {
                    assignmentsList.add(Assignments.builder()
                            .id(resultSet.getLong("id"))
                            .courseId(resultSet.getLong("course_id"))
                            .userId(resultSet.getLong("user_id"))
                            .submissionId(resultSet.getLong("submission_id"))
                            .title(resultSet.getString("title"))
                            .description(resultSet.getString("description"))
                            .dueDate(resultSet.getString("due_date"))
                            .createTime(resultSet.getDate("create_time"))
                            .updateTime(resultSet.getDate("update_time"))
                            .build());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return assignmentsList;
        });
    }

    /**
     * 根据作业id查询提交的作业信息（作业提交表，用户信息表的联查）
     */
    public static List<SubmitWorkDTO> queryByAssignmentIdAndSubmitId(Long assignmentId) {
        final String sql = "SELECT submit_work.*, user.name AS user_name " + "FROM submit_work " +
                "JOIN user ON submit_work.user_id = user.id " +
                "WHERE submit_work.assignment_id = " + assignmentId + ";";
        List<SubmitWorkDTO> submitWorkDTOList = new ArrayList<>();
        return DBWrapper.executeQuery(sql, resultSet -> {
            try {
                while (resultSet.next()) {
                    submitWorkDTOList.add(SubmitWorkDTO.builder()
                            .id(resultSet.getLong("id"))
                            .assignmentId(resultSet.getLong("assignment_id"))
                            .userId(resultSet.getLong("user_id"))
                            .score(resultSet.getString("score"))
                            .status(resultSet.getInt("status"))
                            .feedback(resultSet.getString("feedback"))
                            .name(resultSet.getString("user_name"))
                            .build());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return submitWorkDTOList;
        });
    }

    /**
     * 提交作业
     */
    public static int submitAssignment(SubmitWork submitWork) {
        final String sql = "INSERT INTO `submit_work` (`assignment_id`, `user_id`,`answer`) " +
                "VALUES(" + submitWork.getAssignmentId() + "," + submitWork.getUserId() + ",'" + submitWork.getAnswer() + "');";
        return DBWrapper.executeUpdate(sql);
    }


    /**
     * 根据作业id和用户id查询提交作业信息
     */

    public static List<SubmitWork> queryByAssignmentIdAndUserId(Long assignmentId, Long userId) {
        final String sql = "SELECT  * FROM submit_work WHERE assignment_id = " + assignmentId + " AND user_id = " + userId + ";";
        List<SubmitWork> submitWorkDTOList = new ArrayList<>();
        return DBWrapper.executeQuery(sql, resultSet -> {
            try {
                while (resultSet.next()) {
                    submitWorkDTOList.add(SubmitWork.builder()
                            .id(resultSet.getLong("id"))
                            .assignmentId(resultSet.getLong("assignment_id"))
                            .userId(resultSet.getLong("user_id"))
                            .score(resultSet.getString("score"))
                            .status(resultSet.getInt("status"))
                            .feedback(resultSet.getString("feedback"))
                            .answer(resultSet.getString("answer"))
                            .build());
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return submitWorkDTOList;
        });
    }


    /**
     * 给作业打分和反馈
     */
    public static int assignmentFeedback(SubmitWork submitWork) {
        final String sql = "UPDATE `submit_work` SET `score` = '" + submitWork.getScore() + "', `status` = " + submitWork.getStatus() + ", `feedback` = '" + submitWork.getFeedback() + "' WHERE `assignment_id` = " + submitWork.getAssignmentId() +" AND user_id = " + submitWork.getUserId() + ";";
        return DBWrapper.executeUpdate(sql);
    }


    /**
     * 学生根据作业id和用户id做作业表和提交作业表的联查
     */
@Deprecated
    public static AssignmentsDTO queryDetailByAssignmentAndUserId(Long assignmentId, Long userId){
        final String sql = "SELECT a.*, sw.score, sw.status, sw.feedback, sw.answer " +
                "FROM assignments a " +
                "JOIN submit_work sw ON a.id = sw.assignment_id " +
                "WHERE a.id = " + assignmentId + " AND sw.user_id = " + userId + ";";
        return DBWrapper.executeQuery(sql, resultSet -> {
            try {
                if (resultSet.next()){
                    return AssignmentsDTO.builder()
                           .title(resultSet.getString("title"))
                           .description(resultSet.getString("description"))
                           .dueDate(resultSet.getString("due_date"))
                           .createTime(resultSet.getDate("create_time"))
                           .score(resultSet.getString("score"))
                           .status(resultSet.getInt("status"))
                           .feedback(resultSet.getString("feedback"))
                           .answer(resultSet.getString("answer"))
                           .build();
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return null;
        });
}
}

