package chapter8;

import chapter7.InterruptWithPoisonPill;
import net.jcip.annotations.NotThreadSafe;

import java.util.concurrent.*;

/**
 * This is a classic thread starvation deadlock, there is a single thread pool,
 * submit the RenderPageTask it will take up the would thread pool, but the
 * task submission for header and footer will be blocked by this submission, and
 * the RenderPageTask will also be blocked by not started rendering of header and footer.
 *
 */

@NotThreadSafe
public class ThreadStarvationDeadLock {

    ExecutorService es = Executors.newSingleThreadExecutor();

    public class RenderPageTask implements Callable<String> {
        public String call() throws Exception {
            Future<String> header, footer;
            header = es.submit(new Callable<String>() {
                @Override
                public String call() { return "header"; }
            });

            footer = es.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return "footer";
                }
            });
            String page = header.get() + footer.get();
            return page;
        }
    }

    public static void main(String[] args) {

        ThreadStarvationDeadLock deadLock = new ThreadStarvationDeadLock();

        Callable renderPage = deadLock.new RenderPageTask();

        Future<String> page = deadLock.es.submit(renderPage);

        try {
            Thread.sleep(10 * 1000);
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        }

        try {
            System.out.println(page.get());
        } catch (InterruptedException ie) {
            Thread.currentThread().interrupt();
        } catch (ExecutionException ee) {
            System.out.println("exe exception");
        }

        deadLock.es.shutdownNow();

    }
}
