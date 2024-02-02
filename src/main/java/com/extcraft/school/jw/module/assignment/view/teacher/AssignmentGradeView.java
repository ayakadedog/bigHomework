package com.extcraft.school.jw.module.assignment.view.teacher;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.entity.assignment.SubmitWork;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师批改作业（根据作业id和用户id查询提交信息）
 */
@WebServlet("/assignment/teacher/assignmentGrade")
public class AssignmentGradeView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
            Long assignmentId = Long.valueOf(request.getAttribute("assignmentId").toString());
            Long userId = Long.valueOf(request.getAttribute("userId").toString());
            List<SubmitWork> submitWorkList = AssignmentsDao.queryByAssignmentIdAndUserId(assignmentId,userId);
            context.setVariable("aId",assignmentId);
            context.setVariable("uId",userId);
            context.setVariable("submitWorkList",submitWorkList);
        }

}
