package com.extcraft.school.jw.module.exam.view.teacher;

import com.extcraft.school.jw.dao.exam.PaperQuestionsDao;
import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.dao.exam.QuestionsDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.exam.Paper;
import com.extcraft.school.jw.entity.exam.PaperQuestion;
import com.extcraft.school.jw.entity.exam.Question;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师查看试卷信息
 */
@WebServlet("/exam/teacher/paperCombine")
public class PaperCombineView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
        Long paperId = Long.valueOf(request.getAttribute("paperId").toString());
        List<Question> questionsList = QuestionsDao.examQuery();
//        Object status1 = request.getAttribute("status");
//        if (status1!=null){
//        int status = Integer.parseInt(request.getAttribute("status").toString());
//        context.setVariable("status",status);
//        context.setVariable("status",status);
//        }
        List<PaperQuestion> paperQuestions = PaperQuestionsDao.query(paperId);
        context.setVariable("paperQuestions", paperQuestions);
        context.setVariable("questionsList", questionsList);
        context.setVariable("paperId",paperId);


        }

}
