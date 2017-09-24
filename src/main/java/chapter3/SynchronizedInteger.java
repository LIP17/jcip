package chapter3;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * This is thread safe because Java intrinsic lock guarantee a
 * happens-before order, so if get after set is guaranteed to see the change of value.
 *
 * If get is not synchronized with same intrinsic lock, then get may get stale data.
 * */
@ThreadSafe
public class SynchronizedInteger {
    @GuardedBy("this") private int value;

    public synchronized int get() { return value; }
    public synchronized void set(int v) { this.value = v; }
}


