package com.extcraft.school.jw.module.exam;

import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.dao.exam.StudentAnswersDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.exam.Paper;
import com.extcraft.school.jw.entity.exam.PaperQuestion;
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
 * 学生进入考试页面考试
 */
@WebServlet("/exam/student/doExam/*")
public class doExamAPI extends HttpServlet {
        @Override
        protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                req.setCharacterEncoding("UTF-8");
                // 从请求URL中提取课程ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                req.setAttribute("paperId",paperId);
                RequestDispatcher dispatcher = req.getRequestDispatcher("/exam/student/doExam");
                dispatcher.forward(req,resp);
        }

        @Override
        protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
                //获取这张试卷的题号
                req.setCharacterEncoding("UTF-8");
                User user = (User) req.getSession().getAttribute("user");
                Long userId = user.getId();
                // 从请求URL中提取试卷ID
                String pathInfo = req.getPathInfo();
                String[] pathParts = pathInfo.split("/");
                Long paperId = Long.valueOf(pathParts[1]);
                //通过试卷id查询，该张试卷的题号。
                List<Question> questions = PapersDao.paperPreview(paperId);
                List<Long> questionIds = questions.stream().map(Question::getQuestionId).collect(java.util.stream.Collectors.toList());
                int size = questionIds.size();
                for(int i=0;i<size;i++){
                        //循环获取问题id
                        String s = String.valueOf(questionIds.get(i));
                        String index = req.getParameter(s);
                        String answer = null;
                        if(index!=null){
                                //答案转换
                                try {
                                        Long option = Long.valueOf(index);
                                        if (option == 1L) {
                                                answer = "A";
                                        } else if (option == 2L) {
                                                answer = "B";
                                        } else if (option == 3L) {
                                                answer = "C";
                                        } else if (option == 4L) {
                                                answer = "D";
                                        }
                                }catch (Exception e){
                                        answer = index;
                                }
                        }
                        //将用户选择的题号存入数据库
                        //目标：获取考卷题目的关联表id，插入学生答案表
                        PaperQuestion paperQuestion = StudentAnswersDao.getId(paperId, Long.valueOf(s));
                        Long paperQuestionId = paperQuestion.getPaperQuestionId();
                        StudentAnswersDao.insertAnswer(paperQuestionId,userId,answer);
                        }
                resp.sendRedirect(req.getContextPath() + "/index"); // 这里设置了跳转的路径
                }
        }

