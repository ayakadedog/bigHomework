package com.extcraft.school.jw.module.notification;

import com.extcraft.school.jw.dao.notification.NotificationDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.notification.Notification;
import com.extcraft.school.jw.entity.notification.NotificationRead;
import com.extcraft.school.jw.util.PathFactory;
import com.extcraft.school.jw.util.SessionKeys;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/20
 */
@WebServlet("/api/readAllNotifications")
public class ReadAllNotificationsAPI extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        User user = (User) req.getSession().getAttribute(SessionKeys.USER);
        List<Notification> notificationList = NotificationDao.getNotificationsByUserID(user.getId());
        NotificationDao.addNotificationReads(
                notificationList
                        .stream()
                        .map(notification ->
                                NotificationRead.builder()
                                        .notificationID(notification.getId())
                                        .userID(user.getId())
                                        .build()
                        ).collect(Collectors.toList())
        );
        resp.sendRedirect(PathFactory.buildPath("/notification/"));
    }
}
