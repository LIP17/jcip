package chapter5.cache;

import com.sun.applet2.preloader.CancelException;

import java.util.concurrent.*;

/**
 * Listing 5.19
 */
public class EfficientCache<A, V> implements Computable<A, V> {

    private final ConcurrentMap<A, Future<V>> cache;

    private final Computable<A, V> c;

    public EfficientCache(Computable<A, V> c) {
        cache = new ConcurrentHashMap<>();
        this.c = c;
    }

    /**
     * @param arg use final so the inner class can refer to it
     * */
    @Override
    public V compute(final A arg) throws InterruptedException {

        /**
         * the reason using while(true) is if it is cancelled
         * we still want to get the value
         * */
        while (true) {
            Future<V> f = cache.get(arg);
            if(f == null) {
                Callable<V> eval = () -> { return c.compute(arg); };
                FutureTask<V> ft = new FutureTask<V>(eval);
                f = cache.putIfAbsent(arg, ft);
                /**
                 * this check seems redundant, but it is in case
                 * you have some duplicate computation. If two threads
                 * calling this method with same arg, it may calculate twice
                 * and the expensive computation will be twice. so check before
                 * you start actually running it will avoid this timing issue.
                 *
                 * */
                if(f == null) { f = ft; ft.run();}
            }

            try {
                return f.get();
            } catch (CancellationException ce) {
                /**
                 * If the future task is cancelled, we still want the
                 * value later
                 * */
                cache.remove(arg);
            } catch (ExecutionException ie) {
                throw launderThrowable(ie.getCause());
            }


        }
    }

    /**
     * Listing 5.13
     * if the throwable is Error, throw it
     * if the throwable is RuntimeException, return it
     * if the throwable is not defined, throw it within IllegalStateException.
     * */
    private static RuntimeException launderThrowable(Throwable t) {
        if(t instanceof RuntimeException)
            return (RuntimeException) t;
        else if (t instanceof Error)
            throw (Error) t;
        else
            throw new IllegalStateException("not checked", t);

    }

    public static void main(String[] args) {
        EfficientCache<String, String> cache =
                new EfficientCache<>((str) -> {
                    Thread.sleep(1000);
                    return "Thread return " + str;
                });
        int availableCore = Runtime.getRuntime().availableProcessors();

        for (int i = 0; i < availableCore ; i++) {
            new Thread(() -> {
                try {
                    System.out.println(cache.compute("123"));
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                }
            }).start();
        }
    }
}
