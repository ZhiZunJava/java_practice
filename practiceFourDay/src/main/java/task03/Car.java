package task03;

public class Car implements Runnable {
    private int id;
    private TrafficLight light;

    public Car(int id, TrafficLight light) {
        this.id = id;
        this.light = light;
    }

    @Override
    public void run() {
        if (light.isRed()) {
            System.out.println("车辆 " + id + " 停在红灯前等待");
        } else {
            System.out.println("车辆 " + id + " 通过绿灯");
        }
    }
}
