package chapter7;

import net.jcip.annotations.GuardedBy;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 *
 * Listing 7.15
 */
public class LogServiceWithShutdown {
    private final BlockingQueue<String> queue;
    private final LoggerThread loggerThread;
    private final PrintWriter writer;
    /** this decides whether you can still insert to blocking queue or not.
     * When you call shutdown, but the blocking queue put method is still blocking,
     * you can never correctly interrupt it, so you have to use this flag, and atomically
     * access to it every time in case this race condition happened.
     */
    @GuardedBy("this") private boolean isShutdown;
    /**
     * for graceful shutdown, want to log all non-written message and then shutdown,
     * this is the count for all non-written message.
     * */
    @GuardedBy("this") private int reservations;

    public LogServiceWithShutdown() throws IOException {
        queue = new LinkedBlockingQueue<>();
        writer = new PrintWriter(new File("logger.log"));
        loggerThread = new LoggerThread();
    }


    public void start() { loggerThread.start(); }

    public void stop() {
        synchronized (this) { isShutdown = true; }
        loggerThread.interrupt();
    }

    public void log(String msg) throws InterruptedException {
        synchronized (this) {
            if(isShutdown) throw new InterruptedException("Service shut down.");
            ++reservations;
        }

        queue.put(msg);
    }


    private class LoggerThread extends Thread {
        @Override
        public void run() {
            try {
                while (true) {
                    try {
                        synchronized (LogServiceWithShutdown.this) {
                            if(isShutdown && reservations == 0) break;
                        }
                        String msg = queue.take();
                        synchronized (LogServiceWithShutdown.this) {
                            // have to sync this counter for the shutdown mechanism.
                            --reservations;
                        }
                        writer.println(msg);
                    } catch (InterruptedException ie) { /** retry later. */ }
                }
            } finally {
                writer.close();
            }
        }
    }
}
