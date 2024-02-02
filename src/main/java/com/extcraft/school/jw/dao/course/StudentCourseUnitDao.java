package com.extcraft.school.jw.dao.course;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.course.*;
import com.extcraft.school.jw.util.DBWrapper;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Dao
public class StudentCourseUnitDao {

    public static void init() {

        String createEnrollmentsTableSql = "CREATE TABLE IF NOT EXISTS student_course_unit_progress (\n" +
                "   id         bigint auto_increment\n" +
                "                        primary key,\n" +
                "                        student_id bigint not null,\n" +
                "                course_id  bigint not null,\n" +
                "                unit_id    bigint not null,\n" +
                "                progress   bigint null,\n" +
                "                constraint id\n" +
                "        unique (id),\n" +
                "                constraint unique_combination\n" +
                "        unique (student_id, course_id, unit_id)\n" +
                ")\n" +
                "        comment '学生课程单元是否学习表';";
        DBWrapper.execute(createEnrollmentsTableSql);
    }


    /**
     * 在选课的时候把课程单元也加进表里
     * @param list
     */
    public static void addCourseUnitByStudentId(List<StudentCourseUnitDto> list) {

        String baseSql = "-- Inserting sample data into the student_course_unit_progress table\n" +
                "INSERT INTO student_course_unit_progress (student_id, course_id, unit_id, progress)\n" +
                "VALUES\n";

        for (int i = 0; i < list.size(); i++) {
            // 构建每一条记录的值部分
            String values = String.format("    (%s, %s, %s, %s)",
                    list.get(i).getStudentId(),
                    list.get(i).getCourseId(),
                    list.get(i).getUnitId(),
                    list.get(i).getProcess());

            // 将当前记录的值部分拼接到原始SQL语句中
            String sql = baseSql + values;

            // 执行SQL语句
            DBWrapper.execute(sql);
        }

    }

    /**
     * 在退课的时候顺便把课程单元也清了
     * @param StudentID
     * @param courseID
     */
    public static void deleteCourseUnitByStudentId(String StudentID,String courseID) {
        DBWrapper.executeUpdate("DELETE FROM student_course_unit_progress WHERE student_id = " + StudentID + " AND course_id = " + courseID + ";\n");

    }

    /**
     * 查询学生课程单元以及学习进展
     * @param studentID
     * @param courseID
     * @return
     */
    public static CourseUnitStudentDto queryCourseUnitStudent(Long studentID, Long courseID) {

        return DBWrapper.executeQuery("" +
                        "SELECT\n" +
                        "    courses.id AS course_id,\n" +
                        "    courses.teacher_id,\n" +
                        "    courses.name AS course_name,\n" +
                        "    courses.description AS course_description,\n" +
                        "    course_units.id AS unit_id,\n" +
                        "    course_units.name AS unit_name,\n" +
                        "    course_units.material_url AS unit_description,\n" +
                        "    student_course_unit_progress.progress,\n" +
                        "    student_course_unit_progress.id\n" +
                        "FROM\n" +
                        "    courses,\n" +
                        "    course_units,\n" +
                        "    student_course_unit_progress\n" +
                        "WHERE\n" +
                        "    courses.id = student_course_unit_progress.course_id\n" +
                        "    AND course_units.id = student_course_unit_progress.unit_id\n" +
                        "    AND student_course_unit_progress.student_id = " + studentID.toString()+"\n" +
                        "    AND student_course_unit_progress.course_id = " + courseID.toString() +";\n"
                ,
                resultSet -> {
                    CourseUnitStudentDto courseUnitStudentDto = null;

                    try {
                        while (resultSet.next()) {
                            if (courseUnitStudentDto == null) {

                                Long courseId = resultSet.getLong("course_id");
                                Long teacherId = resultSet.getLong("teacher_id");
                                String courseName = resultSet.getString("course_name");
                                String description = resultSet.getString("course_description");

                                courseUnitStudentDto = new CourseUnitStudentDto();
                                courseUnitStudentDto.setId(courseId);
                                courseUnitStudentDto.setTeacherId(teacherId);
                                courseUnitStudentDto.setName(courseName);
                                courseUnitStudentDto.setDescription(description);
                                courseUnitStudentDto.setList(new ArrayList<>());
                            }


                            Long unitId = resultSet.getLong("unit_id");
                            String unitName = resultSet.getString("unit_name");
                            String unitDescription = resultSet.getString("unit_description");
                            Long progress = resultSet.getLong("progress");
                            CourseUnitStudent courseUnitStudent = new CourseUnitStudent();
                            courseUnitStudent.setId(unitId);
                            courseUnitStudent.setName(unitName);
                            courseUnitStudent.setDescription(unitDescription);
                            courseUnitStudent.setProgress(progress);
                            courseUnitStudentDto.getList().add(courseUnitStudent);
                        }


                        if (courseUnitStudentDto == null) {

                            resultSet.beforeFirst();
                            if (resultSet.next()) {
                                Long courseId = resultSet.getLong("course_id");
                                Long teacherId = resultSet.getLong("teacher_id");
                                String courseName = resultSet.getString("course_name");
                                String description = resultSet.getString("course_description");

                                courseUnitStudentDto = new CourseUnitStudentDto();
                                courseUnitStudentDto.setId(courseId);
                                courseUnitStudentDto.setTeacherId(teacherId);
                                courseUnitStudentDto.setName(courseName);
                                courseUnitStudentDto.setDescription(description);
                                courseUnitStudentDto.setList(new ArrayList<>());

                            }
                        }
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                    return courseUnitStudentDto;
                });

    }

    /**
     * 学生学没学过状态跟新
     * @param courseId
     * @param studentId
     * @param unitId
     */
    public static void updateProgress(String courseId, String studentId, String unitId) {

        DBWrapper.executeUpdate("UPDATE student_course_unit_progress\n" +
                "SET progress = 1\n" +
                "WHERE student_id = "+ studentId +"\n" +
                "  AND course_id = " + courseId +"\n" +
                "  AND unit_id = "+ unitId +";\n");
    }

    /**
     * 获取已学课程数量
     * @param courseId
     * @param studentId
     * @return
     */
    public static Double countLearn(String courseId, String studentId) {

        String sql = "SELECT COUNT(*) AS count\n" +
                "FROM student_course_unit_progress\n" +
                "WHERE student_id = "+ studentId +"\n" +
                "  AND course_id = "+ courseId +"\n" +
                "  AND progress = 1;\n";

        return DBWrapper.executeQuery(sql,resultSet -> {
            Double count = 0D;
            try {
                while (resultSet.next()) {
                     count = resultSet.getDouble("count");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return count;
        });
    }
}
