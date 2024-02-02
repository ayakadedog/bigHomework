package com.extcraft.school.jw.entity.course;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StudentCourseUnitDto {

    private Long courseId;
    private Long studentId;
    private Long unitId;
    //0没学过1学过了
    private Long process;
}
