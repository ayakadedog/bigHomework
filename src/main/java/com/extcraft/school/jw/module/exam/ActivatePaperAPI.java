package com.extcraft.school.jw.module.exam;

import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.exam.Paper;

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
 * 激活试卷
 */
@WebServlet("/exam/teacher/activatePaper/*")
public class ActivatePaperAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                if (pathParts.length > 1) {
                        Long paperId = Long.valueOf(pathParts[1]);
                        int i = PapersDao.activatePaper(paperId);
                        if (i == 1){
                                resp.setStatus(HttpServletResponse.SC_OK);
                                resp.sendRedirect(req.getContextPath() + "/exam/teacher/paperQuery"); // 这里设置了跳转的路径
                        }else {
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                }
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        }
}

