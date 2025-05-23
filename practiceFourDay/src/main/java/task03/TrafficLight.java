package task03;

public class TrafficLight implements Runnable {
    private boolean red = true;

    public synchronized boolean isRed() {
        return red;
    }

    @Override
    public void run() {
        while (true) {
            try {
                red = true;
                System.out.println("红灯亮，车辆需等待");
                Thread.sleep(3000);

                red = false;
                System.out.println("绿灯亮，车辆可通行");
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
