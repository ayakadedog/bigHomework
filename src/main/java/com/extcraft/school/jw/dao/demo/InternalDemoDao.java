package com.extcraft.school.jw.dao.demo;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.util.DBWrapper;

/**
 * 模块内部的DemoDao
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@Dao
public class InternalDemoDao {

    public static void init() {
        DBWrapper.execute("CREATE TABLE IF NOT EXISTS `internal_demo`(\n" +
                "    `id` BIGINT PRIMARY KEY \n" +
                ");\n");
    }

}
