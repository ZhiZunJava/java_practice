package task02;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

import java.util.*;

public class PointExchange {

    private static final Map<Integer, String> gifts = new HashMap<>();
    private static final Map<Integer, Integer> giftCost = new HashMap<>();
    private static final Map<String, Integer> memberPoints = new HashMap<>();

    static {
        gifts.put(1, "加湿器");
        gifts.put(2, "平底锅");
        gifts.put(3, "电风扇");

        giftCost.put(1, 1000);
        giftCost.put(2, 1500);
        giftCost.put(3, 1200);

        // 模拟几个会员初始积分
        memberPoints.put("cz202206112", 4000);
        memberPoints.put("cz202206113", 3000);
        memberPoints.put("cz202206114", 2000);
    }

    public static void main(String[] args) {
        info_validation();
    }

    public static void info_validation() {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n本次可兑换的礼品如下：");
            for (Map.Entry<Integer, String> entry : gifts.entrySet()) {
                System.out.println(entry.getKey() + ". " + entry.getValue() + "（" + giftCost.get(entry.getKey()) + "积分）");
            }

            System.out.print("请输入您要兑换的礼品编号：");
            int giftId = scanner.nextInt();
            if (!gifts.containsKey(giftId)) {
                System.out.println("礼品编号无效！");
                continue;
            }

            System.out.print("请输入您的会员编号（如 cz202206112）：");
            String memberId = scanner.next();
            if (!memberPoints.containsKey(memberId)) {
                System.out.println("会员编号不存在！");
                continue;
            }

            System.out.print("请输入兑换数量：");
            int quantity = scanner.nextInt();
            int totalCost = giftCost.get(giftId) * quantity;
            int currentPoints = memberPoints.get(memberId);

            if (totalCost > currentPoints) {
                System.out.println("积分不足，兑换失败！");
            } else {
                memberPoints.put(memberId, currentPoints - totalCost);
                System.out.println("兑换成功，您兑换了 " + quantity + " 件 " + gifts.get(giftId));
                System.out.println("您的剩余积分为：" + memberPoints.get(memberId));
            }

            System.out.print("是否继续兑换？（Y/N）：");
            String cont = scanner.next();
            if (cont.equalsIgnoreCase("N")) {
                System.out.println("感谢使用积分兑换系统！");
                break;
            }
        }
    }
}