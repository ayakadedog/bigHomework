package com.extcraft.school.jw.module.course.API.teahcer;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.dao.course.CourseUnitDao;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/course/teacher/courseUnitDelete/*")
public class CourseUnitDeleteAPI extends HttpServlet {

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // 从请求URL中提取课程ID
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length > 1) {
            String courseUnitId = pathParts[1];

            Integer deletionSuccessful = CourseUnitDao.deleteCourseUnit(courseUnitId);

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
