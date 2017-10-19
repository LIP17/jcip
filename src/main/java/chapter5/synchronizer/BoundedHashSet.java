package chapter5.synchronizer;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Semaphore;

/**
 */
public class BoundedHashSet<T> {
    private final Set<T> set;
    private final Semaphore semaphore;

    public BoundedHashSet(int size) {
        this.set = Collections.synchronizedSet(new HashSet<T>());
        semaphore = new Semaphore(size);
    }

    public boolean add(T t) throws InterruptedException {
        semaphore.acquire();
        boolean wasAdded = false;
        try {
            wasAdded = set.add(t);
            return wasAdded;
        }  finally {
            if(!wasAdded) {
                semaphore.release();
            }
        }
    }

    public boolean remove(T t) {
        boolean wasRemoved = set.remove(t);
        if(wasRemoved) {
            semaphore.release();
        }
        return  wasRemoved;
    }
}
