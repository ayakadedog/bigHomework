package com.extcraft.school.jw.module.discussion;

import com.extcraft.school.jw.dao.discussion.DiscussionDao;
import com.extcraft.school.jw.entity.discussion.Discussion;
import com.extcraft.school.jw.entity.discussion.DiscussionContent;
import com.extcraft.school.jw.util.PathFactory;

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
@WebServlet("/api/discussion/content/edit")
public class DiscussionContentEditAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String content = req.getParameter("content");
        String id = req.getParameter("id");
        if (content == null || id == null) {
            resp.sendError(400);
            return;
        }
        DiscussionDao.updateDiscussionContent(id,content);
        DiscussionContent discussionContent = DiscussionDao.getDiscussionContent(id);
        resp.sendRedirect(PathFactory.buildPath("/discussion/detail/?id={0}",String.valueOf(discussionContent.getDiscussionID())));
    }
}
