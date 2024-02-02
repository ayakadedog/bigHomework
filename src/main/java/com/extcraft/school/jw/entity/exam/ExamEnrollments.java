package com.extcraft.school.jw.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 学生报名表
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExamEnrollments {
    private Long userId;
    private Long paperId;
}
