package chapter12.test.java;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Listing 12.5
 */
public class TestThreadSafety {

    /**
     * The thread pool size should be larger than # of CPUs, to
     * make sufficient condition for any kind of thread issue happen
     * */
    private static final ExecutorService pool
            = Executors.newCachedThreadPool();

    /**
     * to verify no race condition, putSum and takeSum should be same at the end.
     * */
    private final AtomicInteger putSum = new AtomicInteger(0);
    private final AtomicInteger takeSum = new AtomicInteger(0);
    private final CyclicBarrier barrier;
    /**
     * this is what we are going to test, use blocking queue as an example
     * */
    private final BlockingQueue<Integer> bb;
    private final int nTrials, nPairs;

    public static void main(String[] args) {
        new TestThreadSafety(10, 10, 100000).test(); // sample parameters
        pool.shutdown();
    }

    TestThreadSafety(int capacity, int npairs, int ntrials) {
        this.bb = new LinkedBlockingQueue<>(capacity);
        this.nTrials = ntrials;
        this.nPairs = npairs;

        // notice the size is not
        this.barrier = new CyclicBarrier(npairs * 2 + 2);
    }
    void test() {
        try {
            for (int i = 0; i < nPairs; i++) {
                pool.execute(new Producer());
                pool.execute(new Consumer());
            }
            /**
             * this wait is for waiting all thread start,
             * to make thread interleaving as much as possible.
             * */
            barrier.await(); // wait for all threads to be ready

            /**
             *
             * wait for all pairs finish their loop
             * */
            barrier.await(); // wait for all threads to finish
            assert(putSum.get() == takeSum.get());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Listing 2.16 Producer and consumer for testing.
     * */
    class Producer implements Runnable {
        public void run() {
            try {
                int seed = (this.hashCode() ^ (int)System.nanoTime());
                int sum = 0;
                barrier.await();
                for (int i = nTrials; i > 0; --i) {
                    bb.put(seed);
                    sum += seed;
                    seed = xorShift(seed); // generate a random number
                }
                putSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    class Consumer implements Runnable {
        public void run() {
            try {
                barrier.await();
                int sum = 0;
                for (int i = nTrials; i > 0; --i) {
                    sum += bb.take();
                }
                takeSum.getAndAdd(sum);
                barrier.await();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Listing 12.4 a medium-quality random number generator.
     * */
    private static int xorShift(int y) {
        y ^= (y << 6);
        y ^= (y >>> 21);
        y ^= (y << 7);
        return y;
    }

}
