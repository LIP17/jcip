package chapter8;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Listing 8.5, 8.6
 *
 * public interface ThreadFactory {
 *     Thread newThread(Runnable r);
 * }
 *
 */
public class ThreadFactoryExample {

    private class MyThread extends Thread {
        private Runnable r;
        private String poolName;

        public MyThread(Runnable runnable, String poolName) {
            this.r = runnable;
            this.poolName = poolName;
        }

        @Override
        public void run() {
            r.run();
            System.out.println("threadpool: " + poolName);
            super.run();
        }
    }

    private class MyThreadFactory implements ThreadFactory {

        private final String poolName;

        public MyThreadFactory(String poolName) {
            this.poolName = poolName;
        }

        public Thread newThread(Runnable runnable) {
            return new MyThread(runnable, poolName);
        }
    }


    public static void main(String[] args) {

        ThreadFactoryExample example = new ThreadFactoryExample();

        ThreadPoolExecutor es = new ThreadPoolExecutor(
                1, 1,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(1),
                example.new MyThreadFactory("testpool"));

        es.submit(() -> System.out.println("runnable"));

        es.shutdown();
    }

}
