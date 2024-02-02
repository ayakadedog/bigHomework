package com.extcraft.school.jw.module.exam.view.student;

import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.dao.exam.StudentAnswersDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.exam.Paper;
import com.extcraft.school.jw.entity.exam.Question;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生进入考试页面考试
 */
@WebServlet("/exam/student/doExam")
public class doExamView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
                Long paperId = Long.valueOf(request.getAttribute("paperId").toString());
                List<Question> questionLists = PapersDao.paperPreview(paperId);
                context.setVariable("paperQuestionLists", questionLists);
                context.setVariable("doPaperId", paperId);
        }
}
