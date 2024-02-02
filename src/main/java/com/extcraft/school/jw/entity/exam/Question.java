package com.extcraft.school.jw.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 题库类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Question {
    /**
     * 题目ID (主键，自动递增)
     */
    private Long questionId;
    /**
     * 题目内容
     */
    private String content;
    /**
     * 题目类型
     */
    private String type;
    /**
     * 题目选项（针对选择题）
     */
    private String options;
    /**
     * 正确答案
     */
    private String answer;
    /**
     * 创建者ID
     */
    private Long createdId;

}
