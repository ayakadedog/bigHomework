package com.extcraft.school.jw.module.assignment;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.entity.auth.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 老师发布作业
 * @author fan
 */
@WebServlet("/assignment/teacher/assignmentPublish/*")
public class AssignmentPublishAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        Long courseId = Long.valueOf(pathParts[1]);
        req.setAttribute("courseId", courseId);
        // 转发到/assignment/teacher/assignmentPublish页面
        RequestDispatcher dispatcher = req.getRequestDispatcher("/assignment/teacher/assignmentPublish");
        dispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = req.getParameter("title");
        String description = req.getParameter("description");
        String dueDate = req.getParameter("dueDate");
        User user = (User) req.getSession().getAttribute("user");
        Long id = user.getId();
        Assignments assignments = new Assignments();
        try {
            // 解析输入的时间字符串
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date date = inputFormat.parse(dueDate);
            // 格式化日期
            SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String formattedTime = outputFormat.format(date);

            // 从请求URL中提取课程ID
            String pathInfo = req.getPathInfo();
            String[] pathParts = pathInfo.split("/");
            if (pathParts.length > 1) {
                Long courseId = Long.valueOf(pathParts[1]);
                assignments.setCourseId(courseId);
                assignments.setUserId(id);
                assignments.setTitle(title);
                assignments.setDescription(description);
                assignments.setDueDate(formattedTime);
                int i = AssignmentsDao.publishAssignments(assignments);
                if (i == 1){
                    resp.setStatus(HttpServletResponse.SC_OK);
                    resp.sendRedirect(req.getContextPath() + "/course/teacher/courseList"); // 这里设置了跳转的路径
                }else {
                    resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
            }else {
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            }
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }

    }
}
