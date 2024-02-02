package com.extcraft.school.jw.entity.assignment;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 学生查看作业详情
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssignmentsDTO {
    /**
     * id
     */
    private Long id;

    /**
     * 课程id
     */
    private Long courseId;

    /**
     * 用户id
     */
    private Long userId;

    private Long submissionId;

    /**
     * 作业标题
     */
    private String title;

    /**
     * 作业题目描述
     */
    private String description;

    /**
     * 截至时间
     */
    private String dueDate;


    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 是否删除
     */
    private Integer isDelete;

    private String score;
    /**
     * 作业状态
     */
    private Integer status;

    private String feedback;
    private String answer;
}
