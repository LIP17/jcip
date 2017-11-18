package chapter12.test.java;

import java.util.concurrent.CyclicBarrier;

/**
 * Listing 12.11 a companion timer for using cyclic barrier
 */
public class BarrierTimer implements Runnable {

    private boolean started;
    private long startTime, endTime;

    public synchronized void run() {
        long t = System.nanoTime();
        if(!started) {
            started = true;
            startTime = t;
        } else {
            endTime = t;
        }
    }

    public synchronized void clear() {
        started = false;
    }

    public synchronized long getTime() {
        return endTime - startTime;
    }

    public static void main(String[] args) {
        // barrier timer is the barrier action to get time
        CyclicBarrier cb = new CyclicBarrier(Runtime.getRuntime().availableProcessors(), new BarrierTimer());
    }

}
