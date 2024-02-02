package com.extcraft.school.jw.entity.auth;

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
public class User {

    private Long id;

    private String name;

    private String email;

    private String userName;

    private String userPassword;

    private String role;
    /**
     * 课程名称
     */
    private String className;
    /**
     * 科目
     */
    private String subject;

}
