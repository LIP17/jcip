package chapter12.test.java;


import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Listing 12.7. Test is heap is leaking when using bounded buffer.
 */
public class TestResourceLeak {

    class Big {
        double[] data = new double[100000];
    }

    void testLeak() throws InterruptedException {
        int CAPACITY = 100;
        BlockingQueue<Big> bb = new LinkedBlockingQueue<>(CAPACITY);
        long heapSize1 = Runtime.getRuntime().freeMemory();
        for (int i = 0; i < CAPACITY; i++) {
            bb.put(new Big());
        }
        for (int i = 0; i < CAPACITY; i++) {
            bb.take();
        }
        long heapSize2 = Runtime.getRuntime().freeMemory();
        assert(heapSize1 == heapSize2);
    }

    public static void main(String[] args) throws InterruptedException {
        new TestResourceLeak().testLeak();
    }

}
