package com.extcraft.school.jw.module.exam;

import com.extcraft.school.jw.dao.exam.PaperQuestionsDao;
import com.extcraft.school.jw.dao.exam.StudentAnswersDao;
import com.extcraft.school.jw.entity.exam.AutomaticGradeDTO;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 批改试卷
 */
@WebServlet("/exam/teacher/automaticGrade/*")
public class AutomaticGradeAPI extends HttpServlet {

        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                Long userId = Long.valueOf(pathParts[2]);
                List<AutomaticGradeDTO> grades = StudentAnswersDao.getGrade(paperId, userId);

                for (AutomaticGradeDTO grade : grades) {
                        if (grade.getStandardAnswer().equals(grade.getStudentAnswer())) {
                                // 如果标准答案和学生答案相等，更新分数
                                int result = StudentAnswersDao.setScore(grade.getPaperQuestionId(), userId, grade.getQuestionScore());
                                if (result == 1){
                                        resp.setStatus(HttpServletResponse.SC_OK);
                                }else {
                                        resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
                                }
                        }
                }
                resp.sendRedirect(req.getContextPath() + "/exam/teacher/differentPaper/" + paperId ); // 这里设置了跳转的路径

        }
}
