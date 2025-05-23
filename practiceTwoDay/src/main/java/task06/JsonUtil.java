package task06;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

class JsonUtil {
    // 对象转JSON字符串
    public static String toJson(Object obj) {
        if (obj == null) {
            return "null";
        }
        Class<?> clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        Map<String, Object> jsonMap = new HashMap<>();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                jsonMap.put(field.getName(), field.get(obj));
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return jsonMap.toString().replaceAll("=", ":");
    }

    // JSON字符串转对象
    @SuppressWarnings("unchecked")
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.equals("null")) {
            return null;
        }
        json = json.replace("{", "").replace("}", "").replace(":", "=");
        String[] keyValuePairs = json.split(",");
        Map<String, Object> jsonMap = new HashMap<>();
        for (String pair : keyValuePairs) {
            String[] parts = pair.split("=");
            if (parts.length == 2) {
                String key = parts[0].trim();
                String value = parts[1].trim();
                jsonMap.put(key, value);
            }
        }
        try {
            // 使用getDeclaredConstructor()而不是getConstructor()
            T instance = clazz.getDeclaredConstructor().newInstance();
            for (Field field : clazz.getDeclaredFields()) {
                field.setAccessible(true);
                if (jsonMap.containsKey(field.getName())) {
                    // 类型转换处理
                    Object value = jsonMap.get(field.getName());
                    if (value != null) {
                        Class<?> fieldType = field.getType();
                        if (fieldType == int.class || fieldType == Integer.class) {
                            value = Integer.parseInt(value.toString());
                        }
                        field.set(instance, value);
                    }
                }
            }
            return instance;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}