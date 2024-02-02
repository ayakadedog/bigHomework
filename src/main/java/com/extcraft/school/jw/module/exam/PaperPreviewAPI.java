package com.extcraft.school.jw.module.exam;

import com.extcraft.school.jw.dao.exam.PaperQuestionsDao;
import com.extcraft.school.jw.dao.exam.QuestionsDao;
import com.extcraft.school.jw.entity.exam.Question;
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
 * 试卷预览
 */
@WebServlet("/exam/teacher/paperPreview/*")
public class PaperPreviewAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                req.setAttribute("paperId",paperId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/exam/teacher/paperPreview");
                dispatcher.forward(req,resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String score = req.getParameter("score");
                String id = req.getParameter("questionId");
                Long questionId = Long.valueOf(id);
                System.out.println(questionId);
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                int i = PaperQuestionsDao.insertScore(paperId, questionId, score);
                if (i == 1){
                        resp.setStatus(HttpServletResponse.SC_OK);
                        resp.sendRedirect(req.getContextPath() + "/exam/teacher/paperPreview/" + paperId); // 这里设置了跳转的路径
                }else {
                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                }
        }
}
