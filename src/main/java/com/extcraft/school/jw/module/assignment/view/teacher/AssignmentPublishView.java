package com.extcraft.school.jw.module.assignment.view.teacher;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 老师发布课程作业
 */
@WebServlet("/assignment/teacher/assignmentPublish")
public class AssignmentPublishView extends AbstractBaseView {
    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        //给前端传课程id，方便发布课程作业存储到数据库中
        String courseId = request.getAttribute("courseId").toString();
        context.setVariable("courseId",courseId);
    }
}
