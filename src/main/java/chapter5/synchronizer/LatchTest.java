package chapter5.synchronizer;

import java.util.concurrent.CountDownLatch;

public class LatchTest {

    /**
     * Latch act as a gate, until latch reaches the terminal state, all thread will be waiting
     * and no threads will pass. After the gate is open it cannot be changed again.
     * */

    private void usePattern() {
        // for count down latch, give the initial number, and when it is 0, it will be open
        CountDownLatch cdLatch = new CountDownLatch(1);

        Thread t = new Thread() {
            @Override
            public void run() {
                try {
                    cdLatch.await();
                    System.out.println("2. thread go through latch after count down to 0");
                } catch (InterruptedException e) {}
            }
        };
        t.start();

        System.out.println("the order will be guaranteed by the latch");
        System.out.println("1.blocking by latch");

        cdLatch.countDown();
    }

    // Listing 5.11
    public long timeTasks(int nThreads, final Runnable task) throws InterruptedException {
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for (int i = 0; i < nThreads; i++) {
            Thread t = new Thread() {
                @Override
                public void run() {
                    try {
                        startGate.await();
                        try {
                            task.run();
                        } finally {
                            endGate.countDown();
                        }
                    }  catch (InterruptedException ignored) {}
                }
            };
            t.start();
        }

        long start = System.currentTimeMillis();
        startGate.countDown();
        endGate.await();
        long end = System.currentTimeMillis();
        return end - start;
    }

    public static void main(String[] args) throws InterruptedException {
        System.out.println(new LatchTest().timeTasks(8, new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10000000; i++) {
                    int j = i * i;
                }
            }
        }));

        new LatchTest().usePattern();
    }
}
