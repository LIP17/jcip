package chapter7;

import com.sun.org.apache.bcel.internal.generic.BIPUSH;
import net.jcip.annotations.NotThreadSafe;
import org.omg.PortableInterceptor.INACTIVE;
import org.omg.PortableServer.THREAD_POLICY_ID;

import java.math.BigInteger;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Listing 7.5
 */
public class CancelUsingInterruptionTest {

    private class PrimeProducer extends Thread {
        private final BlockingQueue<BigInteger> queue;

        PrimeProducer(BlockingQueue<BigInteger> queue) {
            this.queue = queue;
        }

        public void run() {
            try {
                BigInteger p = BigInteger.ONE;
                // every time you will check the interruption first
                // which make your cancel call more responsive.
                while (!Thread.currentThread().isInterrupted())
                    queue.put(p = p.nextProbablePrime());
            } catch (InterruptedException ie) {
                // this will catch the interrupted exception in blocking queue
                Thread.currentThread().interrupt();
                System.out.println("bq interrupted exception");
            }
        }

        public void cancel() { interrupt(); }
    }

    // Listing 7.3
    @NotThreadSafe
    private class BrokenPrimeProducer extends Thread {
        private final BlockingQueue<BigInteger> queue;
        private volatile boolean cancelled = false;

        BrokenPrimeProducer(BlockingQueue<BigInteger> q) {
            this.queue = q;
        }

        @Override
        public void run() {
            try {
                BigInteger p = BigInteger.ONE;
                /** the difference here from Listing 7.5
                 * is that this stuff do not use Thread library
                 * and use the flag to read, which will block if
                 * the blocking queue call blocked, and the cancelled
                 * flag may not be read forever.
                */
                while (!cancelled) {
                    queue.put(p = p.nextProbablePrime());
                }
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
        }

        public void cancel() { cancelled = true; }
    }

    public static void main(String[] args) {
//        PrimeProducer pp = new CancelUsingInterruptionTest().new PrimeProducer(new LinkedBlockingQueue<BigInteger>(1));
        BrokenPrimeProducer pp = new CancelUsingInterruptionTest().new BrokenPrimeProducer(new LinkedBlockingQueue<>(1));

        pp.start();

        try {
            Thread.sleep(10000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        pp.cancel();
    }
}
