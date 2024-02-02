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
 * 课程单元
 */
public class CourseUnit {
    private Long id;
    private Long courseId;
    private String name;
    private String materialUrl;

    public CourseUnit(Long courseId, String name, String url) {
        this.courseId = courseId;
        this.name = name;
        this.materialUrl = url;
    }

    public CourseUnit(long courseId, String unitName) {
        this.courseId = courseId;
        this.name = unitName;
    }
}
