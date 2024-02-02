package com.extcraft.school.jw.module.exam.view.teacher;

import com.extcraft.school.jw.dao.exam.PaperQuestionsDao;
import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.dao.exam.QuestionsDao;
import com.extcraft.school.jw.entity.exam.PaperQuestionDTO;
import com.extcraft.school.jw.entity.exam.Question;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import com.extcraft.school.jw.util.ScoreUtils;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 试卷预览
 */
@WebServlet("/exam/teacher/paperPreview")
public class PaperPreviewView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
                Long paperId = Long.valueOf(request.getAttribute("paperId").toString());
                List<PaperQuestionDTO> questionList = PapersDao.paperViewScore(paperId);
            double totalScore = ScoreUtils.calculateTotalScore(
                    questionList.stream()
                            .map(PaperQuestionDTO::getScore)
                            .collect(Collectors.toList())
            );
            context.setVariable("paperQuestionLists", questionList);
                context.setVariable("paperId", paperId);
                context.setVariable("totalScore", totalScore);

        }

}
