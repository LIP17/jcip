package chapter7;

import java.util.concurrent.*;

/**
 * Listing 7.10
 */
public class CancelOnFuture {
    private static final ExecutorService ex = Executors.newCachedThreadPool();

    public static void timedRun(Runnable r, long timeout, TimeUnit unit) throws InterruptedException {
        Future<?> task = ex.submit(r);

        try {
           task.get(timeout, unit);
        } catch (TimeoutException te) {
            // for time out, the task will be cancelled in finally
        } catch (ExecutionException ee) {
            // exception thrown from the actual task, rethrow it out
            throw launderThrowable(ee);
        } finally {
            // Good practice: cancel the result of a future if it time out
            task.cancel(true);
        }

    }

    private static RuntimeException launderThrowable(Throwable t) {
        if(t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("not checked", t);

    }
}
