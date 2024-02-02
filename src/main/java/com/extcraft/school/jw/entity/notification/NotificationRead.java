package com.extcraft.school.jw.entity.notification;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/20
 */
@Data
@Builder
public class NotificationRead {
    private long notificationID;
    private long userID;
    private String readAt;
}
