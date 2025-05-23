package task05;

class Pool {
    private final int capacity;
    private int waterLevel = 0; // 当前水量
    private boolean filling = true;

    public Pool(int capacity) {
        this.capacity = capacity;
    }

    // 同步方法注水
    public synchronized void fillWater() throws InterruptedException {
        while (!filling) wait();

        if (waterLevel < capacity) {
            waterLevel += 5;
            System.out.println("正在注水，当前水量：" + waterLevel + "L");

            if (waterLevel >= capacity) {
                System.out.println("泳池已满，停止注水！");
                filling = false;
                notifyAll(); // 唤醒排水线程
            }
        }

        Thread.sleep(1000);
    }

    // 同步方法排水
    public synchronized void drainWater() throws InterruptedException {
        while (filling) wait();

        if (waterLevel > 0) {
            waterLevel -= 2;
            System.out.println("正在排水，当前水量：" + waterLevel + "L");

            if (waterLevel <= 0) {
                System.out.println("泳池排空，停止排水！");
                filling = true;
                notifyAll(); // 唤醒注水线程
            }
        }

        Thread.sleep(1000);
    }
}