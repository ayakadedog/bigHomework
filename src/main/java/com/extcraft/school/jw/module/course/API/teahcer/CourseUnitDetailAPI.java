package com.extcraft.school.jw.module.course.API.teahcer;

import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.entity.course.CourseUnitDto;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/course/teacher/courseUintDetail/*")
public class CourseUnitDetailAPI extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // 从请求URL中提取课程ID
        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length > 1) {
            String courseId = pathParts[1];

            // 获取课程详细信息
            CourseUnitDto courseUnitDto = getCourseUnitDto(courseId);

            if (courseUnitDto != null) {
                // 将课程信息设置为请求属性
                request.setAttribute("courseUnitDto", courseUnitDto);
                // 转发到/course/teacher/courseDetail页面
                RequestDispatcher dispatcher = request.getRequestDispatcher("/course/teacher/courseDetail");
                dispatcher.forward(request, response);
            } else {
                // 处理未找到课程的情况
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
            }
        } else {
            // 处理未提供课程ID的情况
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID not specified");
        }
    }

    // 从数据库中获取课程详细信息的方法
    private CourseUnitDto getCourseUnitDto(String courseId) {

        return CourseUnitDao.queryCourseUnits(courseId);

    }
}

