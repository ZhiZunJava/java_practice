package task01;

import java.util.*;

public class Main {
    public static void main(String[] args) {
        List<User> users = new ArrayList<>();
        User u1 = new User();
        u1.setU_login_name("1001");
        u1.setU_login_pwd("123");
        u1.setU_name("张三");
        u1.setU_wallet("1000");

        User u2 = new User();
        u2.setU_login_name("1002");
        u2.setU_login_pwd("456");
        u2.setU_name("李四");
        u2.setU_wallet("500");

        users.add(u1);
        users.add(u2);

        Bank bank = new Bank(users);

        Runnable task1 = () -> bank.saveMoney("1001", "123", "200");
        Runnable task2 = () -> bank.drawMoney("1002", "456", "100");
        Runnable task3 = () -> bank.drawMoney("1001", "123", "50");

        new Thread(task1, "窗口1").start();
        new Thread(task2, "窗口2").start();
        new Thread(task3, "窗口3").start();
    }
}