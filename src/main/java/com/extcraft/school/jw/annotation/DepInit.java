package com.extcraft.school.jw.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 依赖初始化注解
 * TO-EDIT-FULL-DESCRIPTION
 * 该注解修饰类必须有一个静态方法init()
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface DepInit {}
