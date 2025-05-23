package task06;

class Rabbit extends Thread {
    @Override
    public void run() {
        for (int i = 1; i <= 400; i++) { // 每次跑2米，400次即800米
            Main.rabbitDistance.addAndGet(2);

            // 模拟兔子到600米处休息60秒
            if (Main.rabbitDistance.get() == 600) {
                System.out.println("兔子 600米，开始睡觉60秒...");
                try {
                    Thread.sleep(60000); // 睡60秒
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            try {
                Thread.sleep(100); // 兔子速度：2 米 / 100 毫秒
                System.out.println("兔子跑了 " + Main.rabbitDistance + " 米，乌龟跑了 " + Main.tortoiseDistance + " 米");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            if (Main.rabbitDistance.get() >= 800) {
                System.out.println("兔子赢得了比赛，此时乌龟跑了 " + Main.tortoiseDistance + " 米");
                break;
            }
        }
    }
}