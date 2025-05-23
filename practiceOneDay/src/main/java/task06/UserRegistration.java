package task06;

import java.util.Scanner;
import java.util.regex.Pattern;

public class UserRegistration {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String username, password, email, phone, idCard;

        // 用户名验证：6位以内，仅限字母、数字、下划线
        while (true) {
            System.out.print("请输入用户名（6位以内，字母/数字/下划线）：");
            username = scanner.nextLine();
            if (Pattern.matches("^[a-zA-Z0-9_]{1,6}$", username)) break;
            System.out.println("❌ 用户名格式错误，请重新输入！");
        }

        // 密码验证：12位以内，必须包含大小写字母、数字、特殊字符
        while (true) {
            System.out.print("请输入密码（12位以内，必须含大小写字母、数字、特殊字符@#$%^&*）：");
            password = scanner.nextLine();
            if (password.length() <= 12 &&
                    Pattern.compile("[A-Z]").matcher(password).find() &&
                    Pattern.compile("[a-z]").matcher(password).find() &&
                    Pattern.compile("[0-9]").matcher(password).find() &&
                    Pattern.compile("[@#$%^&*]").matcher(password).find()) {
                break;
            }
            System.out.println("❌ 密码不符合复杂度要求，请重新输入！");
        }

        // 邮箱验证
        while (true) {
            System.out.print("请输入邮箱（如 test@abc.com）：");
            email = scanner.nextLine();
            if (Pattern.matches("^[a-zA-Z0-9_.-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}$", email)) break;
            System.out.println("❌ 邮箱格式不正确，请重新输入！");
        }

        // 手机号验证
        while (true) {
            System.out.print("请输入手机号（11位，中国大陆）：");
            phone = scanner.nextLine();
            if (Pattern.matches("^1[3-9]\\d{9}$", phone)) break;
            System.out.println("❌ 手机号格式错误，请重新输入！");
        }

        // 身份证号验证（简单版格式）
        while (true) {
            System.out.print("请输入身份证号（18位）：");
            idCard = scanner.nextLine();
            if (Pattern.matches("^[1-9]\\d{5}(19|20)\\d{2}(0[1-9]|1[0-2])"
                    + "(0[1-9]|[12]\\d|3[01])\\d{3}[0-9Xx]$", idCard)) {
                break;
            }
            System.out.println("❌ 身份证号格式错误，请重新输入！");
        }

        System.out.println("✅ 注册成功！");
    }
}
