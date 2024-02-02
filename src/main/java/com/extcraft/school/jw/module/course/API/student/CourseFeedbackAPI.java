package com.extcraft.school.jw.module.course.API.student;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.dao.course.CourseFeedbackDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.Course;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/course/student/courseFeedback/*")
public class CourseFeedbackAPI extends HttpServlet {

    private String courseId;
    private String userId;
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String pathInfo = request.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        User user = (User) request.getSession().getAttribute("user");
        courseId = pathParts[1];
        userId = user.getId().toString();
        String feedback = "";
        try {
            feedback = CourseFeedbackDao.queryFeedback(courseId,userId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        if (feedback == ""){
            feedback = "请输入课程评价";
        }
        request.setAttribute("feedback", feedback);

        RequestDispatcher dispatcher = request.getRequestDispatcher("/course/student/courseFeedback");
        dispatcher.forward(request, response);

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String feedback = request.getParameter("feedback");

        if (!feedback.equals("请输入课程评价")){
            CourseFeedbackDao.addFeedback(userId,courseId,feedback);
        }
        response.sendRedirect("/course/student/courseList");

    }
}
