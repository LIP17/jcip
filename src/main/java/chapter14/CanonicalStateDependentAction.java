package chapter14;

import java.util.concurrent.locks.Lock;

/**
 * When using condition waits (Object.wait or Condition.await):
 *      1. Always have a condition predicate—some test of object state that must hold before proceeding;
 *      2. Always test the condition predicate before calling wait, and again after returning from wait;
 *      3. Always call wait in a loop;
 *      4. Ensure that the state variables making up the condition predicate are guarded by the lock associated with the condition queue;
 *      5. Hold the lock associated with the the condition queue when calling wait, notify, or notifyAll; and
 *      6. Do not release the lock after checking the condition predicate but before acting on it.
 */
public class CanonicalStateDependentAction {

    // just a place holder for code compile
    private Lock lock;
    //
    private boolean conditionPredicate() {
        return false;
    }

    void stateDependentMethod() throws InterruptedException {
        // condition predicate must be guarded by lock
        synchronized (lock) {
            while(!conditionPredicate()) wait();

            // object is now in desired state
        }
    }
}
