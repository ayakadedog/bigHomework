package com.extcraft.school.jw.entity.discussion;

import com.extcraft.school.jw.entity.auth.User;
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
public class Discussion {
    private long id;
    private long courseID;
    private String title;
    private String createdAt;
    private long userID;
    private transient String userName;
    private long views;
}
