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
public class AutomaticGradeDTO {
     private Long paperId;;
    private Long questionId;
    private String questionContent;
    private String standardAnswer;
    private String questionOptions;
    private String questionScore;
    private String studentAnswer;
    private String studentScore;
    private Long paperQuestionId;

}
