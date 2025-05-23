package task03;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;

public class FoodInventory {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Map<String, Integer> inventory = new LinkedHashMap<>();

        System.out.println("-------- 食材入库 --------");

        while (true) {
            System.out.print("请输入入库信息（格式：食材名称-入库数量，如 土豆-10）：");
            String input = scanner.nextLine().trim();
            String[] parts = input.split("-");

            if (parts.length != 2) {
                System.out.println("输入格式有误，请重新输入！");
                continue;
            }

            String name = parts[0];
            int quantity;

            try {
                quantity = Integer.parseInt(parts[1]);
            } catch (NumberFormatException e) {
                System.out.println("数量输入有误，请输入数字！");
                continue;
            }

            inventory.put(name, inventory.getOrDefault(name, 0) + quantity);

            System.out.print("是否继续入库，（是输入Y，否输入N）：");
            String choice = scanner.nextLine().trim().toUpperCase();
            if (choice.equals("N")) {
                break;
            }
        }

        // 输出入库明细
        System.out.println("\n本次入库详情：");
        int total = 0;
        for (Map.Entry<String, Integer> entry : inventory.entrySet()) {
            System.out.println(entry.getKey() + "-" + entry.getValue());
            total += entry.getValue();
        }
        System.out.println("入库总数量：" + total);
    }
}
