package com.extcraft.school.jw.entity.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * 提交作业
 */
public class SubmitWorkDTO {
    private Long id;

    private Long assignmentId;

    private Long userId;
    private String score;
    /**
     * 作业状态
     */
    private Integer status;

    private String feedback;
    private Date submissionDate;

    private String answer;
    private String name;
}
