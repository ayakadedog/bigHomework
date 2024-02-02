package com.extcraft.school.jw.entity.discussion;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * TO-EDIT-FULL-DESCRIPTION
 *
 * @author SmallL-U
 * @version 1.0 2023/12/24
 */
@Data
@Builder
public class DiscussionContent {
    private long id;
    private long discussionID;
    private String content;
    private String createdAt;
    private String updatedAt;
    private long userID;
    private transient String userName;
}
