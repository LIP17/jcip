package chapter15;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Listing 15.1
 * this is a simulation of CAS implemented by processor. The basic semantics described as:
 * in memory location V, if its current value equals expected value, then swap it to be new value.
 *
 */
@ThreadSafe
public class CASSemantics {
    @GuardedBy("this") private int value;

    public synchronized int get() { return value; }

    public synchronized int compareAndSwap(int expectedValue, int newValue) {
        int oldValue = value;
        if(oldValue == expectedValue) value = newValue;

        return oldValue;
    }

    public synchronized boolean compareAndSet(int expectedValue, int newValue) {
        return (expectedValue == compareAndSwap(expectedValue, newValue));
    }
}
