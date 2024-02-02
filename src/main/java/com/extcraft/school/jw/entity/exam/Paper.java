package com.extcraft.school.jw.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 考卷信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Paper {

    /**
     * 考卷ID (主键，自动递增)
     */
    private Long paperId;
    /**
     * 考卷标题 (非空)
     */
    private String title;
    /**
     * 创建者ID (可为空)
     */
    private Long createdId;
    /**
     * 考试开始时间 (可为空)
     */
    private String startTime;
    /**
     * 考试结束时间 (可为空)
     */
    private String endTime;
    /**
     * 是否激活 (默认为0 - 未激活，1 - 激活)
     */
    private Integer isActive;

    public Paper(Long createdId, String title, String startTime, String endTime) {
        this.createdId = createdId;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
    }
}
