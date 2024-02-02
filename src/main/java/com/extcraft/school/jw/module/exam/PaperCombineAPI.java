package com.extcraft.school.jw.module.exam;

import com.extcraft.school.jw.dao.exam.PaperQuestionsDao;
import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.entity.exam.PaperQuestion;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 组合试卷
 */
@WebServlet("/exam/teacher/paperCombine/*")
public class PaperCombineAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                req.setAttribute("paperId",paperId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/exam/teacher/paperCombine");
                dispatcher.forward(req,resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long questionId = Long.valueOf(pathParts[1]);
                req.setAttribute("questionId",questionId);
                String action = req.getParameter("action");
                Long paperId = Long.valueOf(req.getParameter("paperId"));
                if ("add".equals(action)) {
                        int i = PaperQuestionsDao.insert(paperId, questionId);
                        if (i == 1){
                                resp.setStatus(HttpServletResponse.SC_OK);
                                resp.sendRedirect(req.getContextPath() + "/exam/teacher/paperCombine/"+paperId); // 这里设置了跳转的路径
                        }else {
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                } else if ("delete".equals(action)) {
                        int j = PaperQuestionsDao.delete(paperId, questionId);
                        if (j == 1){
                                resp.setStatus(HttpServletResponse.SC_OK);
                                resp.sendRedirect(req.getContextPath() + "/exam/teacher/paperCombine/"+paperId); // 这里设置了跳转的路径
                        }else {
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                }

        }
}
