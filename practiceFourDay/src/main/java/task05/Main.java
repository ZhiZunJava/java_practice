package task05;

public class Main {
    public static void main(String[] args) {
        Pool pool = new Pool(50); // 容量500升

        Thread fillThread = new Thread(new Filler(pool));
        Thread drainThread = new Thread(new Drainer(pool));

        fillThread.start();
        drainThread.start();
    }
}