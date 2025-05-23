package task05;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class ArithmeticCardGenerator {

    public static void main(String[] args) {
        List<String> questions = new ArrayList<>();
        Random random = new Random();

        // 1. 生成50道加减法题
        while (questions.size() < 50) {
            int a = random.nextInt(100) + 1; // 1~100
            int b = random.nextInt(100) + 1;
            String question;

            if (random.nextBoolean()) {
                // 加法，限制结果 <= 100
                if (a + b <= 100) {
                    question = a + " + " + b + " = ";
                    questions.add(question);
                }
            } else {
                // 减法，限制结果 > 0
                if (a > b) {
                    question = a + " - " + b + " = ";
                    questions.add(question);
                }
            }
        }

        // 2. 生成20道乘除法题
        while (questions.size() < 70) {
            int a = random.nextInt(20) + 1; // 1~20
            int b = random.nextInt(19) + 1; // 1~19，避免除数为0
            String question;

            if (random.nextBoolean()) {
                // 乘法，无特别限制
                question = a + " × " + b + " = ";
            } else {
                // 除法，允许出现小数
                question = a + " ÷ " + b + " = ";
            }

            questions.add(question);
        }

        // 3. 打印题目卡，按每行5道输出
        System.out.println("-------- 算术题卡 --------");
        for (int i = 0; i < questions.size(); i++) {
            System.out.printf("%-15s", questions.get(i));
            if ((i + 1) % 5 == 0) System.out.println();
        }
        System.out.println("-------- 算术题卡 --------");
    }
}
