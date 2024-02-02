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
 * 老师查看学生作业情况（展示作业状态，学生姓名，批改完成则展示作业分数）
 */
@WebServlet("/assignment/teacher/assignmentSituation/*")
public class AssignmentSituationAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        //获取作业id
        Long assignmentId = Long.valueOf(pathParts[1]);
        req.setAttribute("assignmentId", assignmentId);
        // 转发到/assignment/teacher/assignmentCheck页面
        RequestDispatcher dispatcher = req.getRequestDispatcher("/assignment/teacher/assignmentSituation");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
