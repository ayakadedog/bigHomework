package com.extcraft.school.jw.dao.course;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.course.CourseMaterial;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程材料类
 */
@Dao
public class CourseMaterialDao {
//-- auto-generated definition
//    create table course_materials
//            (
//                    id            bigint auto_increment comment '资料ID'
//                    primary key,
//                    unit_id       bigint      null comment '单元ID',
//                    material_type varchar(50) null comment '资料类型（文章、视频等）',
//    material_url  varchar(50) null comment '资料链接',
//    course_id     bigint      null,
//    constraint id
//    unique (id)
//)
//    comment '课程资料表';




    public static void init() {
        String createCourseMaterialsTableSql = "CREATE TABLE IF NOT EXISTS course_materials ("
                + "id bigint auto_increment comment '资料ID' primary key, "
                + "unit_id bigint null comment '单元ID', "
                + "material_type varchar(255) null comment '资料类型（文章、视频等）', "
                + "material_url varchar(255) null comment '资料链接', "
                + "course_id bigint null, "
                + "constraint id unique (id)"
                + ") comment '课程资料表';";

        DBWrapper.execute(createCourseMaterialsTableSql);
    }

    /**
     * 添加课程资料
     * @param courseId
     * @param material_type
     * @param url
     * @return
     */
    public static int addCourseMaterial(String courseId, String material_type, String url){
        url = url.replace("\\", "\\\\");
        return DBWrapper.executeUpdate("INSERT INTO course_materials (course_id, material_type, material_url) \n" +
                "VALUES (" + courseId + ",'" + material_type + "','" + url + "');");
    }

    /**
     * 查询课程资料
     * @param ID
     * @return
     */
    public static List<CourseMaterial> queryCourseMaterial(String ID){
        String sql = "SELECT *\n" +
                "FROM course_materials\n" +
                "WHERE course_id =" + ID + "\n" +
                "AND unit_id IS NULL;\n";

        return DBWrapper.executeQuery(sql, resultSet -> {
            List<CourseMaterial> rt = new ArrayList<>();
            try {
                while (resultSet.next()) {

                    Long id = resultSet.getLong("id");
                    Long courseId  = resultSet.getLong("course_id");
                    String type = resultSet.getString("material_type");
                    String url = resultSet.getString("material_url");

                    CourseMaterial courseMaterial = new CourseMaterial(id, courseId,type,url);
                    rt.add(courseMaterial);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rt;
        });
    }

    /**
     * 删除课程资料
     * @param courseMaterialId
     * @return
     */
    public static int deleteCourseMaterial(String courseMaterialId) {

        return DBWrapper.executeUpdate("DELETE FROM course_materials WHERE id = " + courseMaterialId + ";\n");
    }
}
