package com.extcraft.school.jw.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 考卷问题关联类
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaperQuestion {
    /**
     * 考卷题目关联ID (主键，自动递增)
     */
    private Long paperQuestionId;
    /**
     * 考卷ID
     */
    private Long paperId;
    /**
     * 题目ID
     */
    private Long questionId;

    private Integer status;

    private String score;
}
