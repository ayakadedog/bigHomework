package com.extcraft.school.jw.module.notification;

import com.extcraft.school.jw.dao.notification.NotificationDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.notification.NotificationRead;
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
 * @version 1.0 2023/12/20
 */
@WebServlet("/api/readNotification")
public class ReadNotificationAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        if (id == null) return;
        User user = (User) req.getSession().getAttribute(SessionKeys.USER);
        NotificationDao.addNotificationRead(NotificationRead.builder()
                .notificationID(Integer.parseInt(id))
                .userID(user.getId())
                .build());
        resp.sendRedirect(PathFactory.buildPath("/notification/"));
    }
}
