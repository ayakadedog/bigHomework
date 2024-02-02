package com.extcraft.school.jw.module.exam.view.teacher;

import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.dao.exam.QuestionsDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.exam.Paper;
import com.extcraft.school.jw.entity.exam.Question;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师查看试卷信息
 */
@WebServlet("/exam/teacher/paperQuery")
public class PaperQueryView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
                User user = (User) request.getSession().getAttribute("user");
                Long userId = user.getId();
                List<Paper> paperList = PapersDao.getPapers(userId);
                context.setVariable("paperList", paperList);
        }

}
