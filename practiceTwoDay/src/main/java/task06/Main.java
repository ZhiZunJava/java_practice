package task06;

public class Main {
    public static void main(String[] args) {
        ExampleObject example = new ExampleObject();
        example.setId(1);
        example.setName("祝融号");

        // 对象转JSON字符串
        String json = JsonUtil.toJson(example);
        System.out.println("对象转JSON: " + json);

        // JSON字符串转对象
        ExampleObject newExample = JsonUtil.fromJson(json, ExampleObject.class);
        System.out.println("JSON转对象 - id: " + newExample.getId() + ", name: " + newExample.getName());
    }
}