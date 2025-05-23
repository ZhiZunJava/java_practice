package task02;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Properties;

public class SerAPP {
    public static void main(String[] args) throws Exception {
        // 1. 读取配置文件
        Properties prop = new Properties();
        try (Reader reader = new InputStreamReader(
                Files.newInputStream(Paths.get("src/main/java/task02/config.properties")), StandardCharsets.UTF_8)) {
            prop.load(reader);
        }

        // 2. 读取对象信息字符串
        String spaceStr = prop.getProperty("space");

        // 3. 解析类名和字段
        String[] parts = spaceStr.split(";");
        String className = parts[0];
        Class<?> clazz = Class.forName(className);
        Object obj = clazz.getDeclaredConstructor().newInstance();

        // 4. 反射设置字段值
        for (int i = 1; i < parts.length; i++) {
            String[] entry = parts[i].split("=");
            String fieldName = entry[0];
            String value = entry[1];

            Field field = clazz.getDeclaredField(fieldName);
            field.setAccessible(true);

            if (field.getType() == int.class) {
                field.set(obj, Integer.parseInt(value));
            } else if (field.getType() == String.class) {
                field.set(obj, value);
            }
        }

        // 5. 打印对象
        System.out.println("反序列化获得的space: " + obj);
    }
}
