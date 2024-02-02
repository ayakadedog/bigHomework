package com.extcraft.school.jw.module.course.API.student;

import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.dao.course.StudentCourseUnitDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.CourseUnit;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/course/student/courseUnitEdit/*")
public class CourseUnitEditAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        User user = (User) request.getSession().getAttribute("user");
        String unitId = pathParts[1];
        String courseId = pathParts[2];
        String userId = user.getId().toString();

        StudentCourseUnitDao.updateProgress(courseId,userId,unitId);
        response.sendRedirect("/course/student/courseDetail/" + courseId);


    }

}
