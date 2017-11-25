package chapter13;

import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * Design consideration:
 *
 *  1. release preference: when writer release lock, and both readers and writers are queued up, which should be preferred?
 *  2. reader barging: when readers held lock but there are waiting writers, will newly arrived reader get the lock or writer?
 *  3. re-entrancy: is this rwlock reentrant?
 *  4. downgrading: if a writer hold write lock, can it acquire read lock without releasing write lock? This will downgrade writer
 *                  to reader and block other writer modify the guarded resources.
 *  5. upgrade:     if a reader upgrade to writer? (deadlock prone, rarely do this)
 *
 *  Listing 13.7
 */
public class ReadWriteLockDesign {

    private final Map<String, String> map;
    private final ReadWriteLock rlock = new ReentrantReadWriteLock();
    private final Lock r = rlock.readLock();
    private final Lock w = rlock.writeLock();


    public ReadWriteLockDesign(Map<String, String> map) {
        this.map = map;
    }

    public String put(String key, String value) {
        w.lock();
        try {
            return map.put(key, value);
        } finally {
            w.unlock();
        }
    }

    public String get(String key) {
        r.lock();
        try {
            return map.get(key);
        } finally {
            r.unlock();
        }
    }

}
