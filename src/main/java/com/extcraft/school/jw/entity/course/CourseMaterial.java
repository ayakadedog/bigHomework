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
 * 课程资料
 */
public class CourseMaterial {
    private Long id;
    private Long courseId;
    private Long unitId;
    private String materialType;
    private String materialUrl;

    public CourseMaterial(Long id, Long courseId, String type, String url) {
        this.id = id;
        this.courseId = courseId;
        this.materialType = type;
        this.materialUrl = url;
    }
}
