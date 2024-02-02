package com.extcraft.school.jw.module.course.API.student;

import com.extcraft.school.jw.dao.course.CourseMaterialDao;
import com.extcraft.school.jw.dao.course.CourseUnitDao;
import com.extcraft.school.jw.dao.course.StudentCourseUnitDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.CourseMaterial;
import com.extcraft.school.jw.entity.course.CourseUnitDto;
import com.extcraft.school.jw.entity.course.CourseUnitStudentDto;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;


@WebServlet("/course/student/courseDetail/*")
public class CourseDetailAPI extends AbstractBaseView {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");

        if (pathParts.length > 1) {
            String courseId = pathParts[1];

            // 获取课程详细信息
            CourseUnitDto courseUnitDto = CourseUnitDao.queryCourseUnits(courseId);
            User user = (User) request.getSession().getAttribute("user");
            CourseUnitStudentDto courseUnitStudentDto = StudentCourseUnitDao.queryCourseUnitStudent(user.getId(), Long.valueOf(courseId));
            List<CourseMaterial> courseMaterialList = CourseMaterialDao.queryCourseMaterial(courseId);

            if (courseUnitDto != null) {
                // 将课程信息设置为请求属性
                request.setAttribute("courseUnitStudentDto", courseUnitStudentDto);
                if (courseMaterialList != null){
                    request.setAttribute("courseMaterialList", courseMaterialList);
                }
                RequestDispatcher dispatcher = request.getRequestDispatcher("/course/student/courseDetail");
                try {
                    dispatcher.forward(request, response);
                } catch (ServletException e) {
                    throw new RuntimeException(e);
                }
            } else {
                // 处理未找到课程的情况
                response.sendError(HttpServletResponse.SC_NOT_FOUND, "Course not found");
            }
        } else {
            // 处理未提供课程ID的情况
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Course ID not specified");
        }
    }

}
