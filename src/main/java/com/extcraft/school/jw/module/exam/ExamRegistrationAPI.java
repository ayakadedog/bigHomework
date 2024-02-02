package com.extcraft.school.jw.module.exam;

import com.extcraft.school.jw.dao.exam.ExamEnrollmentsDao;
import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.exam.Paper;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 学生考试报名
 */
@WebServlet("/exam/student/examRegister/*")
public class ExamRegistrationAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length > 1) {
                Long paperId = Long.valueOf(pathParts[1]);
                User user = (User) req.getSession().getAttribute("user");
                Long userId = user.getId();
                ExamEnrollmentsDao.examRegister(userId,paperId);
                resp.sendRedirect(req.getContextPath() + "/exam/student/examRegister"); // 这里设置了跳转的路径
                }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                super.doPost(req, resp);
        }
}
