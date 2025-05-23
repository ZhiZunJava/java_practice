package task03;

public class TrafficLightProgram {
    public static void main(String[] args) {
        TrafficLight trafficLight = new TrafficLight();
        Thread trafficLightThread = new Thread(trafficLight);
        trafficLightThread.start();

        int id = 1;
        while (true) {
            Car car = new Car(id++, trafficLight);
            new Thread(car).start();

            if (id % 10 == 0) {
                try {
                    Thread.sleep(2000); // 模拟拥堵暂停
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            try {
                Thread.sleep(500); // 每辆车进入间隔
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}