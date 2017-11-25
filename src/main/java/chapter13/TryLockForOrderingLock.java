package chapter13;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Listing 13.3, implementing same functionality of listing 10.3
 */
public class TryLockForOrderingLock {
    private class Account {
        Lock lock = new ReentrantLock();
    }

    public boolean transferMoney(Account from,
                                 Account to)
            throws InterruptedException{

        long fixedDelay = 1L;
        long randMod = 1L;
        long maximumWait = 1L;
        long stopTime = System.nanoTime() + maximumWait;

        while(true) {
            if (from.lock.tryLock()) {
                try {
                    if (to.lock.tryLock()) {
                        try {
                            // transfer money logic
                            return true;
                        } finally {
                            to.lock.unlock();
                        }
                    }
                } finally {
                    from.lock.unlock();
                }

                if (System.nanoTime() > stopTime)
                    return false;

                Thread.sleep(fixedDelay + randMod);

            }
        }
    }

}
