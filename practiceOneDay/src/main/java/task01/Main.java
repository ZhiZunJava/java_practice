package task01;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        MemberCard card = new MemberCard(1023, 98.0);

        Scanner scanner = new Scanner(System.in);
        System.out.print("请输入消费金额：");
        double amount = scanner.nextDouble();

        try {
            card.pay(amount);
        } catch (Exception e) {
            System.out.println("发生错误：" + e.getMessage());
        } finally {
            System.out.println("感谢您对本店的光顾！");
        }
    }
}
