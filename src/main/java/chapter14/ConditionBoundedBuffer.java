package chapter14;

import net.jcip.annotations.GuardedBy;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * circular buffer implementation using Condition and Lock
 */
public class ConditionBoundedBuffer<T> {

    protected final Lock lock = new ReentrantLock();
    // CONDITION PREDICATE: notFull(cound < item.length)
    private final Condition notFull = lock.newCondition();
    // CONDITION PREDICATE: notEmpty(count > 0)
    private final Condition notEmpty = lock.newCondition();

    private static final int BUFFER_SIZE = 100;
    @GuardedBy("lock")
    private final T[] items = (T[]) new Object[BUFFER_SIZE];
    @GuardedBy("lock") private int tail, head, count;

    // BLOCKS-UNTIL: not full
    public void put(T t) throws InterruptedException {
        lock.lock();
        try {
            while (count == items.length) notFull.await();
            items[tail] = t;
            if(++tail == items.length) tail = 0;
            ++count;
            notEmpty.signal();  // do not have to notifyAll, which is more efficient
        } finally {
            lock.unlock();
        }
    }

    // BLOCKS-UNTIL: not empty
    public T take() throws InterruptedException {
        lock.lock();

        try {
            while (count == 0) notEmpty.await();
            T t = items[head];
            items[head] = null;
            if(++head == items.length) head = 0;
            --count;
            notFull.signal();
            return t;
        } finally {
            lock.unlock();
        }
    }
}
