package com.extcraft.school.jw.module.exam;

import com.extcraft.school.jw.dao.exam.PaperQuestionsDao;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 批改试卷
 */
@WebServlet("/exam/teacher/paperGrade/*")
public class PaperGradeAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                Long userId = Long.valueOf(pathParts[2]);
                req.setAttribute("paperId",paperId);
                req.setAttribute("userId",userId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/exam/teacher/paperGrade");
                dispatcher.forward(req,resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String score = req.getParameter("score");
                String id = req.getParameter("paperQuestionId");
                Long paperQuestionId = Long.valueOf(id);
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long userId = Long.valueOf(pathParts[1]);
                Long paperId = Long.valueOf(pathParts[2]);
                int i = PaperQuestionsDao.updateStudentScore(paperQuestionId,userId, score);
                if (i == 1){
                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.sendRedirect(req.getContextPath() + "/exam/teacher/paperGrade/" + paperId + '/' + userId); // 这里设置了跳转的路径
                }else {
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
        }
}
