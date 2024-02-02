package com.extcraft.school.jw.dao.course;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程类
 */
@Dao
public class CourseDao {
//    create table courses
//            (
//                    id          int         not null comment '课程ID'
//                    primary key,
//                    teacher_id  int         not null comment '教师ID',
//                    name        varchar(50) not null comment '课程名称',
//    description varchar(50) null comment '课程描述'
//
//            )
//    comment '课程信息表';



    public static void init() {

        String createCoursesTableSql = "        CREATE TABLE IF NOT EXISTS courses\n" +
                "                (\n" +
                "                        id          bigint auto_increment comment '课程ID'\n" +
                "        primary key,\n" +
                "        teacher_id  bigint      not null comment '教师ID',\n" +
                "                name        varchar(50) not null comment '课程名称',\n" +
                "                description varchar(50) null comment '课程描述',\n" +
                "                constraint id\n" +
                "        unique (id)\n" +
                ")\n" +
                "        comment '课程信息表';";


        DBWrapper.execute(createCoursesTableSql);
    }

    /**
     * 查询老师查询课程
     * @param Id
     * @return
     */
    public static List<Course> queryTeacherCourse(Long Id) {
        return DBWrapper.executeQuery("SELECT * FROM courses where teacher_id =" +  Id.toString(), resultSet -> {
            List<Course> rt = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long teacher  = resultSet.getLong("teacher_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Course course = new Course(id, teacher,name,description);
                    rt.add(course);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rt;
        });
    }


    /**
     * 根据id查询课程
     * @param Id
     * @return
     */
    public static Course queryCourse(Long Id) {
        return DBWrapper.executeQuery("SELECT * FROM courses where id =" +  Id.toString(), resultSet -> {
            Course course = new Course();
            try {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long teacher  = resultSet.getLong("teacher_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    course.setTeacherId(teacher);
                    course.setId(id);
                    course.setName(name);
                    course.setDescription(description);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return course;
        });
    }

    /**
     * 查询学生未选的课程
     * @param
     * @return
     */
    public static List<Course> queryAllCourse(Long studentId) {
        String sql = "SELECT c.*\n" +
                "FROM courses c\n" +
                "WHERE NOT EXISTS (\n" +
                "    SELECT 1\n" +
                "    FROM enrollments e\n" +
                "    WHERE e.student_id = "+ studentId.toString() + "\n" +
                "      AND e.course_id = c.id\n" +
                ");\n";

        return DBWrapper.executeQuery(sql, resultSet -> {
            List<Course> rt = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("id");
                    Long teacher  = resultSet.getLong("teacher_id");
                    String name = resultSet.getString("name");
                    String description = resultSet.getString("description");
                    Course course = new Course(id, teacher,name,description);
                    rt.add(course);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return rt;
        });
    }

    /**
     * 添加课程
     * @param course
     * @return
     */
    public static int addCourse(Course course){

//        return DBWrapper.executeUpdate("INSERT INTO courses (teacher_id, name, description) \n" +
//                "VALUES (" + course.getTeacherId().toString() + "," + course.getName() + "," + course.getDescription() + ");");

        return DBWrapper.executeUpdate("INSERT INTO courses (teacher_id, name, description) \n" +
                "VALUES (" + course.getTeacherId().toString() + ",'" + course.getName() + "','" + course.getDescription() + "');");

    }

    /**
     * 删除课程
     * @param courseId
     * @return
     */
    public static int deleteCourse(String courseId) {

        return DBWrapper.executeUpdate("DELETE FROM courses WHERE id = " + courseId + ";\n");
    }


    /**
     * 更新课程
     * @param course
     * @return
     */
    public static void updateCourse(Course course){
        DBWrapper.execute("UPDATE courses SET name = '" + course.getName() + "', description = '" + course.getDescription() + "' WHERE id = 1;");
    }
}
