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
 * 课程表
 */
public class Course {
    private Long id;
    private Long teacherId;
    private String name;
    private String description;

    public Course(Long id, String name,String description) {
        this.id = id;
        this.name = name;
        this.description = description;
    }
}
