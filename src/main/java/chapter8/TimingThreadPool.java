package chapter8;


import java.util.concurrent.*;

/**
 * Listing 8.9
 */
public class TimingThreadPool extends ThreadPoolExecutor{


    private final ThreadLocal<Long> startTime = new ThreadLocal<>();


    public TimingThreadPool() {
        super(1, 1, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<>());
    }


    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        super.beforeExecute(t, r);
        startTime.set(System.nanoTime());
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        long taskTime = System.nanoTime() - startTime.get();
        System.out.println("Time elapsing: " + taskTime);
    }

    @Override
    protected void terminated() {
        super.terminated();
        System.out.println("terminated");
    }

    public static void main(String[] args) {
        ThreadPoolExecutor pool = new TimingThreadPool();

        pool.submit(() -> {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        });

        pool.shutdown();
    }
}
