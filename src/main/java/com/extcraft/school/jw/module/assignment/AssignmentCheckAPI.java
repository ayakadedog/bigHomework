package com.extcraft.school.jw.module.assignment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 老师查看某门课程的作业
 */
@WebServlet("/assignment/teacher/assignmentCheck/*")
public class AssignmentCheckAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        Long courseId = Long.valueOf(pathParts[1]);
        req.setAttribute("courseId", courseId);
        // 转发到/assignment/teacher/assignmentCheck页面
        RequestDispatcher dispatcher = req.getRequestDispatcher("/assignment/teacher/assignmentCheck");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
