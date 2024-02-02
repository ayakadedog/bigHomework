package com.extcraft.school.jw.module.assignment;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 老师批改作业（根据作业id和用户id查询提交信息）
 */
@WebServlet("/assignment/teacher/assignmentGrade/*")
public class AssignmentGradeAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        //获取作业id
        Long assignmentId = Long.valueOf(pathParts[1]);
        Long userId = Long.valueOf(pathParts[2]);
        req.setAttribute("assignmentId", assignmentId);
        req.setAttribute("userId",userId);
        // 转发到/assignment/teacher/assignmentCheck页面
        RequestDispatcher dispatcher = req.getRequestDispatcher("/assignment/teacher/assignmentGrade");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
