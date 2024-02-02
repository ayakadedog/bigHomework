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
 * 学生课程课程单元
 */
public class CourseUnitStudent {
    private Long id;
    //单元名字
    private String name;
    private Long progress;
    private String description;
}
