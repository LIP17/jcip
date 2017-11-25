package chapter13;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 */
public class TimedLock {

    Lock lock = new ReentrantLock();

    public boolean tryTimedLocking(long timeOut, TimeUnit unit)
                                    throws InterruptedException {
        if(!lock.tryLock(unit.toNanos(timeOut), TimeUnit.NANOSECONDS)) {
            return false;
        }

        try {
            // do something before
            return true;
        } finally {
            lock.unlock();
        }
    }
}
