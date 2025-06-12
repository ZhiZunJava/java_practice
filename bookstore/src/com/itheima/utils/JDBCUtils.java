package com.itheima.utils;



import com.alibaba.druid.pool.DruidDataSourceFactory;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JDBCUtils {
    // 声明连接池对象
    private static DataSource dataSource;
    // 初始化连接池容器
    static {
        try {
            // 加载druid.properties配置文件
            Properties properties = new Properties();
            properties.load(new FileInputStream("src/druid.properties"));
            // 通过Druid的工厂，创建连接池对象
            dataSource =
                    DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            throw new RuntimeException("druid连接池初始化失败...");
        }
    }
    // 获取连接池对象的静态方法
    public static DataSource getDataSource() {
        return dataSource;
    }
    // 获取数据库连接对象的静态方法
    public static Connection getConnection() throws Exception {
        return dataSource.getConnection();
    }
    // 释放资源的方法（connection对象不是销毁，而是归还到连接池）
    public static void release(ResultSet resultSet, Statement statement,
                               Connection connection) {
        // 关闭ResultSet
        if (resultSet != null) {
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭Statement
        if (statement != null) {
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        // 关闭Connection
        if (connection != null) {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
