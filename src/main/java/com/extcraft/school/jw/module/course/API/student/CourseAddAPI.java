package com.extcraft.school.jw.module.course.API.student;

import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.dao.course.EnrollmentDao;
import com.extcraft.school.jw.dao.course.StudentCourseUnitDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.StudentCourseUnitDto;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/course/student/courseAdd/*")
public class CourseAddAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String courseId = pathParts[1];
        List<Long> list = CourseUnitDao.queryAllCourseUnit(courseId);
        User user = (User) request.getSession().getAttribute("user");
        Long studentId = user.getId();
        ArrayList<StudentCourseUnitDto> studentCourseUnitDtos = new ArrayList<>();
        list.stream()
                .map(courseUnitId -> new StudentCourseUnitDto(Long.valueOf(courseId), studentId, courseUnitId, 0L))
                .forEach(studentCourseUnitDtos::add);
//        for (int i = 0; i < list.size(); i++){
//            studentCourseUnitDtos.add(new StudentCourseUnitDto(Long.valueOf(courseId), studentId, list.get(i), 0L));
//        }
        //先选课插入
        EnrollmentDao.addEnrollment(studentId,courseId);

        //若选择课程无单元则不插入
        StudentCourseUnitDao.addCourseUnitByStudentId(studentCourseUnitDtos);


        response.sendRedirect("/course/student/courseAdd");


    }
}
