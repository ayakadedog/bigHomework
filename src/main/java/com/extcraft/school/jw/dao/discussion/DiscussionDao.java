package com.extcraft.school.jw.dao.discussion;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.discussion.Discussion;
import com.extcraft.school.jw.entity.discussion.DiscussionContent;
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
 * @version 1.0 2023/12/24
 */
@Dao
public class DiscussionDao {
    public static void init() {
        DBWrapper.execute("CREATE TABLE IF NOT EXISTS `discussion`(\n" +
                "    `id` BIGINT AUTO_INCREMENT,\n" +
                "    `course_id` BIGINT NOT NULL,\n" +
                "    `title` VARCHAR(255) NOT NULL,\n" +
                "    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "    `user_id` BIGINT NOT NULL,\n" +
                "    `views` BIGINT NOT NULL DEFAULT 0,\n" +
                "    PRIMARY KEY(`id`)\n" +
                ")");
        DBWrapper.execute("CREATE TABLE IF NOT EXISTS `discussion_content`(\n" +
                "    `id` BIGINT AUTO_INCREMENT,\n" +
                "    `discussion_id` INT NOT NULL,\n" +
                "    `content` TEXT NOT NULL,\n" +
                "    `created_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "    `updated_at` DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,\n" +
                "    `user_id` BIGINT NOT NULL,\n" +
                "    PRIMARY KEY(`id`)\n" +
                ");");
    }

    public static void updateDiscussionContent(String id,String content) {
        final String sql = "UPDATE `discussion_content` SET `content`=?, `updated_at`=CURRENT_TIMESTAMP WHERE `id`=?";
        DBWrapper.executeUpdate(sql,content,id);
    }

    public static void addDiscussion(Discussion discussion) {
        final String sql = "INSERT INTO `discussion`(`course_id`,`title`,`user_id`) VALUES(?,?,?)";
        Pair<Integer,Long> pair = DBWrapper.executeUpdateWithGeneratedKeys(sql,String.valueOf(discussion.getCourseID()),discussion.getTitle(),String.valueOf(discussion.getUserID()));
        if (pair.getV1() != 1) return;
        discussion.setId(pair.getV2());
    }

    public static void addDiscussionContent(DiscussionContent discussionContent) {
        final String sql = "INSERT INTO `discussion_content`(`discussion_id`,`content`,`user_id`) VALUES(?,?,?)";
        DBWrapper.executeUpdate(sql,String.valueOf(discussionContent.getDiscussionID()),discussionContent.getContent(),String.valueOf(discussionContent.getUserID()));
    }

    public static Discussion getDiscussion(String id) {
        final String sql = "SELECT * FROM `discussion` d LEFT JOIN `user` u ON d.`user_id`=u.`id` WHERE d.`id`=?";
        return DBWrapper.executeQuery(sql, Lists.newArrayList(id), defaultSingleDiscussionCallback());
    }

    public static DiscussionContent getDiscussionContent(String id) {
        final String sql = "SELECT * FROM `discussion_content` dc LEFT JOIN `user` u ON dc.`user_id`=u.`id` WHERE dc.`id`=?";
        return DBWrapper.executeQuery(sql, Lists.newArrayList(id), defaultSingleDiscussionContentCallback());
    }

    public static List<DiscussionContent> getDiscussionContents(String discussionID) {
        final String sql = "SELECT * FROM `discussion_content` dc LEFT JOIN `user` u ON dc.`user_id`=u.`id` WHERE `discussion_id`=?";
        return DBWrapper.executeQuery(sql, Lists.newArrayList(discussionID), defaultDiscussionContentListCallback());
    }

    public static List<Discussion> getDiscussions(String courseID) {
        final String sql = "SELECT * FROM `discussion` d LEFT JOIN `user` u ON d.`user_id`=u.`id` WHERE `course_id`=? ORDER BY `created_at` DESC";
        return DBWrapper.executeQuery(sql, Lists.newArrayList(courseID), defaultDiscussionListCallback());
    }

    public static void increaseViews(String id) {
        final String sql = "UPDATE `discussion` SET `views`=`views`+1 WHERE `id`=?";
        DBWrapper.executeUpdate(sql, id);
    }

    public static Function<ResultSet,Discussion> defaultSingleDiscussionCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            try {
                if (!resultSet.next()) return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return discussionMapper.apply(resultSet);
        };
    }

    public static Function<ResultSet, List<Discussion>> defaultDiscussionListCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            List<Discussion> discussions = Lists.newArrayList();
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                discussions.add(discussionMapper.apply(resultSet));
            }
            return discussions;
        };
    }

    public static Function<ResultSet,List<DiscussionContent>> defaultDiscussionContentListCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            List<DiscussionContent> discussionContents = Lists.newArrayList();
            while (true) {
                try {
                    if (!resultSet.next()) break;
                } catch (SQLException e) {
                    throw new RuntimeException(e);
                }
                discussionContents.add(discussionContentMapper.apply(resultSet));
            }
            return discussionContents;
        };
    }

    public static Function<ResultSet,DiscussionContent> defaultSingleDiscussionContentCallback() {
        //noinspection DuplicatedCode
        return resultSet -> {
            if (resultSet == null) return null;
            try {
                if (!resultSet.next()) return null;
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return discussionContentMapper.apply(resultSet);
        };
    }

    private static final Function<ResultSet,DiscussionContent> discussionContentMapper = resultSet -> {
        DiscussionContent discussionContent;
        try {
            discussionContent = DiscussionContent.builder()
                    .id(resultSet.getLong("dc.id"))
                    .discussionID(resultSet.getLong("dc.discussion_id"))
                    .content(resultSet.getString("dc.content"))
                    .createdAt(resultSet.getString("dc.created_at"))
                    .updatedAt(resultSet.getString("dc.updated_at"))
                    .userID(resultSet.getLong("dc.user_id"))
                    .userName(resultSet.getString("u.name"))
                    .build();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return discussionContent;
    };

    private static final Function<ResultSet, Discussion> discussionMapper = resultSet -> {
        Discussion discussion;
        try {
            discussion = Discussion.builder()
                    .id(resultSet.getLong("d.id"))
                    .courseID(resultSet.getLong("d.course_id"))
                    .title(resultSet.getString("d.title"))
                    .createdAt(resultSet.getString("d.created_at"))
                    .userID(resultSet.getLong("d.user_id"))
                    .userName(resultSet.getString("u.name"))
                    .views(resultSet.getLong("d.views"))
                    .build();
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
        return discussion;
    };

}
