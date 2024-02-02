package com.extcraft.school.jw.module.exam.view.teacher;

import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.dao.exam.StudentAnswersDao;
import com.extcraft.school.jw.entity.exam.DifferentPeopleDTO;
import com.extcraft.school.jw.entity.exam.PaperQuestionDTO;
import com.extcraft.school.jw.entity.exam.PersonScore;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import com.extcraft.school.jw.util.ScoreUtils;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 老师查看某次考试不同人的试卷
 */
@WebServlet("/exam/teacher/differentPaper")
public class DifferentPeoplePaperView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
            Long paperId = Long.valueOf(request.getAttribute("paperId").toString());
            List<DifferentPeopleDTO> information = StudentAnswersDao.getInformation(paperId);
            //调用获取总分的工具类方法
//            double total = ScoreUtils.calculateTotalScore(
//                    information.stream()
//                            .map(DifferentPeopleDTO::getScore)
//                            .collect(Collectors.toList())
//            );
            // 计算每个人的试卷总分
//            Map<String, Double> totalScoresByPerson = information.stream()
//                    .collect(Collectors.groupingBy(
//                            DifferentPeopleDTO::getName,
//                            Collectors.summingDouble(dto -> {
//                                String scoreStr = dto.getScore();
//                                if (scoreStr == null || scoreStr.isEmpty()) {
//                                    return 0.0; // 如果分数是空，返回0.0
//                                }
//                                try {
//                                    return Double.parseDouble(scoreStr);
//                                } catch (NumberFormatException e) {
//                                    return 0.0; // 如果分数格式不正确，返回0.0
//                                }
//                            })
//                    ));
//
//            // 创建PersonScore对象的列表
//            List<PersonScore> personScores = totalScoresByPerson.entrySet().stream()
//                    .map(entry -> new PersonScore(entry.getKey(), entry.getValue()))
//                    .collect(Collectors.toList());
            Map<Long, Map<String, Double>> totalScoresByPerson = information.stream()
                    .collect(Collectors.groupingBy(
                            DifferentPeopleDTO::getUserId,
                            Collectors.toMap(
                                    DifferentPeopleDTO::getName,
                                    dto -> {
                                        String scoreStr = dto.getScore();
                                        if (scoreStr == null || scoreStr.isEmpty()) {
                                            return 0.0;
                                        }
                                        try {
                                            return Double.parseDouble(scoreStr);
                                        } catch (NumberFormatException e) {
                                            return 0.0;
                                        }
                                    },
                                    Double::sum
                            )
                    ));

            List<PersonScore> personScores = totalScoresByPerson.entrySet().stream()
                    .flatMap(entry -> entry.getValue().entrySet().stream()
                            .map(e -> new PersonScore(e.getKey(), entry.getKey(), e.getValue())))
                    .collect(Collectors.toList());
            context.setVariable("personScores",personScores);
            context.setVariable("paperId",paperId);
        }


}
