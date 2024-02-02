package com.extcraft.school.jw.module.discussion;

import com.extcraft.school.jw.dao.course.CourseDao;
import com.extcraft.school.jw.dao.discussion.DiscussionDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.course.Course;
import com.extcraft.school.jw.entity.discussion.Discussion;
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
@WebServlet("/api/discussion/add")
public class DiscussionAddAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = req.getParameter("title");
        String content = req.getParameter("content");
        String courseID = req.getParameter("course_id");
        if (title == null || content == null || courseID == null) {
            resp.setStatus(400);
            return;
        }
        Course course = CourseDao.queryCourse(Long.parseLong(courseID));
        assert course != null;
        Discussion discussion = Discussion.builder()
                .courseID(course.getId())
                .title(title)
                .userID(((User) req.getSession().getAttribute(SessionKeys.USER)).getId())
                .build();
        DiscussionDao.addDiscussion(discussion);
        assert discussion.getId() != 0;
        DiscussionContent discussionContent = DiscussionContent.builder()
                .discussionID(discussion.getId())
                .content(content)
                .userID(((User) req.getSession().getAttribute(SessionKeys.USER)).getId())
                .build();
        DiscussionDao.addDiscussionContent(discussionContent);
        resp.sendRedirect(PathFactory.buildPath("/discussion/list/?course_id={0}",String.valueOf(course.getId())));
    }
}
