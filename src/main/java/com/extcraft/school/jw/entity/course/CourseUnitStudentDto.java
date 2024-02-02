package com.extcraft.school.jw.entity.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CourseUnitStudentDto {
    private Long id;
    private Long teacherId;
    private String name;
    private String description;
    private List<CourseUnitStudent> list;
}
