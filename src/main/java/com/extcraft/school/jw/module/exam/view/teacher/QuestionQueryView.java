package com.extcraft.school.jw.module.exam.view.teacher;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.dao.exam.QuestionsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.entity.exam.Question;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师查看题库
 */
@WebServlet("/exam/teacher/examQuery")
public class QuestionQueryView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
                List<Question> questionList = QuestionsDao.examQuery();
                context.setVariable("questionList", questionList);
        }

}
