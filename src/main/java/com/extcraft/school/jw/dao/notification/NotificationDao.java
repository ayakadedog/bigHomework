package com.extcraft.school.jw.dao.notification;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.notification.Notification;
import com.extcraft.school.jw.entity.notification.NotificationRead;
import com.extcraft.school.jw.util.DBWrapper;
import com.extcraft.school.jw.util.Pair;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/19
 */
@Dao
public class NotificationDao {

    public static void init() {
        DBWrapper.execute("CREATE TABLE IF NOT EXISTS `notification`(\n" +
                "    `id` BIGINT AUTO_INCREMENT ,\n" +
                "    `title` VARCHAR(255) NOT NULL,\n" +
                "    `content` TEXT NOT NULL,\n" +
                "    `target_table` VARCHAR(64) NOT NULL,\n" +
                "    `target_id` BIGINT NOT NULL,\n" +
                "    `created_at` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    PRIMARY KEY (`id`, `target_table`, `target_id`)\n" +
                ")");
        DBWrapper.execute("CREATE TABLE IF NOT EXISTS `notification_read`(\n" +
                "    `notification_id` BIGINT NOT NULL,\n" +
                "    `user_id` BIGINT NOT NULL,\n" +
                "    `read_at` DATETIME DEFAULT CURRENT_TIMESTAMP,\n" +
                "    PRIMARY KEY (`notification_id`, `user_id`)\n" +
                ")");
    }

    public static List<Notification> getNotificationsByUserID(long userID) {
        final String sql = "SELECT `notification`.*\n" +
                "FROM `notification`\n" +
                "         LEFT JOIN `enrollments` ON\n" +
                "    `notification`.`target_table` = 'enrollments' AND\n" +
                "    `notification`.`target_id` = `enrollments`.`course_id`\n" +
                "WHERE (\n" +
                "    `notification`.`target_table` = 'enrollments' AND `enrollments`.`student_id` = ?)\n" +
                "   OR (`notification`.`target_table` = 'user' AND `notification`.`target_id` = ?)";
        return DBWrapper.executeQuery(sql, Lists.newArrayList(String.valueOf(userID), String.valueOf(userID)), defaultNotificationListCallback());
    }

    public static List<NotificationRead> getNotificationReadsByUserID(long userID) {
        final String sql = "SELECT * from `notification_read` WHERE `user_id`=?";
        return DBWrapper.executeQuery(sql, Lists.newArrayList(String.valueOf(userID)), defaultNotificationReadListCallback());
    }

    public static Notification addNotification(Notification notification) {
        final String sql = "INSERT INTO `notification`(`title`, `content`, `target_table`, `target_id`) VALUES (?,?,?,?)";
        Pair<Integer, Long> pair = DBWrapper.executeUpdateWithGeneratedKeys(sql,
                notification.getTitle(),
                notification.getContent(),
                notification.getTargetTable(),
                String.valueOf(notification.getTargetID()));
        if (pair.getV1() != 1) return null;
        notification.setId(pair.getV2());
        return notification;
    }

    public static int addNotificationReads(List<NotificationRead> notificationReadList) {
        StringBuilder sql = new StringBuilder("INSERT IGNORE INTO `notification_read`(`notification_id`, `user_id`) VALUES ");
        notificationReadList.forEach(notificationRead -> sql.append("(?,?),"));
        sql.deleteCharAt(sql.length() - 1);
        return DBWrapper.executeUpdate(
                sql.toString(),
                notificationReadList.stream()
                        .flatMap(notificationRead -> Lists.newArrayList(
                                String.valueOf(notificationRead.getNotificationID()),
                                String.valueOf(notificationRead.getUserID())).stream())
                        .toArray(String[]::new)
        );
    }

    public static int addNotificationRead(NotificationRead notificationRead) {
        final String sql = "INSERT IGNORE INTO `notification_read`(`notification_id`, `user_id`) VALUES (?,?)";
        return DBWrapper.executeUpdate(sql,
                String.valueOf(notificationRead.getNotificationID()),
                String.valueOf(notificationRead.getUserID()));
    }

    private static Function<ResultSet, List<NotificationRead>> defaultNotificationReadListCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            List<NotificationRead> notificationReads = Lists.newArrayList();
            try {
                while (resultSet.next()) {
                    notificationReads.add(notificationReadMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return notificationReads;
        };
    }

    private static Function<ResultSet, List<Notification>> defaultNotificationListCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            List<Notification> notifications = Lists.newArrayList();
            try {
                while (resultSet.next()) {
                    notifications.add(notificationMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return notifications;
        };
    }

    private static Function<ResultSet, Notification> defaultSingleNotificationCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            Notification notification = null;
            try {
                if (resultSet.next()) {
                    notification = notificationMapper.apply(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return notification;
        };
    }

    private static final Function<ResultSet, NotificationRead> notificationReadMapper = resultSet -> {
        NotificationRead notificationRead;
        try {
            notificationRead = NotificationRead.builder()
                    .notificationID(resultSet.getLong("notification_id"))
                    .userID(resultSet.getLong("user_id"))
                    .readAt(resultSet.getString("read_at"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notificationRead;
    };

    private static final Function<ResultSet, Notification> notificationMapper = resultSet -> {
        Notification notification;
        try {
            notification = Notification.builder()
                    .id(resultSet.getLong("id"))
                    .title(resultSet.getString("title"))
                    .content(resultSet.getString("content"))
                    .createdAt(resultSet.getString("created_at"))
                    .targetTable(resultSet.getString("target_table"))
                    .targetID(resultSet.getLong("target_id"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return notification;
    };

}
