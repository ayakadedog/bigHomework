package com.extcraft.school.jw.dao.exam;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.exam.*;
import com.extcraft.school.jw.util.DBWrapper;
import org.checkerframework.checker.units.qual.A;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生答卷类
 */
@Dao
public class StudentAnswersDao {



    public static void init() {
    String createStudentAnswersTableSql = "CREATE TABLE IF NOT EXISTS student_answers (" +
            "student_answer_id BIGINT AUTO_INCREMENT COMMENT '学生答卷ID' PRIMARY KEY, " +
            "paper_question_id BIGINT NOT NULL COMMENT '考卷题目ID', " +
            "user_id BIGINT NOT NULL COMMENT '学生ID', " +
            "answer TEXT NULL COMMENT '学生答案', " +
            "score VARCHAR(32) NULL DEFAULT 0 COMMENT '得分', " +
            "is_graded tinyint default 0 NOT NULL COMMENT '0-没评分 1-已评分' " +
            ") COMMENT '学生答卷表' COLLATE = utf8_bin;";

    DBWrapper.execute(createStudentAnswersTableSql);
}

    /**
     * 查看我报名的考试
     */

    public static List<Paper> myExamQuery(Long userId) {
        final String sql = "SELECT p.* FROM papers p JOIN exam_enrollments e ON p.paper_id = e.paper_id WHERE e.user_id = " + userId + ";";
        List<Paper> papers = new ArrayList<>() ;
        return DBWrapper.executeQuery(sql,resultSet -> {
            try {
                while (resultSet.next()) {
                    papers.add(Paper.builder()
                                    .paperId(resultSet.getLong("paper_id"))
                                   .title(resultSet.getString("title"))
                                   .startTime(resultSet.getString("start_time"))
                                   .endTime(resultSet.getString("end_time"))
                                   .isActive(resultSet.getInt("is_active"))
                                   .build());
                }
            }catch (Exception e){
                throw new RuntimeException();
            }
            return papers;
        });
    }

    /**
     * 获取题目试卷表的id
     */
     public static PaperQuestion getId(Long paperId, Long questionId) {
         final String sql = "SELECT paper_question_id FROM paper_questions WHERE paper_id = " + paperId + " AND question_id = " + questionId + ";";
         PaperQuestion paperQuestion = new PaperQuestion();
         return DBWrapper.executeQuery(sql,resultSet -> {
             try {
                 if (resultSet.next()){
                     paperQuestion.setPaperQuestionId(resultSet.getLong("paper_question_id"));
                 }
             } catch (SQLException e) {
                 throw new RuntimeException(e);
             }
             return paperQuestion;
         });
     }

    /**
     * 插入学生答案表
     */

    public static int insertAnswer(Long paperQuestionId, Long userId, String answer){
        final String sql = "INSERT INTO student_answers (paper_question_id, user_id, answer) VALUES (" + paperQuestionId + ", " + userId + ", '" + answer + "');";
        return DBWrapper.executeUpdate(sql);
    }

    /**
     * 人工阅卷
     */
    public static List<QuestionAnswerDetailDTO> getPaperQuestion(Long paperId,Long userId) {
      final String sql = "SELECT q.question_id, pq.paper_question_id, q.content AS question_content, " +
                "u.name AS student_name, sa.answer AS student_answer, sa.score " +
                "FROM papers p " +
                "JOIN paper_questions pq ON p.paper_id = pq.paper_id " +
                "JOIN questions q ON pq.question_id = q.question_id " +
                "JOIN student_answers sa ON pq.paper_question_id = sa.paper_question_id " +
                "JOIN user u ON sa.user_id = u.id " +
                "WHERE p.paper_id = " + paperId + " AND sa.user_id = " + userId + ";";
        List<QuestionAnswerDetailDTO> questionList = new ArrayList<>();
        return DBWrapper.executeQuery(sql,resultSet -> {
            try {
                while (resultSet.next()) {
                    questionList.add(QuestionAnswerDetailDTO.builder()
                                    .paperQuestionId(resultSet.getLong("paper_question_id"))
                                   .questionId(resultSet.getLong("question_id"))
                                   .content(resultSet.getString("question_content"))
                                   .name(resultSet.getString("student_name"))
                                   .answer(resultSet.getString("student_answer"))
                                   .score(resultSet.getString("score"))
                            .build());
                }
            }catch (Exception e){
                throw new RuntimeException();
            }
            return questionList;
        });
    }

    /**
     * 老师查看某次考试不同人的试卷
     */

    public static List<DifferentPeopleDTO> getInformation(Long paperId){
        final String sql = "SELECT sa.user_id, u.name, sa.paper_question_id, sa.is_graded, sa.score " +
                "FROM student_answers sa " +
                "JOIN user u ON sa.user_id = u.id " +
                "JOIN paper_questions pq ON sa.paper_question_id = pq.paper_question_id " +
                "JOIN papers p ON pq.paper_id = p.paper_id " +
                "WHERE p.paper_id = " + paperId + " " +
                "GROUP BY sa.user_id, u.name, sa.paper_question_id, sa.is_graded, sa.score;";
        List<DifferentPeopleDTO> personInformation = new ArrayList<>() ;
        return DBWrapper.executeQuery(sql,resultSet -> {
            try {
                while (resultSet.next()) {
                    personInformation.add(DifferentPeopleDTO.builder()
                                    .name(resultSet.getString("name"))
                                   .userId(resultSet.getLong("user_id"))
                                   .isGrade(resultSet.getInt("is_graded"))
                                   .score(resultSet.getString("score"))
                                   .build());
                }
            }catch (Exception e){
                throw new RuntimeException();
            }
            return personInformation;
        });
    }

    /**
     * 自动评分
     */
    public static List<AutomaticGradeDTO> getGrade(Long paperId,Long userId){
        final String sql = "SELECT " +
                "pq.paper_id, " +
                "pq.question_id, " +
                "q.content AS question_content, " +
                "q.answer AS standard_answer, " +
                "q.options AS question_options, " +
                "pq.score AS question_score, " +
                "sa.answer AS student_answer, " +
                "sa.score AS student_score ," +
                "sa.paper_question_id AS paper_question_id "+
                "FROM paper_questions pq " +
                "INNER JOIN questions q ON pq.question_id = q.question_id " +
                "INNER JOIN papers p ON pq.paper_id = p.paper_id " +
                "INNER JOIN student_answers sa ON pq.paper_question_id = sa.paper_question_id " +
                "INNER JOIN exam_enrollments ee ON pq.paper_id = ee.paper_id AND sa.user_id = ee.user_id " +
                "WHERE p.paper_id = " + paperId + " " +
                "AND ee.user_id = " + userId + " " +
                "AND q.type = '选择'";
        List<AutomaticGradeDTO> arrayList = new ArrayList<>() ;
        return DBWrapper.executeQuery(sql,resultSet -> {
            try {
                while (resultSet.next()) {
                    arrayList.add(AutomaticGradeDTO.builder()
                                    .paperId(resultSet.getLong("paper_id"))
                                    .questionId(resultSet.getLong("question_id"))
                                  .questionContent(resultSet.getString("question_content"))
                                  .standardAnswer(resultSet.getString("standard_answer"))
                                  .questionOptions(resultSet.getString("question_options"))
                                    .questionScore(resultSet.getString("question_score"))
                                 .studentAnswer(resultSet.getString("student_answer"))
                                  .studentScore(resultSet.getString("student_score"))
                                    .paperQuestionId(resultSet.getLong("paper_question_id"))
                                  .build());
                }
            }catch (Exception e){
                throw new RuntimeException();
            }
            return arrayList;
        });
    }

    /**
     * 自动插入分数
     */
    public static int setScore(long paperQuestionId, long userId, String score) {
        final String updateSql = "UPDATE student_answers SET score = '" + score + "' "
                + "WHERE paper_question_id = " + paperQuestionId + " AND user_id = " + userId + ";";
        return DBWrapper.executeUpdate(updateSql);
    }

}
