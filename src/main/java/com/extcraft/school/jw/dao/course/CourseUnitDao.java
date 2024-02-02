package com.extcraft.school.jw.dao.course;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.course.*;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 课程单元信息类
 */
@Dao
public class CourseUnitDao {
//    create table course_units
//            (
//                    id           bigint      not null comment '单元ID'
//                    primary key,
//                    course_id    bigint      not null comment '课程ID',
//                    name         varchar(50) not null comment '单元名称',
//    material_url varchar(50) null comment '资料链接'
//            )
//    comment '课程单元信息表';




    public static void init() {

        String createCourseUnitsTableSql = "-- auto-generated definition\n" +
                "CREATE TABLE IF NOT EXISTS course_units\n" +
                "(\n" +
                "    id           bigint auto_increment comment '单元ID'\n" +
                "        primary key,\n" +
                "    course_id    bigint      not null comment '课程ID',\n" +
                "    name         varchar(50) not null comment '单元名称',\n" +
                "    material_url varchar(50) null comment '资料链接',\n" +
                "    constraint id\n" +
                "        unique (id)\n" +
                ")\n" +
                "    comment '课程单元信息表';\n" +
                "\n";


        DBWrapper.execute(createCourseUnitsTableSql);
    }


    /**
     * 查询课程单元
     * @param id
     * @return
     */
    public static CourseUnitDto queryCourseUnits(String id) {
//        return DBWrapper.executeQuery("SELECT courses.id AS course_id, teacher_id, courses.name AS course_name, description, " +
//                        "course_units.id AS unit_id, course_id AS unit_course_id, course_units.name AS unit_name, material_url " +
//                        "FROM courses JOIN course_units ON courses.id = course_units.course_id " +
//                        "WHERE courses.id =" + id + ";",
        return DBWrapper.executeQuery("SELECT courses.id AS course_id, teacher_id, courses.name AS course_name, description, " +
                        "course_units.id AS unit_id, course_id AS unit_course_id, course_units.name AS unit_name, material_url " +
                        "FROM courses LEFT JOIN course_units ON courses.id = course_units.course_id " +
                        "WHERE courses.id =" + id + ";",
                resultSet -> {
                    CourseUnitDto courseUnitDto = null;

                    try {
                        while (resultSet.next()) {
                            if (courseUnitDto == null) {

                                Long courseId = resultSet.getLong("course_id");
                                Long teacherId = resultSet.getLong("teacher_id");
                                String courseName = resultSet.getString("course_name");
                                String description = resultSet.getString("description");

                                courseUnitDto = new CourseUnitDto();
                                courseUnitDto.setId(courseId);
                                courseUnitDto.setTeacherId(teacherId);
                                courseUnitDto.setName(courseName);
                                courseUnitDto.setDescription(description);
                                courseUnitDto.setList(new ArrayList<>());
                            }


                            Long unitId = resultSet.getLong("unit_id");
                            Long unitCourseId = resultSet.getLong("unit_course_id");
                            String unitName = resultSet.getString("unit_name");
                            String materialUrl = resultSet.getString("material_url");

                            CourseUnit courseUnit = new CourseUnit();
                            courseUnit.setId(unitId);
                            courseUnit.setCourseId(unitCourseId);
                            courseUnit.setName(unitName);
                            courseUnit.setMaterialUrl(materialUrl);

                            courseUnitDto.getList().add(courseUnit);
                        }

                        if (courseUnitDto == null) {

                            resultSet.beforeFirst();
                            if (resultSet.next()) {
                                Long courseId = resultSet.getLong("course_id");
                                Long teacherId = resultSet.getLong("teacher_id");
                                String courseName = resultSet.getString("course_name");
                                String description = resultSet.getString("description");

                                courseUnitDto = new CourseUnitDto();
                                courseUnitDto.setId(courseId);
                                courseUnitDto.setTeacherId(teacherId);
                                courseUnitDto.setName(courseName);
                                courseUnitDto.setDescription(description);
                                courseUnitDto.setList(new ArrayList<>());
                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return courseUnitDto;
                });

    }

    /**
     * 删除课程单元
     * @param courseId
     * @return
     */
    public static int deleteCourseUnit(String courseId) {

        return DBWrapper.executeUpdate("DELETE FROM course_units WHERE id = " + courseId + ";\n");

    }

    /**
     * 删除所有关联课程单元
     * @param courseUnitId
     * @return
     */
    public static int deleteCourseAllUnit(String courseUnitId) {

        return DBWrapper.executeUpdate("DELETE FROM course_units WHERE course_id = " + courseUnitId + ";\n");

    }

    /**
     * 添加课程单元
     * @param courseUnit
     * @return
     */
    public static int addCourseUnit(CourseUnit courseUnit){
//        return DBWrapper.executeUpdate("        INSERT INTO course_units (course_id, name,material_url)\n" +
//                "        VALUES (" + courseUnit.getCourseId() + "," + courseUnit.getName() + "," + courseUnit.getMaterialUrl() + ");");
        return DBWrapper.executeUpdate(
                "INSERT INTO course_units (course_id, name, material_url)\n" +
                        "VALUES (?, ?, ?)",
                courseUnit.getCourseId().toString(),
                courseUnit.getName(),
                courseUnit.getMaterialUrl()
        );
    }

    /**
     * 查询单元信息
     * @param ID
     * @return
     */
    public static CourseUnit queryCourseUnit(String ID){
        String sql = "SELECT *\n" +
                "FROM course_units\n" +
                "WHERE id =" + ID + "\n";

        return DBWrapper.executeQuery(sql, resultSet -> {
            CourseUnit courseUnit = new CourseUnit();
            try {
                while (resultSet.next()) {

                    Long id = resultSet.getLong("id");
                    Long courseId  = resultSet.getLong("course_id");
                    String name = resultSet.getString("name");
                    String url = resultSet.getString("material_url");

                    courseUnit.setCourseId(id);
                    courseUnit.setCourseId(courseId);
                    courseUnit.setName(name);
                    courseUnit.setMaterialUrl(url);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return courseUnit;
        });
    }

    /**
     * 更新课程单元
     * @param courseUnit
     * @return
     */
    public static void updateCourseUnit(CourseUnit courseUnit){
        DBWrapper.execute("UPDATE course_units SET name = '" + courseUnit.getName() + "', material_url = '" + courseUnit.getMaterialUrl() + "' WHERE id = 1;");
    }


    public static List<Long> queryAllCourseUnit(String courseId){
        String sql = "SELECT cu.*\n" +
                "FROM course_units cu\n" +
                "JOIN courses c ON cu.course_id = c.id\n" +
                "WHERE c.id = " + courseId + ";\n";

        return DBWrapper.executeQuery(sql, resultSet -> {
            List<Long> unitIds = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    unitIds.add(id);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return unitIds;
        });

    }


    /**
     * 统计一个课程有几个单元
     * @param courseId
     * @return
     */
    public static Double countUnit(String courseId){
        return DBWrapper.executeQuery("SELECT COUNT(*) AS unit_count\n" +
                "FROM course_units\n" +
                "WHERE course_id = "+ courseId +";\n",
                resultSet -> {
            double count = 0D;
                    try {
                        while (resultSet.next()) {
                            count = resultSet.getDouble("unit_count");
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return count;
        });
    }

}
