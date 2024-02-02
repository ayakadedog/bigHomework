package com.extcraft.school.jw.dao.course;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.course.*;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 学生选课类
 */
@Dao
public class EnrollmentDao {
//    create table enrollments
//            (
//                    id         bigint not null comment '选课ID'
//                    primary key,
//                    student_id bigint not null comment '学生ID',
//                    course_id  bigint not null comment '课程ID'
//            )
//    comment '学生选课表';

//    CREATE TABLE enrollments (
//            student_id BIGINT NOT NULL COMMENT '学生ID',
//            course_id  BIGINT NOT NULL COMMENT '课程ID',
//            PRIMARY KEY (student_id, course_id)
//) COMMENT '学生选课表';

    public static void init() {

        String createEnrollmentsTableSql = "CREATE TABLE IF NOT EXISTS enrollments (\n" +
                "    student_id BIGINT NOT NULL COMMENT '学生ID',\n" +
                "    course_id  BIGINT NOT NULL COMMENT '课程ID',\n" +
                "    PRIMARY KEY (student_id, course_id)\n" +
                ") COMMENT '学生选课表';\n";

        DBWrapper.execute(createEnrollmentsTableSql);
    }

    /**
     * 查询学生所选课程
     * @param id
     * @return
     */
    public static List<EnrollmentDto> queryStudentCourse(Long id){
        String sql = "SELECT courses.*, enrollments.student_id AS studentId " +
                "FROM enrollments " +
                "JOIN courses ON enrollments.course_id = courses.id " +
                "WHERE enrollments.student_id = " + id.toString() + ";";

        return DBWrapper.executeQuery(sql,
                resultSet -> {
                    List<EnrollmentDto> list = new ArrayList<>();
                    try {
                        while (resultSet.next()) {
                            Long courseId = resultSet.getLong("id");
                            Long teacherId = resultSet.getLong("teacher_id");
                            String courseName = resultSet.getString("name");
                            String description = resultSet.getString("description");
                            Long studentId = resultSet.getLong("studentId");
                            EnrollmentDto enrollmentDto = new EnrollmentDto(courseId, studentId, teacherId, courseName, description);
                            list.add(enrollmentDto);
                        }
                        return list;
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return list;
                });

    }

    /**
     * 学生退课
     * @param studentID
     * @param courseID
     * @return
     */
    public static int deleteCourse(String studentID,String courseID) {
        StudentCourseUnitDao.deleteCourseUnitByStudentId(studentID,courseID);
        return DBWrapper.executeUpdate("DELETE FROM enrollments WHERE student_id = " + studentID + " AND course_id = " + courseID + ";\n");
    }

    /**
     * 学生选课
     * @param studentID
     * @param courseID
     */
    public static void addEnrollment(Long studentID,String courseID) {
        String sql = "INSERT INTO enrollments (student_id, course_id) VALUES (?, ?)";
        DBWrapper.executeUpdate(sql, studentID.toString(), courseID);
    }

    public static List<StudentDto> queryCourseStudent(String courseId){
        String sql = "SELECT u.id AS student_id, u.name AS student_name\n" +
                "FROM user u\n" +
                "JOIN enrollments cu ON u.id = cu.student_id\n" +
                "WHERE cu.course_id = "+ courseId +";\n";

        return DBWrapper.executeQuery(sql,resultSet -> {
            List<StudentDto> list = new ArrayList<>();
            try {
                while (resultSet.next()) {
                    Long id = resultSet.getLong("student_id");
                    String studentName = resultSet.getString("student_name");
                    StudentDto user = new StudentDto();
                    user.setName(studentName);
                    user.setStudentId(id);
                    user.setCourseId(courseId);
                    list.add(user);
                }
                return list;
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return list;

        });
    }

}
