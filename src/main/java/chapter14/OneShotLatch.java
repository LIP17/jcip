package chapter14;

import net.jcip.annotations.ThreadSafe;
import java.util.concurrent.locks.*;
/**
 * Listing 14.14 binary latch using AQS
 */
@ThreadSafe
public class OneShotLatch {

    private final Sync sync = new Sync();

    public void signal() { sync.releaseShared(0); }

    public void await() throws InterruptedException {
        sync.acquireInterruptibly(0);
    }

    private class Sync extends AbstractQueuedSynchronizer {
        @Override
        protected int tryAcquireShared(int ignored) {
            // Succeed if latch is open(state == 1), else fail
            return (getState() == 1) ? 1 : -1;
        }

        @Override
        protected boolean tryReleaseShared(int ignored) {
            setState(1);
            return true;
        }
    }
}
