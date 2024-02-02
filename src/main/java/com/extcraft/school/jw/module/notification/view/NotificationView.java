package com.extcraft.school.jw.module.notification.view;

import com.extcraft.school.jw.dao.notification.NotificationDao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.entity.notification.Notification;
import com.extcraft.school.jw.entity.notification.NotificationRead;
import com.extcraft.school.jw.module.base.AbstractBaseView;
import com.extcraft.school.jw.util.SessionKeys;
import org.thymeleaf.context.WebContext;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/20
 */
@WebServlet("/notification/")
public class NotificationView extends AbstractBaseView {

    @Override
    protected void handle(WebContext context, HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute(SessionKeys.USER);
        List<Notification> notificationList = NotificationDao.getNotificationsByUserID(user.getId());
        List<NotificationRead> notificationReadList = NotificationDao.getNotificationReadsByUserID(user.getId());
        // Convert notificationReadList to a Set of notification IDs for efficient lookup
        Set<Long> readNotificationIds = notificationReadList.stream()
                .map(NotificationRead::getNotificationID)
                .collect(Collectors.toSet());
        // Create a map of Notification to Boolean indicating if it's read
        Map<Notification, Boolean> notificationsParam = notificationList.stream()
                .collect(Collectors.toMap(notification -> notification,
                        notification -> readNotificationIds.contains(notification.getId())));
        context.setVariable("notifications",notificationsParam);
    }
}
