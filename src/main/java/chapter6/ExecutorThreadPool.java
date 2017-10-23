package chapter6;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * In what thread will tasks be executed?
 * In what order should tasks be executed?
 * How many tasks concurrently?
 * How many tasks queued?
 * If tasks have to be executed if system overload, how to pick victim?
 * What actions should be taken before and after execution?
 *
 */
public class ExecutorThreadPool {

    private void testToRunInService(ExecutorService es, int numOfTasks) {
        for (int i = 0; i < numOfTasks; i++) {
            es.submit(() -> {
               System.out.println("Thread id: " + Thread.currentThread().getId());
            });
        }

    }

    private void withFixedThreadPool(int numOfTasks) {
        /**
         * FixedThreadPool: the max number of threads is fixed. If
         * any of the threads died in between , it will create a new one for it.
         *
         * This comes with an unbounded queue for task queueing.
         * */
        ExecutorService es = Executors.newFixedThreadPool(2);
        try {
            testToRunInService(es, numOfTasks);
        } finally {
            es.shutdown();
        }
    }

    private void withCachedThreadPool(int numOfTasks) {
        /**
         * CachedThreadPool: A cached thread pool has flexibility to reap idle threads,
         * and add new threads, but there is no bound for the threads pool size
         *
         * */

        ExecutorService es = Executors.newCachedThreadPool();

        try {
            testToRunInService(es, numOfTasks);
        } finally {
            es.shutdown();
        }

    }

    private void withSingleThreadPool(int numOfTasks) {

        /**
         * SinglethreadPool is only one thread and will be replaced if this
         * thread is dead.
         *
         * Tasks are guaranteed to be processed sequentially according to the order imposed
         * by task queue.
         * */
        ExecutorService es = Executors.newSingleThreadExecutor();

        try {
            testToRunInService(es, numOfTasks);
        } finally {
            es.shutdown();
            System.out.println("is executorservice shutdown? " + es.isShutdown());
            while (!es.isTerminated()) ;


            System.out.println("is es terminated? " + es.isTerminated());
        }
    }

    private void withScheduledThreadPool(int numOfTasks) {
        ScheduledExecutorService es = Executors.newScheduledThreadPool(2);

        /**
         * initial delay is the delay after start program
         * delay is the time interval between tasks
         * */

        es.scheduleWithFixedDelay(() -> {
            System.out.println("" + Thread.currentThread().getId());
        }, 10000, 100, TimeUnit.MILLISECONDS);

    }

    private void withEsShutdownNow(int numOfTasks) {
        /**
         * When you call shutdown now, you get a list of outstanding
         * tasks that all still pending.
         * */
        ExecutorService es = Executors.newSingleThreadExecutor();

        try {
            testToRunInService(es, numOfTasks);
        } finally {
            List<Runnable> outstandingPending = es.shutdownNow();
            System.out.println(outstandingPending.size());
        }
    }


    public static void main(String[] args) {
//        new ExecutorThreadPool().withFixedThreadPool(100);
//        new ExecutorThreadPool().withCachedThreadPool(100);
//        new ExecutorThreadPool().withSingleThreadPool(100);
        new ExecutorThreadPool().withScheduledThreadPool(100);
//        new ExecutorThreadPool().withEsShutdownNow(100);
    }

}
