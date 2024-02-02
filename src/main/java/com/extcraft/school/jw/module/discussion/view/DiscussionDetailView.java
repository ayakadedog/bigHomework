package com.extcraft.school.jw.module.discussion.view;

import com.extcraft.school.jw.dao.discussion.DiscussionDao;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import com.extcraft.school.jw.util.SessionKeys;
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
@WebServlet("/discussion/detail/")
public class DiscussionDetailView extends AbstractBaseView {

    @Override
    protected boolean intercept(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameter("id") == null) {
            response.setStatus(400);
            return true;
        }
        return false;
    }

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        DiscussionDao.increaseViews(request.getParameter("id"));
        context.setVariable("user",request.getSession().getAttribute(SessionKeys.USER));
        context.setVariable("discussion",DiscussionDao.getDiscussion(request.getParameter("id")));
        context.setVariable("contents",DiscussionDao.getDiscussionContents(request.getParameter("id")));
    }
}
