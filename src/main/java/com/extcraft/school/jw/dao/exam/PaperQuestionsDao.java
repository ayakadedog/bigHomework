package com.extcraft.school.jw.dao.exam;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.entity.exam.PaperQuestion;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 考卷题库关联类
 */
@Dao
public class PaperQuestionsDao {

public static void init() {
    String createPaperQuestionsTableSql = "CREATE TABLE IF NOT EXISTS paper_questions (" +
            "paper_question_id BIGINT AUTO_INCREMENT COMMENT '考卷题目关联ID' PRIMARY KEY, " +
            "paper_id BIGINT NOT NULL COMMENT '考卷ID', " +
            "question_id BIGINT NOT NULL COMMENT '题目ID', " +
            "score VARCHAR(32) NULL COMMENT '题目分数', " +
            "status tinyint  null comment '0-已添加' " +
            ") COMMENT '考卷题目关联表' COLLATE = utf8_bin;";
    DBWrapper.execute(createPaperQuestionsTableSql);
}

    /**
     * 添加题目
     * @param paperId
     * @param questionId
     * @return
     */
    public static int insert(long paperId, long questionId) {
        final String insertSql = "INSERT INTO paper_questions (paper_id, question_id,status) VALUES (" + paperId + ", " + questionId + ",1);";
        return DBWrapper.executeUpdate(insertSql);
    }

    /**
     * 删除添加的题目
     * @param paperId
     * @param questionId
     * @return
     */
    public static int delete(long paperId, long questionId) {
        final String deleteSql = "DELETE FROM paper_questions WHERE paper_id = " + paperId + " AND question_id = " + questionId + ";";
        return DBWrapper.executeUpdate(deleteSql);
    }
    /**
     * 查询添加状态
     */
    public static List<PaperQuestion> query(long paperId) {
    final String querySql = "SELECT * FROM paper_questions WHERE paper_id = " + paperId + ";";
    List<PaperQuestion> paperQuestion = new ArrayList<>();
    return DBWrapper.executeQuery(querySql, resultSet -> {
        try {
            while (resultSet.next()) {
                paperQuestion.add(PaperQuestion.builder()
                                .paperQuestionId(resultSet.getLong("paper_question_id"))
                               .paperId(resultSet.getLong("paper_id"))
                               .questionId(resultSet.getLong("question_id"))
                               .status(resultSet.getInt("status"))
                               .build());
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return paperQuestion;
     });
    }

/**
 * 设置题目分数
 *
 */
 public  static  int insertScore(long paperId, long questionId, String score) {
     final String updateSql = "UPDATE paper_questions SET score = '" + score + "' WHERE paper_id = " + paperId + " AND question_id = " + questionId + ";";
     return DBWrapper.executeUpdate(updateSql);
 }


    /**
     * 设置学生题目答案分数
     */

    public static int updateStudentScore(long paperQuestionId, long userId, String score) {
        final String updateSql = "UPDATE student_answers SET score = '" + score + "' "
                + "WHERE paper_question_id = " + paperQuestionId + " AND user_id = " + userId + ";";
        return DBWrapper.executeUpdate(updateSql);
    }


}
