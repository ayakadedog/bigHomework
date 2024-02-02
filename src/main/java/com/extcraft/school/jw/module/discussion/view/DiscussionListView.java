package com.extcraft.school.jw.module.discussion.view;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.dao.discussion.DiscussionDao;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/24
 */
@WebServlet("/discussion/list/")
public class DiscussionListView extends AbstractBaseView {

    @Override
    protected boolean intercept(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("course_id") == null) {
            response.setStatus(400);
            return true;
        }
        return false;
    }

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        context.setVariable("course", CourseDao.queryCourse(Long.parseLong(request.getParameter("course_id"))));
        context.setVariable("discussions",DiscussionDao.getDiscussions(request.getParameter("course_id")));
    }
}
