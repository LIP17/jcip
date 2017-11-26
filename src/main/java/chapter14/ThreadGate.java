package chapter14;

import net.jcip.annotations.GuardedBy;

/**
 * Listing 14.9
 */
public class ThreadGate {

    // CONDITION-PREDICATE: open-since(n) (isOpen || generation > n)
    @GuardedBy("this") private boolean isOpen;
    // use generation counter in case when you only check isOpen, and
    // the gate is open firstly and close rapidly after, if all N threads
    // arrive in the same time, some threads may find the gate is closed
    // by the close operation, but they should pass because of the open before.
    @GuardedBy("this") private int generation;

    public synchronized void close() { isOpen = false; }

    public synchronized void open() {
        ++generation;
        isOpen = true;
        notifyAll();
    }

    // BLOCKS-UNTIL: opened-since(generation on entry)
    public synchronized void await() throws InterruptedException {
        int arrivalGeneration = generation;
        while (!isOpen && arrivalGeneration == generation)
            wait();
    }

}
