package chapter6;

import java.util.concurrent.*;

/**
 * Listing 6.11
 */
public class CallableAndFuture<V> {

    /**
     * The only method defined in callable interface is call,
     * compared with Runnable, it can throw exception and return values
     * */
    Callable<V> c = new Callable<V>() {
        @Override
        public V call() throws Exception {
            return null;
        }
    };

    Future<V> f = new Future<V>() {
        @Override
        public boolean cancel(boolean mayInterruptIfRunning) {
            return false;
        }

        @Override
        public boolean isCancelled() {
            return false;
        }

        @Override
        public boolean isDone() {
            return false;
        }

        /**
         * get will block if the task is not completed,
         * if the task is complete, it will either return value, or throw exception
         *
         * @Exception InterruptedException this will throw out when the job is interrupted
         * @Exception ExecutionException the underlying exception is wrapped inside, use getCause()
         *            to get the inside exception.
         *
         * */
        @Override
        public V get() throws InterruptedException, ExecutionException {
            return null;
        }

        @Override
        public V get(long timeout, TimeUnit unit) throws InterruptedException, ExecutionException, TimeoutException {
            return null;
        }
    };
}

