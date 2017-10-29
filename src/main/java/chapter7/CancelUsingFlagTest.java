package chapter7;

import net.jcip.annotations.GuardedBy;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Listing 7.1
 * You can use external call to stop a thread, but if you have a blocking
 * queue instead of list, and you got blocked, that thread will never be cancelled
 * because it has no time to check flag.
 */

public class CancelUsingFlagTest {
    private class PrimeGenerator implements Runnable {

        @GuardedBy("this")
        private final List<BigInteger> primes = new ArrayList<>();
        private volatile boolean cancelled;

        @Override
        public void run() {
            BigInteger p = BigInteger.ONE;
            while(!cancelled) {
                p = p.nextProbablePrime();
                synchronized (this) {
                    primes.add(p);
                }
            }
        }

        public void cancel() { cancelled = true; }

        public synchronized List<BigInteger> get() { return new ArrayList<BigInteger>(primes); }
    }

    public static void main(String[] args) {
        ExecutorService es = Executors.newFixedThreadPool(1);

        PrimeGenerator pg = new CancelUsingFlagTest().new PrimeGenerator();

        es.submit(pg);

        try {
            Thread.sleep(1000);
            pg.cancel();
        } catch (InterruptedException ie) {
          Thread.currentThread().interrupt();
        } finally {
            es.shutdown();
        }

        List<BigInteger> l = pg.get();

        for(BigInteger b: l) {
            System.out.println(b);
        }

    }
}
