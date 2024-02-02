package com.extcraft.school.jw.module.assignment;
import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.SubmitWork;
import com.extcraft.school.jw.entity.auth.User;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


/**
 * 学生查看作业详情
 */
@WebServlet("/assignment/student/assignmentSubmit/*")
public class AssignmentSubmitAPI extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }
//TODO 添加文件提交
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String answer = req.getParameter("answer");
        User user = (User) req.getSession().getAttribute("user");
        Long id = user.getId();
        SubmitWork submitWork = new SubmitWork();
        // 从请求URL中提取课程ID
        String pathInfo = req.getPathInfo();
        String[] pathParts = pathInfo.split("/");
        if (pathParts.length > 1) {
            Long assignmentId = Long.valueOf(pathParts[1]);
            submitWork.setAssignmentId(assignmentId);
            submitWork.setUserId(id);
            submitWork.setAnswer(answer);
            int i = AssignmentsDao.submitAssignment(submitWork);
            if (i == 1){
                resp.setStatus(HttpServletResponse.SC_OK);
                resp.sendRedirect(req.getContextPath() + "/course/student/courseList"); // 这里设置了跳转的路径
            }else {
                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            }
        }else {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }
    }
}
