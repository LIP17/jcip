package chapter11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * A map with stripped lock
 */
@ThreadSafe
public class StripedMap {
    private static class Node {
        Node next = null;
        Object key = null;
        Object val = null;
    }

    private static final int N_LOCKS = 6;
    @GuardedBy("buckets[n] guarded by locks[n%N_LOCKS]")
    private final Node[] buckets; // each bucket is guarded by corresponding lock, and consist of linked list
    private final Object[] locks;

    public StripedMap(int numOfBuckets) {
        buckets = new Node[numOfBuckets];
        locks = new Object[N_LOCKS];
        for(int i = 0; i < N_LOCKS; i++)
            locks[i] = new Object();
    }

    private final int hash(Object key) {
        return Math.abs(key.hashCode() % buckets.length);
    }

    public Object get(Object key) {
        int hash = hash(key);
        synchronized (locks[hash % N_LOCKS]) {
            for (Node n = buckets[hash]; n != null ; n = n.next) {
                if(n.key.equals(key)) return n.val;
            }
        }
        return null;
    }

    /**
     * Notice when you do clear you take locks in some order, which could have
     * race condition when other threads are adding element to map, which is tolerable
     * in this case when clear or getSize is not a frequent operation for this kind of map.
     * */
    public void clear() {
        for (int i = 0; i < buckets.length; i++) {
            synchronized (locks[i % N_LOCKS]) {
                buckets[i] = null;
            }
        }
    }
}
