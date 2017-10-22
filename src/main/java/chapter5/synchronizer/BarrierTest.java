package chapter5.synchronizer;


import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;

/**
 */
public class BarrierTest {

    private void testCyclicBarrier() {
        int cpuCount = Runtime.getRuntime().availableProcessors();

        /**
         * You can define the number of threads to wait and what will be execute after
         * all threads arrive barrier in constructor.
         *
         * when you define the count, make sure its equal to the number of threads running,
         * it has same behavior like semaphore, if you define the number less than cpuCount,
         * the barrier may be passed several times, and the finish callable will be called
         * more than once.
         * */

        CyclicBarrier cBarrier = new CyclicBarrier(cpuCount,
                new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("all threads arrived at barrier");
                    }
                });

        Thread[] tGroup = new Thread[cpuCount];
        for (int i = 0; i < cpuCount; i++) {
            tGroup[i] = new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(1000);
                        System.out.println("Thread " + Thread.currentThread().getId() + " is here");
                        try {
                            cBarrier.await();
                        } catch (InterruptedException e) {
                            // if the current thread is interrupted.
                            System.out.println("interrupted");
                        } catch (BrokenBarrierException b) {

                            /**
                             * You can set up await timeout in await method, if the await time out,
                             * or a thread blocked in await interrupted.
                             * */
                            System.out.println("one thread is interrupted");
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            });
        }

        for (int i = 0; i < cpuCount; i++) {
            tGroup[i].start();
        }
    }


    public static void main(String[] args) {
        new BarrierTest().testCyclicBarrier();
    }

}
