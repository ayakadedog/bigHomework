package com.extcraft.school.jw.module.progress;

import com.extcraft.school.jw.dao.course.EnrollmentDao;
import com.extcraft.school.jw.entity.course.StudentDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/progress/studentList/*")
public class StudentProgressAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        String courseId = pathParts[1];
        List<StudentDto> userDtos = EnrollmentDao.queryCourseStudent(courseId);

        request.setAttribute("userDtos", userDtos);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/progress/studentList");
        dispatcher.forward(request, response);

    }
}
