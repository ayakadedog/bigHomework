package com.extcraft.school.jw.config;

import com.extcraft.school.jw.annotation.ConfigFile;
import com.extcraft.school.jw.config.general.DatabaseConfig;
import lombok.Data;

/**
 * 通用配置
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/9
 */
@ConfigFile("general")
@Data
public class GeneralConfig {
    public static GeneralConfig INSTANCE;

    private DatabaseConfig databaseConfig;
}
