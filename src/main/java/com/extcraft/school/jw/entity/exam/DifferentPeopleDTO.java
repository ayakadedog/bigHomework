package com.extcraft.school.jw.entity.exam;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 考卷信息
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DifferentPeopleDTO {
private  String name;
private Long userId;
private Integer isGrade;
private String score;
}
