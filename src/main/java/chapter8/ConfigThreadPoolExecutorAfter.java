package chapter8;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 */
public class ConfigThreadPoolExecutorAfter {
    private class Task implements Runnable, Comparable<Task> {

        private final int TASK_ID;
        public Task(int n) {
            this.TASK_ID = n;
        }

        @Override
        public void run() {
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException ie) { Thread.currentThread().interrupt(); }
            System.out.println("Task: " + TASK_ID + ": THREAD_ID: " + Thread.currentThread().getId());
        }

        public int getTaskId() { return this.TASK_ID; }

        @Override
        public int compareTo(Task o) {
            return this.getTaskId() - o.getTaskId();
        }
    }

    private void unconfigurablePool() {
        ExecutorService es = Executors.newCachedThreadPool();

        /**
         * limit the service to be unconfigurable.
         * */
        ExecutorService unconfigService = Executors.unconfigurableExecutorService(es);

        if(unconfigService instanceof ThreadPoolExecutor)
            ((ThreadPoolExecutor) unconfigService).setCorePoolSize(100);
        else
            System.out.println("this class is wrapped and cannot be config_ed");
    }

    private void configPool() {
        ExecutorService es = Executors.newSingleThreadExecutor();

        es.submit(new Task(1));
        es.submit(new Task(2));

        es.submit(new Task(2));
        es.submit(new Task(2));
        es.submit(new Task(2));
        es.submit(new Task(2));
        es.submit(new Task(2));

        if(es instanceof ThreadPoolExecutor) {
            ((ThreadPoolExecutor) es).setMaximumPoolSize(10);
            ((ThreadPoolExecutor) es).setCorePoolSize(10);
        }
        else
            System.out.println();

        es.submit(new Task(2));
        es.submit(new Task(2));
        es.submit(new Task(2));


        es.shutdown();
    }


    public static void main(String[] args) {

//        new ConfigThreadPoolExecutorAfter().unconfigurablePool();
        new ConfigThreadPoolExecutorAfter().configPool();
    }

}
