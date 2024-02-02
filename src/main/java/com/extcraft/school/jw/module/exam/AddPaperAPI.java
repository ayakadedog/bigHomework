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
 * 添加试卷
 */
@WebServlet("/exam/teacher/addPaper")
public class AddPaperAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                super.doGet(req, resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                User user = (User) req.getSession().getAttribute("user");
                Long createdId = user.getId();
                String title = req.getParameter("title");
                String startTime = req.getParameter("startTime");
                String endTime = req.getParameter("endTime");
                // 解析输入的时间字符串
                SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
                // 格式化日期
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                Date startDate = null;
                Date endDate = null;
                try {
                        startDate = inputFormat.parse(startTime);
                        endDate = inputFormat.parse(endTime);
                        String formattedStartTime = outputFormat.format(startDate);
                        String formattedEndTime = outputFormat.format(endDate);
                        Paper paper = new Paper(createdId, title, formattedStartTime, formattedEndTime);
                        int i = PapersDao.addPaper(paper);
                        if (i == 1){
                                resp.setStatus(HttpServletResponse.SC_OK);
                                resp.sendRedirect(req.getContextPath() + "/exam/teacher/paperQuery"); // 这里设置了跳转的路径
                        }else {
                                resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                        }
                } catch (ParseException e) {
                        throw new RuntimeException(e);
                }

        }
}
