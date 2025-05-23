package task04;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    // 剩余优惠券数（线程安全）
    private static final int TOTAL_COUPONS = 5;
    private static AtomicInteger remaining = new AtomicInteger(TOTAL_COUPONS);

    public static void main(String[] args) {
        int totalCustomers = 20; // 模拟20位顾客同时参与秒杀

        for (int i = 1; i <= totalCustomers; i++) {
            String name = "顾客" + i;
            new Thread(() -> grabCoupon(name)).start();
        }
    }

    public static void grabCoupon(String customerName) {
        int current;
        while ((current = remaining.get()) > 0) {
            // 尝试抢购
            if (remaining.compareAndSet(current, current - 1)) {
                System.out.println(customerName + " 抢到了优惠券！（剩余：" + (current - 1) + "）");
                return;
            }
        }

        System.out.println(customerName + " 来晚了，优惠券已抢光！");
    }
}
