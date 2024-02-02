package com.extcraft.school.jw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 配置文件注解
 * TO-EDIT-FULL-DESCRIPTION
 * 使用该注解的类必须包含静态变量INSTANCE
 *
 * @author SmallL-U
 * @version 1.0 2023/12/9
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ConfigFile {
    /**
     * 配置文件名
     * @return 配置文件名
     */
    String value();
}
