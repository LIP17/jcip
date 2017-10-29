package chapter7;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Listing 7.17,18,19
 * In order to interrupt a producer-consumer service, we can put
 * a poinson pill on the shared resource, and when the consumer see
 * the poison queue, it can stop.
 *
 * Notice, for multiple producer-consumer part, you have to use multiple
 * poison pills, and the queue used should be unbounded, in case the
 * poison pill put is blocked.
 */
public class InterruptWithPoisonPill {

    private final BlockingQueue<String> bq = new LinkedBlockingQueue();
    private static final String POINSON = "POISON";

    private class ProducerWithPill implements Runnable {
        @Override
        public void run() {
            try {
                while(true) {
                    bq.put("message");
                    Thread.sleep(1 * 1000);
                }
            } catch (InterruptedException ie) { /* fall through to the finally block */ }
            finally {
                while(true) {
                    try {
                        bq.put(POINSON);
                    } catch (InterruptedException ie1) { /* retry, have to do it to exit */ }
                }
            }

        }
    }

    private class ConsumerReadPill implements Runnable {
        @Override
        public void run() {
            while (true) {
                System.out.println("consumer thread");
                String msg = null;
                try {
                    msg = bq.take();
                } catch (InterruptedException ie) { }

                if(msg.equals(POINSON)) { System.out.println("here"); break; }
                else System.out.println(msg);
            }

        }
    }

    public static void main(String[] args) throws InterruptedException {
        InterruptWithPoisonPill service = new InterruptWithPoisonPill();

         ProducerWithPill producer = service.new ProducerWithPill();
         ConsumerReadPill consumer = service.new ConsumerReadPill();

         Thread t1 = new Thread(producer);
         Thread t2 = new Thread(consumer);

         t1.start();
         t2.start();

         try {
             Thread.sleep(10 * 1000);
         } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }

         t1.interrupt();
         t2.join();
    }
}
