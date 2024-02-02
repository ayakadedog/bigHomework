package com.extcraft.school.jw.util;

import com.extcraft.school.jw.annotation.DepInit;
import com.extcraft.school.jw.config.GeneralConfig;
import com.extcraft.school.jw.config.general.DatabaseConfig;
import com.google.common.collect.Lists;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * 数据库包装器 full life cycle
 * 对hikaricp的简单封装，未引入异步
 * 未引入PreparedStatement的set方法 由于是课程设计 请自行拼接sql
 *
 * @author SmallL-U
 * @version 1.0 2023/12/9
 */
@DepInit
@SuppressWarnings({"SqlSourceToSinkFlow", "CallToPrintStackTrace"})
public class DBWrapper {

    public static void init() {
        HikariConfig config = new HikariConfig();
        DatabaseConfig databaseConfig = GeneralConfig.INSTANCE.getDatabaseConfig();
        config.setDriverClassName(databaseConfig.getDriver());
        config.setJdbcUrl(databaseConfig.getUrl());
        config.setUsername(databaseConfig.getUsername());
        config.setPassword(databaseConfig.getPassword());
        ds = new HikariDataSource(config);
    }

    private static DataSource ds;

    /**
     * 执行sql
     * @param sql sql(此处不防止sql注入，仅建表)
     */
    public static void execute(String sql) {
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            statement.execute();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @Deprecated
    public static <T> T executeQuery(String sql, Function<ResultSet, T> callback) {
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql);
                ResultSet resultSet = statement.executeQuery()
        ) {
            return callback.apply(resultSet);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    private static Consumer<PreparedStatement> defaultStatementConsumer(Iterable<String> params) {
        return statement -> {
            int i = 1;
            if (params != null) {
                for (String param : params) {
                    try {
                        statement.setString(i, param);
                    } catch (SQLException e) {
                        e.printStackTrace();
                        throw new RuntimeException(e);
                    }
                    i++;
                }
            }
        };
    }

    /**
     * 定参数查询
     *
     * @param sql      sql
     * @param params   参数
     * @param callback 回调
     * @param <T>      回调返回值类型
     * @return 回调返回值
     */
    public static <T> T executeQuery(String sql, List<String> params, Function<ResultSet, T> callback) {
        return executeQueryIndefiniteParams(sql, callback, defaultStatementConsumer(params));
    }

    /**
     * 不定参数AND查询
     *
     * @param baseSQL   基础sql
     * @param params    参数[数据库字段名,值]
     * @param endingSQL 附加sql
     * @param callback  回调
     * @param <T>       回调返回值类型
     * @return 回调返回值
     */
    public static <T> T executeQueryIndefiniteEquivalentParams(String baseSQL, Map<String, String> params, String endingSQL, Function<ResultSet, T> callback) {
        return executeQueryIndefiniteParams(baseSQL, sql -> {
            int idx = 0;
            for (String key : params.keySet()) {
                if (idx == 0) sql.append(" WHERE ");
                else sql.append(" AND ");
                sql.append('`').append(key).append('`').append(" = ").append('?');
                idx++;
            }
        }, endingSQL, callback, defaultStatementConsumer(params.values()));
    }

    /**
     * 不定参数OR查询
     *
     * @param baseSQL   基础sql
     * @param params    参数[数据库字段名,值]
     * @param endingSQL 附加sql
     * @param callback  回调
     * @param <T>       回调返回值类型
     * @return 回调返回值
     */
    public static <T> T executeQueryIndefiniteOrValentParams(String baseSQL, Map<String, String> params, String endingSQL, Function<ResultSet, T> callback) {
        return executeQueryIndefiniteParams(baseSQL, sql -> {
            int idx = 0;
            for (String key : params.keySet()) {
                if (idx == 0) sql.append(" WHERE ");
                else sql.append(" OR ");
                sql.append('`').append(key).append('`').append(" = ").append('?');
                idx++;
            }
        }, endingSQL, callback, defaultStatementConsumer(params.values()));
    }

    /**
     * 不定参数查询
     *
     * @param baseSQL           基础sql
     * @param sqlHandler        sql处理器
     * @param endingSQL         附加sql
     * @param callback          回调
     * @param statementConsumer statement消费器
     * @param <T>               回调返回值类型
     * @return 回调返回值
     */
    private static <T> T executeQueryIndefiniteParams(String baseSQL, Consumer<StringBuilder> sqlHandler, String endingSQL, Function<ResultSet, T> callback, Consumer<PreparedStatement> statementConsumer) {
        StringBuilder builder = new StringBuilder(baseSQL);
        sqlHandler.accept(builder);
        if (endingSQL != null) builder.append(endingSQL);
        return executeQueryIndefiniteParams(builder.toString(), callback, statementConsumer);
    }

    /**
     * 不定参数查询
     *
     * @param sql      sql
     * @param callback 回调
     * @param consumer statement消费器
     * @param <T>      回调返回值类型
     * @return 回调返回值
     */
    private static <T> T executeQueryIndefiniteParams(String sql, Function<ResultSet, T> callback, Consumer<PreparedStatement> consumer) {
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            consumer.accept(statement);
            try (ResultSet resultSet = statement.executeQuery()) {
                return callback.apply(resultSet);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 定参数更新
     *
     * @param sql    sql
     * @param params 参数
     * @return 更新行数
     */
    public static int executeUpdate(String sql, String... params) {
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql)
        ) {
            if (params.length != 0) defaultStatementConsumer(Lists.newArrayList(params)).accept(statement);
            return statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    /**
     * 定参数自增值更新
     *
     * @param sql    sql
     * @param params 参数
     * @return [更新行数,自增ID]
     */
    public static Pair<Integer, Long> executeUpdateWithGeneratedKeys(String sql, String... params) {
        try (
                Connection connection = ds.getConnection();
                PreparedStatement statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS)
        ) {
            if (params.length != 0) defaultStatementConsumer(Lists.newArrayList(params)).accept(statement);
            int i = statement.executeUpdate();
            ResultSet resultSet = statement.getGeneratedKeys();
            if (resultSet.next()) {
                return new Pair<>(i, resultSet.getLong(1));
            } else {
                return new Pair<>(i, null);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

}
