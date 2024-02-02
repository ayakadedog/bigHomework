package com.extcraft.school.jw.module.assignment.view.teacher;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 查看作业详情
 */
@WebServlet("/assignment/teacher/assignmentDetail")
public class AssignmentDetailView extends AbstractBaseView {
    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        Long assignmentId = Long.valueOf(request.getAttribute("assignmentId").toString());
        //提交数量
        int num = AssignmentsDao.queryAssignmentSubmissionCount(assignmentId);
        //根据作业id查询作业信息
        //todo 对创建时间进行修改
        List<Assignments> assignmentList = AssignmentsDao.queryByAssignmentId(assignmentId);
        context.setVariable("assignmentList",assignmentList);
        context.setVariable("submitNum",num);
    }
}
