package com.extcraft.school.jw.entity.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EnrollmentDto {

    private Long courseId;
    private Long studentId;
    private Long teacherId;
    private String name;
    private String description;
    private Integer process;

    public EnrollmentDto(Long courseId, Long studentId, Long teacherId, String courseName, String description) {
        this.description = description;
        this.courseId =courseId;
        this.studentId = studentId;
        this.teacherId = teacherId;
        this.name = courseName;
        this.process = 0;
    }
}
