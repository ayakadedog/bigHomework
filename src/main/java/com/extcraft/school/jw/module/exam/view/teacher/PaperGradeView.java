package com.extcraft.school.jw.module.exam.view.teacher;

import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.dao.exam.StudentAnswersDao;
import com.extcraft.school.jw.entity.exam.PaperQuestionDTO;
import com.extcraft.school.jw.entity.exam.QuestionAnswerDetailDTO;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 批改试卷
 */
@WebServlet("/exam/teacher/paperGrade")
public class PaperGradeView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
            Long paperId = Long.valueOf(request.getAttribute("paperId").toString());
            Long userId = Long.valueOf(request.getAttribute("userId").toString());
            List<QuestionAnswerDetailDTO> paperQuestion = StudentAnswersDao.getPaperQuestion(paperId, userId);
            context.setVariable("paperQuestion", paperQuestion);
            context.setVariable("userId", userId);
            context.setVariable("paperId", paperId);
        }

}
