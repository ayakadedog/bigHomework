package com.extcraft.school.jw.module.assignment.view.teacher;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 删除作业
 */
@WebServlet("/assignment/teacher/assignmentDelete")
public class AssignmentDeleteView extends AbstractBaseView {
    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        // TODO
    }
}
