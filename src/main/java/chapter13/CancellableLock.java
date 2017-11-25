package chapter13;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 */
public class CancellableLock {

    Lock lock = new ReentrantLock();

    public boolean cancellableLock() throws InterruptedException {
        lock.lockInterruptibly();
        try {
            return cancellableTask();
        } finally {
            lock.unlock();
        }
    }

    private boolean cancellableTask() throws InterruptedException {
        return false;
    }

}
