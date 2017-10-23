package chapter6;

import java.util.Comparator;
import java.util.concurrent.*;

/**
 * Using CompletionService, which implements blocking queue and
 * executor in one class
 */

public class QueueingFutureTest {

    public static void main(String[] args) {

        ExecutorService es = Executors.newFixedThreadPool(2);
        /**
         *
         * notice, when the initial capacity is less than the for loop number
         * below, it will lead to problem, the number is not in order!
         *
         * it is tricky to find because if the number of job finished is larger than
         * the queue size, some tasks could blocked by the queue until you poll some jobs
         * from the queue to meet the size limit. And in this case it will only happen when you
         * actually call the take method on CompletionService.
         *
         * */
        BlockingQueue<Future<Integer>> bq = new PriorityBlockingQueue(1, new Comparator<Future<Integer>>(){
            @Override
            public int compare(Future o1, Future o2) {
                try {
                    return (int)o1.get() - (int)o2.get();
                } catch (InterruptedException ie) {

                } catch (ExecutionException ee) {

                }

                return 0;
            }
        });


        /**
         * es: the underlying executor service
         * bq: a blocking queue, when job is done, it will be put into
         * */
        CompletionService cs = new ExecutorCompletionService(es, bq);

        for (int i = 0; i < 100; i++) {
            final Integer c = i;
            cs.submit(() -> {
                return c;
            });
        }

        for (int i = 0; i < 100; i++) {
            try {
                System.out.println(bq.take().get());
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            } catch (ExecutionException ee) {

            }
        }

        es.shutdownNow();
    }
}
