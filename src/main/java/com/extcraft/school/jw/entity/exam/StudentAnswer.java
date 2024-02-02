package com.extcraft.school.jw.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生答案类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentAnswer {

    /**
     * 学生答卷ID (主键，自动递增)
     */
    private Long studentAnswerId;
    /**
     * 考卷题目ID
     */
    private Long paperQuestionId;
    /**
     * 学生ID
     */
    private Long userId;
    /**
     * 学生答案
     */
    private String answer;
    /**
     * 得分
     */
    private String score;
    /**
     * 是否已评分 (默认为0 - 未评分，1 - 已评分)
     */
    private Integer isGraded;

}
