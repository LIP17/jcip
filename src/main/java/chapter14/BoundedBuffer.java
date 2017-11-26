package chapter14;

/**
 * Listing 14.6 Bounded Buffer using condition queues
 */
public class BoundedBuffer<V> extends BaseBoundedBuffer<V> {

    public BoundedBuffer(int size) { super(size); }

    // BLOCKS-UNTIL: not full
    public synchronized void put(V v) throws InterruptedException {
        while (isFull()) wait();

        doPut(v);
        notifyAll();
    }

    // BLOCK-UNTIL: not empty
    public synchronized V take() throws InterruptedException {
        while (isEmpty()) wait();

        V v = doTake();
        notifyAll();
        return v;
    }
}
