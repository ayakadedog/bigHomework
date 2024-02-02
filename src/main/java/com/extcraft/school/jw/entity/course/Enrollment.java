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
 * 学生选课
 */
public class Enrollment {
    private Long id;
    private Long studentId;
    private Long courseId;
}
