package chapter13;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class LockIdiom {

    public void lock() {
        Lock lock  = new ReentrantLock();

        lock.lock();
        try {
            // do some thing
        } finally {
            lock.unlock();
        }
    }

}
