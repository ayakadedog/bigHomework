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
public class Notification {
    private long id;
    private String title;
    private String content;
    private String targetTable;
    private long targetID;
    private String createdAt;
}
