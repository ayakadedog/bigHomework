package com.extcraft.school.jw.module.assignment;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 查看作业详情
 */
@WebServlet("/assignment/teacher/assignmentDetail/*")
public class AssignmentDetailAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        //获取作业id
        Long assignmentId = Long.valueOf(pathParts[1]);
        req.setAttribute("assignmentId", assignmentId);
        // 转发到/assignment/teacher/assignmentCheck页面
        RequestDispatcher dispatcher = req.getRequestDispatcher("/assignment/teacher/assignmentDetail");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
