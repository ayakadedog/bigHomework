package com.extcraft.school.jw.module.exam.view.student;

import com.extcraft.school.jw.dao.exam.PapersDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.exam.Paper;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生考试报名
 */
@WebServlet("/exam/student/examRegister")
public class ExamRegistrationView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
                List<Paper> papers = PapersDao.paperQuery();
                if (papers!= null) {
                        context.setVariable("papers", papers);
                }
        }
}
