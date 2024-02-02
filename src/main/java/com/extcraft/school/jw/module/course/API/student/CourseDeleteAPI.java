package com.extcraft.school.jw.module.course.API.student;

import com.extcraft.school.jw.dao.course.EnrollmentDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/course/student/courseDelete/*")
public class CourseDeleteAPI extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 从请求URL中提取课程ID
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length > 2) {
            String courseId = pathParts[1];
            String studentId = pathParts[2];

            Integer deletionSuccessful = EnrollmentDao.deleteCourse(studentId, courseId);
            if (deletionSuccessful==1) {
                response.setStatus(HttpServletResponse.SC_OK);
            } else {
                response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        } else {
            response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
