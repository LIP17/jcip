package chapter5.synchronizer;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * Listing 5.12
 */
public class FutureTest {
    private class Info {
        private int info;
        Info(int info) {
            this.info = info;
        }
        int getInfo() {
            return this.info;
        }
    }

    private class DataLoadException extends Exception {

    }

    private Info loadProductInfo() throws DataLoadException {
        // this may take a long time to run, so
        // do not evaluate in real time, just wait until we need it
        // and then evaluate by FutureTask.
        try {
            Thread.sleep(100);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
        return new Info(new Random().nextInt(100));
    }


    private final FutureTask<Info> future =
        new FutureTask<Info>(new Callable<Info>() {
            @Override
            public Info call() throws DataLoadException {
                return loadProductInfo();
            }
        });

    Thread t = new Thread(future);

    public void start() {
        t.start();
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

    public Info get() throws DataLoadException, InterruptedException {
        try {
            // when you call future.get, if the state is not DONE,
            // it will be blocked by conditional variable, so you have
            // to submit it to a thread or executor to start it.
            t.start();
            return future.get();
        } catch (Exception e) {
            // when you call get, the Callable could throw
            // checked or unchecked exception or error, so
            // you have to handle all of them
            Throwable cause = e.getCause();
            if(cause instanceof DataLoadException) {
                throw (DataLoadException) cause;
            } else {
                throw launderThrowable(cause);
            }
        }
    }

    public static void main(String[] args) {
        FutureTest ft = new FutureTest();
        try{
            System.out.println(ft.get().getInfo());
        } catch (Exception e) {

        }
    }
}
