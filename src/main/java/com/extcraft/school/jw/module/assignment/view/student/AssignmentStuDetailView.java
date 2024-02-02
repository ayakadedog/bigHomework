package com.extcraft.school.jw.module.assignment.view.student;

import com.extcraft.school.jw.dao.assignment.AssignmentsDao;
import com.extcraft.school.jw.entity.assignment.Assignments;
import com.extcraft.school.jw.entity.assignment.AssignmentsDTO;
import com.extcraft.school.jw.entity.assignment.SubmitWork;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.checkerframework.checker.units.qual.A;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 学生查看作业详情
 */
@WebServlet("/assignment/student/assignmentStuDetail")
public class AssignmentStuDetailView extends AbstractBaseView {
    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        Long assignmentId = Long.valueOf(request.getAttribute("assignmentId").toString());
        //根据作业id查询作业信息
        //todo 对创建时间进行修改 2018-03-14 14:55:00
        //TODO 修改查询语句，如果有成绩存在则需要展示成绩，查询语句需要修改
        User user = (User) request.getSession().getAttribute("user");
        Long userId = user.getId();
        //修改查询语句，可以查看已交情况

        List<Assignments> assignmentStuList = AssignmentsDao.queryByAssignmentId(assignmentId);
        context.setVariable("assignmentId",assignmentId);
        context.setVariable("assignmentStuList",assignmentStuList);

        //添加查看提交状态的信息
        List<SubmitWork> submitWorks = AssignmentsDao.queryByAssignmentIdAndUserId(assignmentId, userId);
        if (submitWorks.size() > 0){
            context.setVariable("submitWork",submitWorks.get(0));
        }else {
            context.setVariable("submitWork",null);
        }
    }
}
