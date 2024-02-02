package com.extcraft.school.jw.module.discussion;

import com.extcraft.school.jw.dao.discussion.DiscussionDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.discussion.DiscussionContent;
import com.extcraft.school.jw.util.PathFactory;
import com.extcraft.school.jw.util.SessionKeys;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/24
 */
@WebServlet("/api/discussion/content/add")
public class DiscussionContentAddAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String discussionID = req.getParameter("discussion_id");
        String content = req.getParameter("content");
        if (discussionID == null || content == null) {
            resp.setStatus(400);
            return;
        }
        DiscussionContent discussionContent = DiscussionContent.builder()
                .discussionID(Long.parseLong(discussionID))
                .content(content)
                .userID(((User) req.getSession().getAttribute(SessionKeys.USER)).getId())
                .build();
        DiscussionDao.addDiscussionContent(discussionContent);
        resp.sendRedirect(PathFactory.buildPath("/discussion/detail/?id={0}", discussionID));
    }
}
