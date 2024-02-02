package com.extcraft.school.jw.module.exam;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 老师查看某次考试不同人的试卷
 */
@WebServlet("/exam/teacher/differentPaper/*")
public class DifferentPeoplePaperAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                req.setAttribute("paperId",paperId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/exam/teacher/differentPaper");
                dispatcher.forward(req,resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                super.doPost(req, resp);
        }
}
