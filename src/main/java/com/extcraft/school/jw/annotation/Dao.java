package com.extcraft.school.jw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Dao注解
 * TO-EDIT-FULL-DESCRIPTION
 * 使用该注解的类必须包含静态方法init
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Dao { }
