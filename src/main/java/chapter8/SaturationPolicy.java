package chapter8;

import java.util.Comparator;
import java.util.PriorityQueue;
import java.util.concurrent.*;

/**
 */
public class SaturationPolicy {

    private class Task implements Runnable, Comparable<Task> {

        private final int TASK_ID;
        public Task(int n) {
            this.TASK_ID = n;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1 * 1000L);
            } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            System.out.println("Task: " + TASK_ID + ": THREAD _ID" + Thread.currentThread().getId());
        }

        public int getTaskId() { return this.TASK_ID; }

        @Override
        public int compareTo(Task o) {
            return this.getTaskId() - o.getTaskId();
        }
    }


    private void abortPolicyTest() {
        /**
         * When queue is full and all available threads are occupied,
         * when you submit a task, you will get RejectedExecutionException for client to handle.
         * */
        ThreadPoolExecutor es =
                new ThreadPoolExecutor(
                        1, 1,
                        0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());

        es.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());

        es.submit(new Task(1));
        try {
            es.submit(new Task(2));
        } catch(RejectedExecutionException ree) {
            System.out.println(ree.getMessage());
        }

        es.shutdown();
    }

    private void discardPolicyTest() {
        /**
         * silently throw away job when saturation happened.
         * */
        ThreadPoolExecutor es =
                new ThreadPoolExecutor(
                        1, 1,
                        0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());

        es.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());

        es.submit(new Task(1));
        es.submit(new Task(2)); // this will be throw away silently

        es.shutdown();
    }

    private void callerRunPolicyTest() {

        /**
         * This will reject the job and let the caller thread run the execute method,
         * if the main thread is also saturated, the rejection will be thrown to its caller.
         * eg, for http server, the rejection will be thrown to TCP layer and handled there.
         * */
        ThreadPoolExecutor es =
                new ThreadPoolExecutor(
                        1, 1,
                        0L, TimeUnit.MILLISECONDS, new SynchronousQueue<Runnable>());

        es.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());

        es.submit(new Task(1));
        es.submit(new Task(2)); // this will be throw away silentl

        es.shutdown();
    }

    private void discardOldestPolicyTest() {

        /**
         * This will throw the oldest one
         *
         * important, when you use priority queue, the one with highest priority will be thrown away!
         * */
         ThreadPoolExecutor es =
                new ThreadPoolExecutor(
                        1, 1,
                        0L, TimeUnit.MILLISECONDS,
                        new LinkedBlockingQueue<>(1));

         es.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());

         es.submit(new Task(0));
         es.submit(new Task(1)); // will be throw away because when 0 is executed, 2 is inserted.
         es.submit(new Task(2));

         es.shutdown();

    }

    public static void main(String[] args) {

//        new SaturationPolicy().abortPolicyTest();

//        new SaturationPolicy().discardPolicyTest();

//        new SaturationPolicy().callerRunPolicyTest();
        new SaturationPolicy().discardOldestPolicyTest();
    }


}
