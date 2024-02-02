package com.extcraft.school.jw.dao.exam;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.exam.Paper;
import com.extcraft.school.jw.entity.exam.PaperQuestionDTO;
import com.extcraft.school.jw.entity.exam.Question;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 考卷信息类
 */
@Dao
public class PapersDao {

public static void init() {
    String createPapersTableSql = "CREATE TABLE IF NOT EXISTS papers (" +
            "paper_id BIGINT AUTO_INCREMENT COMMENT '考卷ID' PRIMARY KEY, " +
            "title VARCHAR(32) NOT NULL COMMENT '考卷标题', " +
            "created_id BIGINT NULL COMMENT '创建者ID', " +
            "start_time DATETIME NULL COMMENT '考试开始时间', " +
            "end_time DATETIME NULL COMMENT '考试结束时间', " +
            "is_active tinyint default 0 NOT NULL COMMENT '0-没激活 1-激活（学生可以查看试卷）' " +
            ") COMMENT '考卷信息表' COLLATE = utf8_bin;";
    DBWrapper.execute(createPapersTableSql);
}

/**
 * 查看试卷信息
 */

public static List<Paper> getPapers(Long userId) {
  final String sql = "SELECT * FROM papers WHERE created_id =" + userId + ";";
  List<Paper> paperList = new ArrayList<>();
 return DBWrapper.executeQuery(sql,resultSet -> {
          try {
             while (resultSet.next()) {
                 paperList.add(Paper.builder()
                     .paperId(resultSet.getLong("paper_id"))
                     .title(resultSet.getString("title"))
                     .createdId(resultSet.getLong("created_id"))
                     .startTime(resultSet.getString("start_time"))
                     .endTime(resultSet.getString("end_time"))
                     .isActive(resultSet.getInt("is_active"))
                     .build());
             }
          } catch (SQLException e) {
              throw new RuntimeException(e);
          }
          return paperList;
  });
}

/**
 * 添加试卷信息
 */
public static int addPaper(Paper paper) {
    final String sql = "INSERT INTO `papers` (`title`, `created_id`, `start_time`, `end_time`) " +
            "VALUES('" + paper.getTitle() + "', " + paper.getCreatedId() + ", '" + paper.getStartTime() +
            "', '" + paper.getEndTime() + "');";
    return DBWrapper.executeUpdate(sql);
}

/**
 * 激活试卷
 */
public static int activatePaper(Long paperId) {
    final String sql = "UPDATE papers SET is_active = 1 WHERE paper_id = " + paperId + ";";
    return DBWrapper.executeUpdate(sql);
}

/**
 * 取消试卷激活
 */
public static int cancelActivation(Long paperId) {
    final String sql = "UPDATE papers SET is_active = 0 WHERE paper_id = " + paperId + ";";
    return DBWrapper.executeUpdate(sql);
}

/**
 * 删除试卷
 */
public static int deletePaper(Long paperId) {
    final String sql = "DELETE FROM papers WHERE paper_id = " + paperId + ";";
    return DBWrapper.executeUpdate(sql);
}

/**
 * 试卷预览
 */

public static List<Question> paperPreview(Long paperId) {
    final String sql = "SELECT Q.* " +
            "FROM papers AS P " +
            "JOIN paper_questions AS PQ ON P.paper_id = PQ.paper_id " +
            "JOIN questions AS Q ON PQ.question_id = Q.question_id " +
            "WHERE P.paper_id = " + paperId + ";";
    List<Question> questionList = new ArrayList<>();
    return DBWrapper.executeQuery(sql,resultSet -> {
        try {
            while (resultSet.next()) {
                questionList.add(Question.builder()
                       .questionId(resultSet.getLong("question_id"))
                       .content(resultSet.getString("content"))
                       .type(resultSet.getString("type"))
                       .options(resultSet.getString("options"))
                       .answer(resultSet.getString("answer"))
                       .build());
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return questionList;
    });
}

/**
 * 查看考试名字，准备报名考试（学生用）
 */

public static List<Paper> paperQuery(){
    final String sql = "SELECT * FROM papers where is_active = 1";
    List<Paper> paper = new ArrayList<>();
    return DBWrapper.executeQuery(sql,resultSet -> {
        try {
            while (resultSet.next()) {
                paper.add(Paper.builder()
                        .paperId(resultSet.getLong("paper_id"))
                        .title(resultSet.getString("title"))
                        .createdId(resultSet.getLong("created_id"))
                        .startTime(resultSet.getString("start_time"))
                        .endTime(resultSet.getString("end_time"))
                        .isActive(resultSet.getInt("is_active"))
                        .build());
            }
        }catch (Exception e){
            throw new RuntimeException();
        }
        return paper;
    });
}

    /**
     * 试卷预览（包含分数）
     */
    public static List<PaperQuestionDTO> paperViewScore(Long paperId) {
        final String sql = "SELECT PQ.score, Q.* " +
                "FROM papers AS P " +
                "JOIN paper_questions AS PQ ON P.paper_id = PQ.paper_id " +
                "JOIN questions AS Q ON PQ.question_id = Q.question_id " +
                "WHERE P.paper_id = " + paperId + ";";
        List<PaperQuestionDTO> questionList = new ArrayList<>();
        return DBWrapper.executeQuery(sql,resultSet -> {
            try {
                while (resultSet.next()) {
                    questionList.add(PaperQuestionDTO.builder()
                            .questionId(resultSet.getLong("question_id"))
                            .content(resultSet.getString("content"))
                            .type(resultSet.getString("type"))
                            .options(resultSet.getString("options"))
                            .answer(resultSet.getString("answer"))
                            .score(resultSet.getString("score"))
                            .build());
                }
            }catch (Exception e){
                throw new RuntimeException();
            }
            return questionList;
        });
    }

}

