package com.extcraft.school.jw.module.course.API.teahcer;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.entity.course.CourseUnit;
import com.extcraft.school.jw.util.DBWrapper;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/course/teacher/courseEdit/*")
public class CourseEditAPI extends HttpServlet {

    private String courseId;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        courseId = pathParts[1];

        Course coursesInfo = CourseDao.queryCourse(Long.valueOf(courseId));
        request.setAttribute("courseInfo", coursesInfo);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/course/teacher/courseEdit");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        String name = request.getParameter("courseName");
        String description = request.getParameter("courseDescription");

        CourseDao.updateCourse(new Course(Long.valueOf(courseId),name,description));

        response.sendRedirect("/course/teacher/courseDetail/" + courseId);


    }
}
