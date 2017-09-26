package chapter4;

import chapter2.Widget;
import net.jcip.annotations.GuardedBy;
import java.util.HashSet;
import java.util.Set;
/**
 * Java Monitor Pattern: encapsulate all its mutable state and
 * guards it with the objects' own intrinsic lock, or private lock.
 * */
public interface JavaMonitorPattern {

}

class IntrinsicLock<T> implements JavaMonitorPattern {
    @GuardedBy("this")
    private final Set<T> set = new HashSet<T>();

    public synchronized void add(T t) { set.add(t); }

    public synchronized boolean contains(T t) { return set.contains(t); }
}

/**
 * The benefit of using private lock is, other object access this will
 * never get the intrinsic lock, in case client code doing something bad.
 * */
class PrivateLock implements JavaMonitorPattern {
    private final Object internalLock = new Object();

    @GuardedBy("this")
    private Widget widget;

    void someMethod() {
        synchronized (internalLock) {
            // access widget
        }
    }
}

