package com.extcraft.school.jw.module.assignment.view.teacher;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师查看作业题目
 */
@WebServlet("/assignment/teacher/assignmentCheck")
public class AssignmentCheckView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
            Long courseId = Long.valueOf(request.getAttribute("courseId").toString());
            List<Assignments> assignments = AssignmentsDao.checkAssignment(courseId);
            context.setVariable("assignmentCheck",assignments);
        }

}
