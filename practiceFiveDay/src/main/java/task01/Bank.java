package task01;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

public class Bank {
    private List<User> userList = new ArrayList<>();

    public Bank(List<User> userList) {
        this.userList = userList;
    }

    public synchronized User getUserByCard(String card) {
        for (User u : userList) {
            if (u.getU_login_name().equals(card)) {
                return u;
            }
        }
        return null;
    }

    public void saveMoney(String card, String pwd, String moneyNum) {
        User u = getUserByCard(card);
        synchronized (Bank.class) {
            if (u != null && u.getU_login_name().equals(card) && u.getU_login_pwd().equals(pwd)) {
                BigDecimal oldBalance = new BigDecimal(u.getU_wallet());
                BigDecimal income = new BigDecimal(moneyNum);
                u.setU_wallet(oldBalance.add(income).toString());
                u.setSave_money_time(new Date());
                String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(u.getSave_money_time());
                System.out.println(Thread.currentThread().getName() + " 存款 --> " + u.getU_name() + " 在 " + date + " 存入 " + income + " 元");
            }
        }
    }

    public void drawMoney(String card, String pwd, String moneyNum) {
        User u = getUserByCard(card);
        synchronized (Bank.class) {
            if (u != null && u.getU_login_name().equals(card) && u.getU_login_pwd().equals(pwd)) {
                BigDecimal oldBalance = new BigDecimal(u.getU_wallet());
                BigDecimal draw = new BigDecimal(moneyNum);
                if (oldBalance.compareTo(draw) >= 0) {
                    u.setU_wallet(oldBalance.subtract(draw).toString());
                    u.setDraw_money_time(new Date());
                    String date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(u.getDraw_money_time());
                    System.out.println(Thread.currentThread().getName() + " 取款 --> " + u.getU_name() + " 在 " + date + " 取出 " + draw + " 元, 余额: " + u.getU_wallet());
                } else {
                    System.out.println(u.getU_name() + " 取款[" + moneyNum + "]失败, 余额不足");
                }
            }
        }
    }
}
