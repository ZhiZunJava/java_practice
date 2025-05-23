package task05;

class Drainer implements Runnable {
    private final Pool pool;

    public Drainer(Pool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                pool.drainWater();
            } catch (InterruptedException e) {
                System.out.println("排水线程中断");
                break;
            }
        }
    }
}