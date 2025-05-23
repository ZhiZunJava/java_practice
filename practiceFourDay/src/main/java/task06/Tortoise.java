package task06;

class Tortoise extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 800; i++) {
            if (Main.rabbitDistance.get() >= 800) {
                System.out.println("🐇 兔子赢得了比赛，此时乌龟跑了 " + Main.tortoiseDistance + " 米");
                break;
            }

            Main.tortoiseDistance.addAndGet(1);

            try {
                Thread.sleep(100); // 乌龟速度：1 米 / 100 毫秒
                System.out.println("🐢 乌龟跑了 " + Main.tortoiseDistance + " 米，兔子跑了 " + Main.rabbitDistance + " 米");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Main.tortoiseDistance.get() >= 800) {
                System.out.println("🐢 乌龟赢得了比赛，此时兔子跑了 " + Main.rabbitDistance + " 米");
                break;
            }
        }
    }
}