package com.extcraft.school.jw.module.assignment.view.student;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生查看课程作业
 */
@WebServlet("/assignment/student/assignmentWatch")
public class AssignmentWatchView extends AbstractBaseView {
        @Override
        protected void handle(WebContext context, HttpServletRequest request) {
            Long courseId = Long.valueOf(request.getAttribute("courseId").toString());
            List<Assignments> assignments = AssignmentsDao.checkAssignment(courseId);
            context.setVariable("assignmentWatch",assignments);
        }

}
