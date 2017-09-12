package chapter2;

/**
 * Created by lip on 9/10/17.
 */
public class Widget {
    public synchronized void doSomething() {

    }
}

/**
 * Intrinsic Lock: `synchronized` There are two types, instance lock or static lock,
 * instance lock will block all access to any instance methods, but will not lock for non-sync or static method,
 * static lock is similar.
 *
 * Notice, syncronized is on object level, so if there are two synchronized methods, only one can acquire the lock
 * per instance.
 *
 * Intrinsic lock guerantee a happen-before logic
 *
 * */

/**
 * Intrinsic lock is reentrant, which means lock is per-thread.
 *
 * Reentrant lock is implemented by counter. If the counter is 0, means any thread
 * can acquire the lock. If same thread acquire the lock again, it will increment lock counter.
 * After using it, it will be back to 0 again.
 * */
class LoggingWidget extends Widget{
    public synchronized void doSomething() {
        System.out.println("subclass do something");
        super.doSomething();
    }
}

