package task05;

class Filler implements Runnable {
    private final Pool pool;

    public Filler(Pool pool) {
        this.pool = pool;
    }

    @Override
    public void run() {
        while (true) {
            try {
                pool.fillWater();
            } catch (InterruptedException e) {
                System.out.println("注水线程中断");
                break;
            }
        }
    }
}