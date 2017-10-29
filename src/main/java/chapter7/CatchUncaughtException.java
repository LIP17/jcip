package chapter7;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Listing 7.23, 7.25
 */
public class CatchUncaughtException extends Thread{

    /**
     * use logger to log the runtime exception
     * */
    private class UEHLogger implements Thread.UncaughtExceptionHandler {
        @Override
        public void uncaughtException(Thread t, Throwable e) {
            Logger logger = Logger.getAnonymousLogger();
            logger.log(Level.SEVERE, "Thread terminated with Exception", e);
        }
    }

    @Override
    public void run() {
        Throwable thrown = null;
        try {
            while (!isInterrupted()) ;

        } catch (Throwable e) {
            thrown = e;
        } finally {
            handleUncaughtException(thrown);
        }
    }

    public void handleUncaughtException(Throwable e) {

    }
}
