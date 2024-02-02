package com.extcraft.school.jw.dao.auth;

import com.extcraft.school.jw.annotation.Dao;
import com.extcraft.school.jw.entity.auth.User;
import com.extcraft.school.jw.util.DBWrapper;
import com.extcraft.school.jw.util.Pair;
import com.google.common.collect.Lists;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

/**
 * 用户类
 */
@Dao
public class UserDao {
    public static void init() {
        String createUserTableSql = "CREATE TABLE IF NOT EXISTS user (" +
                "id BIGINT AUTO_INCREMENT COMMENT '用户ID' PRIMARY KEY, " +
                "name VARCHAR(100) NOT NULL COMMENT '姓名', " +
                "email VARCHAR(100) NOT NULL COMMENT '邮箱', " +
                "user_name VARCHAR(64) UNIQUE NOT NULL COMMENT '用户名', " +
                "user_password VARCHAR(64) NOT NULL COMMENT '密码', " +
                "role VARCHAR(50) NOT NULL COMMENT '角色', " +
                "class_name VARCHAR(100) NULL COMMENT '班级', " +
                "subject VARCHAR(100) NULL COMMENT '科目' " +
                ") COMMENT '用户表' COLLATE = utf8_bin;";
        DBWrapper.execute(createUserTableSql);
        // insert default user
        DBWrapper.executeUpdate("INSERT IGNORE INTO `user`( `id`,`name`, `email`, `user_name`,`user_password`,`role`,`class_name`,`subject`) VALUES\n" +
                "(1,'admin','admin@example.com','admin',SHA2('admin',256),'admin','admin','admin')");
    }

    public static User addUser(User user) {
        final String sql = "INSERT INTO `user`(`name`, `email`, `user_name`,`user_password`,`role`,`class_name`,`subject`) VALUES (?,?,?,SHA2(?,256),?,?,?)";
        Pair<Integer, Long> result = DBWrapper.executeUpdateWithGeneratedKeys(sql,
                user.getName(),
                user.getEmail(),
                user.getUserName(),
                user.getUserPassword(),
                user.getRole(),
                user.getClassName(),
                user.getSubject());
        if (result.getV1() != 1) return null;
        user.setId(result.getV2());
        return user;
    }

    public static User login(String username, String password) {
        final String sql = "SELECT * FROM `user` WHERE `user_name`= ? AND `user_password`= SHA2(?,256) LIMIT 1";
        return DBWrapper.executeQuery(sql,
                Lists.newArrayList(username, password), defaultSingleUserCallback());
    }

    private static Function<ResultSet, List<User>> defaultUserListCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            List<User> users = Lists.newArrayList();
            try {
                while (resultSet.next()) {
                    users.add(userMapper.apply(resultSet));
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return users;
        };
    }

    private static Function<ResultSet,User> defaultSingleUserCallback() {
        return resultSet -> {
            if (resultSet == null) return null;
            User user = null;
            try {
                if (resultSet.next()) {
                    user = userMapper.apply(resultSet);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
            return user;
        };
    }

    private static final Function<ResultSet,User> userMapper = resultSet -> {
        User user;
        try {
            user = User.builder()
                    .id(resultSet.getLong("id"))
                    .name(resultSet.getString("name"))
                    .email(resultSet.getString("email"))
                    .userName(resultSet.getString("user_name"))
                    .userPassword(resultSet.getString("user_password"))
                    .role(resultSet.getString("role"))
                    .className(resultSet.getString("class_name"))
                    .subject(resultSet.getString("subject"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return user;
    };

}
