package com.extcraft.school.jw.entity.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * 课程评论
 */
public class CourseFeedback {
    private Long id;
    private Long studentId;
    private Long courseId;
    private String feedback;
}
