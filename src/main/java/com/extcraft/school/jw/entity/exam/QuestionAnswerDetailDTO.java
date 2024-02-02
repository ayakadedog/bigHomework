package com.extcraft.school.jw.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 考卷信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class QuestionAnswerDetailDTO {

    private Long paperQuestionId;
    private Long questionId;
    private String content;
    private String name;
    private String answer;
    private String score;

}
