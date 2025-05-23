package task01;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class TableCreator {

    // 获取数据库连接
    public static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/demo";
        String username = "root";
        String password = "123456";
        return DriverManager.getConnection(url, username, password);
    }

    // 获取 Java 字段对应的 MySQL 列类型
    public static String getColumnType(Class<?> fieldType) {
        if (fieldType == String.class) {
            return "VARCHAR(255)";
        } else if (fieldType == int.class || fieldType == Integer.class) {
            return "INT";
        } else if (fieldType == double.class || fieldType == Double.class) {
            return "DOUBLE";
        } else if (fieldType == char.class || fieldType == Character.class) {
            return "CHAR";
        }
        return "VARCHAR(255)";
    }

    // 创建表方法
    public static void createTable(Class<?> entityClass) {
        String tableName = entityClass.getSimpleName();
        StringBuilder sql = new StringBuilder("CREATE TABLE IF NOT EXISTS ");
        sql.append(tableName).append(" (");

        Field[] fields = entityClass.getDeclaredFields();
        for (Field field : fields) {
            String columnName = field.getName();
            String columnType = getColumnType(field.getType());
            sql.append(columnName).append(" ").append(columnType).append(", ");
        }

        // 删除最后多余的逗号
        sql.deleteCharAt(sql.length() - 2);
        sql.append(")");

        // 执行SQL语句
        try (Connection connection = getConnection(); Statement statement = connection.createStatement()) {
            statement.executeUpdate(sql.toString());
            System.out.println("表创建成功");
        } catch (Exception e) {
            System.out.println("表创建失败");
            e.printStackTrace();
        }
    }
}

