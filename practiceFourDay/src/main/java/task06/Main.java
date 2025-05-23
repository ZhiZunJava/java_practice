package task06;

import java.util.concurrent.atomic.AtomicInteger;

public class Main {
    public static final AtomicInteger rabbitDistance = new AtomicInteger(0);
    public static final AtomicInteger tortoiseDistance = new AtomicInteger(0);

    public static void main(String[] args) {
        // 创建并启动乌龟和兔子线程
        Thread tortoise = new Tortoise();
        Thread rabbit = new Rabbit();

        tortoise.start();
        rabbit.start();
    }
}