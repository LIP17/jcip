package chapter5;

import net.jcip.annotations.ThreadSafe;

import java.lang.reflect.Executable;
import java.util.Comparator;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.PriorityBlockingQueue;
import java.util.concurrent.SynchronousQueue;

public class BlockingQueueTest {

    public static void main(String[] args) {

        BlockingQueue<Integer> blockingQueue = new LinkedBlockingDeque<>(10);

        try {
            // blocking queue (LinkedBlockingQueue and ArrayBlockingQueue) will
            // block until the conditional variable meet the condition (put if not full, take if not empty)
            blockingQueue.put(1);
            blockingQueue.take();

            // offer wont block, and will return boolean on whether the capacity limit is broken when insert this element.
            blockingQueue.offer(1);
            // also, poll wont block like offer, if queue is empty it will return null object
            blockingQueue.poll();
        } catch (InterruptedException e) {
            // Listing 5.10: you have to do something with the interrupt, do never ignore the
            // interrupted exception
            Thread.currentThread().interrupt();
        }

        BlockingQueue<Integer> pqBlockingQueue = new PriorityBlockingQueue<>(10, new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o2 - o1;
            }
        });

        try {
            pqBlockingQueue.put(2);
            pqBlockingQueue.put(1);
            assert(pqBlockingQueue.take() == 1);

        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        BlockingQueue<Integer> sq = new SynchronousQueue<>();
        try {
            // synchronous queue do not have any capacity, so it is not a queue actually!

            // the put method will block until take method is called, so in this case everytime you run
            // this program it will block forever.
            sq.put(1);
        } catch (Exception e) {
            Thread.currentThread().interrupt();
        }
    }

}
