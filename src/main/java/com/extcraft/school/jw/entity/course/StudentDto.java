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
 * 用户信息
 */
public class StudentDto {

    private Long studentId;

    private String name;

    private String courseId;

    private Integer process;

    private String score;

}
