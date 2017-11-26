package chapter15;

import java.util.concurrent.*;

/**
 */
public class CASCounter {

    private final CASSemantics value = new CASSemantics();

    public int getValue() {
        return value.get();
    }

    public int increment() {
        int v;
        do {
            v = value.get();
        } while (v != value.compareAndSwap(v, v + 1));

        return v + 1;
    }

    public static void main(String[] args) throws InterruptedException {
        final CASCounter counter = new CASCounter();

        final int numThreads = 20;

        ExecutorService es = Executors.newFixedThreadPool(numThreads);

        final CyclicBarrier barrier = new CyclicBarrier(numThreads, () -> System.out.println("all threads arrive"));

        for (int i = 0; i < numThreads; i++) {
            es.submit(new Runnable() {
                @Override
                public void run() {
                    try {
                        barrier.await();
                    } catch (Throwable t) {
                        Thread.currentThread().interrupt();
                    }
                    System.out.println(counter.increment());
                }
            });
        }

        Thread.sleep(10 * 1000);
        es.shutdown();
    }
}
