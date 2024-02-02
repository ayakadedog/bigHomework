package com.extcraft.school.jw.config.general;

import lombok.Data;

/**
 * 数据库配置
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/9
 */
@Data
public class DatabaseConfig {
    private String driver;
    private String url;
    private String username;
    private String password;
}
