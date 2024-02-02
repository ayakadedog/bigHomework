package com.extcraft.school.jw.dao.exam;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.exam.Question;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 题库类
 */
@Dao
public class QuestionsDao {

public static void init() {
    String createQuestionsTableSql = "CREATE TABLE IF NOT EXISTS questions (" +
            "question_id BIGINT AUTO_INCREMENT COMMENT '题目ID' PRIMARY KEY, " +
            "content TEXT NOT NULL COMMENT '题目内容', " +
            "type VARCHAR(32) NOT NULL COMMENT '题目类型(选择或者填空)', " +
            "options TEXT NULL COMMENT '题目选项（针对选择题）', " +
            "answer TEXT NULL COMMENT '正确答案', " +
            "created_id BIGINT NULL COMMENT '创建者ID' " +
            ") COMMENT '题库表' COLLATE = utf8_bin;";
    String insert_sql = "INSERT ignore INTO questions (question_id, content, type, options, answer, created_id) " +
            "VALUES " +
            "(1, '1 + 1 等于多少？', '填空', null, '2', 1), " +
            "(2, '水的化学式是什么？', '填空', null, 'H2O', 1), " +
            "(3, '中国的首都是哪里？', '选择', 'A) 上海; B) 北京; C) 广州; D) 武汉', 'B', 1), " +
            "(4, '太阳系中最大的行星是？', '选择', 'A) 土星; B) 木星; C) 火星; D) 地球', 'B', 1), " +
            "(5, '以下哪个是能量的单位？', '选择', 'A) 牛顿; B) 焦耳; C) 米; D) 安培', 'B', 1), " +
            "(6, '“鹅鹅鹅”是谁的诗作？', '填空', null, '骆宾王', 1), " +
            "(7, '计算机中，1字节等于多少位(bit)？', '填空', null, '8', 1), " +
            "(8, '以下哪种动物是哺乳动物？', '选择', 'A) 鲨鱼; B) 鳄鱼; C) 豚鼠; D) 鹦鹉', 'C', 1), " +
            "(9, '黄河流经的最后一个省份是？', '填空', null, '山东', 1), " +
            "(10, '三角形内角和等于多少度？', '填空', null, '180', 1), " +
            "(11, '金属中导电性最好的是？', '填空', null, '银', 1), " +
            "(12, '谁是《红楼梦》的作者？', '填空', null, '曹雪芹', 1), " +
            "(13, '中国的国花是什么？', '选择', 'A) 牡丹; B) 荷花; C) 菊花; D) 梅花', 'A', 1), " +
            "(14, '世界上最长的河是？', '选择', 'A) 尼罗河; B) 亚马逊河; C) 黄河; D) 扬子江', 'A', 1), " +
            "(15, '维生素C主要来源于哪种食物？', '选择', 'A) 牛肉; B) 鸡蛋; C) 橙子; D) 面包', 'C', 1), " +
            "(16, '计算机语言中，通常用来表示真的是？', '填空', null, '1', 1), " +
            "(17, '物质在固态到液态的转变叫做什么？', '填空', null, '熔化', 1), " +
            "(18, '第一个登月的人是谁？', '填空', null, '尼尔·阿姆斯特朗', 1), " +
            "(19, '“我参与，我快乐”是哪个运动会的口号？', '填空', null, '残奥会', 1), " +
            "(20, '世界上海拔最高的山是？', '填空', null, '珠穆朗玛峰', 1);";
    DBWrapper.execute(createQuestionsTableSql);
    DBWrapper.executeUpdate(insert_sql);

}
    /**
     * 查询所有题库信息
     */
    public static List<Question> examQuery(){

        String query_sql = "select * from questions";
        List<Question> questionList = new ArrayList<>();
        return DBWrapper.executeQuery(query_sql,resultSet -> {
                try {
                   while(resultSet.next()){
                       questionList.add(Question.builder()
                               .questionId(resultSet.getLong("question_id"))
                               .content(resultSet.getString("content"))
                               .type(resultSet.getString("type"))
                               .options(resultSet.getString("options"))
                               .answer(resultSet.getString("answer"))
                               .createdId(resultSet.getLong("created_id"))
                               .build());
                   }
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
            return questionList;
        });
    }

}
