package com.extcraft.school.jw.dao;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.util.DBWrapper;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/18
 */
@Dao
public class ShareDemoDao {

    public static void init() {
        DBWrapper.execute("CREATE TABLE IF NOT EXISTS `share_demo`(\n" +
                "    `id` BIGINT PRIMARY KEY \n" +
                ");\n");
    }

}
