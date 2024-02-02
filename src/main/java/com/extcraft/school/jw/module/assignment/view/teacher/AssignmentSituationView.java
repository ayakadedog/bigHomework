package com.extcraft.school.jw.module.assignment.view.teacher;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.entity.assignment.SubmitWorkDTO;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * 老师查看学生作业情况（展示作业状态，学生姓名，批改完成则展示作业分数）
 */
@WebServlet("/assignment/teacher/assignmentSituation")
public class AssignmentSituationView extends AbstractBaseView {
    @Override
        protected void handle(WebContext context, HttpServletRequest request) {
            //根据作业id查询提交的作业信息（作业提交表，用户信息表，作业信息表的联查）
            Long assignmentId = Long.valueOf(request.getAttribute("assignmentId").toString());
            List<SubmitWorkDTO> list = AssignmentsDao.queryByAssignmentIdAndSubmitId(assignmentId);
            context.setVariable("assignmentSituation",list);
        }

}
