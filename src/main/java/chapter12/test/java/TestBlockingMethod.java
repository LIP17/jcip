package chapter12.test.java;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *  Listing 12.3 how to test blocking method.
 */
public class TestBlockingMethod {

    private static final int LOCKUP_DETECT_TIMEOUT_IN_MILLISECOND = 1000;
    // test take blocks for empty collection
    // use LinkedBlockingQueue as example
    void testTakeBlocksWhenEmpty() {
        final BlockingQueue<Integer> bb = new LinkedBlockingQueue<>();

        Thread taker = new Thread() {
            @Override
            public void run() {
                try {
                    int unused = bb.take();
                    fail(); // take should block, so if you arrive here, your blocking failed.
                } catch (InterruptedException ie) {
                    // blocking works!
                }}};


        try {
            taker.start();
            Thread.sleep(LOCKUP_DETECT_TIMEOUT_IN_MILLISECOND);
            taker.interrupt();
            taker.join(LOCKUP_DETECT_TIMEOUT_IN_MILLISECOND);
            assert(!taker.isAlive());
        } catch (InterruptedException ie) {
            fail();
        }

    }

    void fail() { }

    public static void main(String[] args) {
        new TestBlockingMethod().testTakeBlocksWhenEmpty();
    }
}
