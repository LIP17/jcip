package chapter5;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

public class BlockingDequeTest {

    public static void main(String[] args) {
        BlockingDeque<Integer> bdq = new LinkedBlockingDeque<>();

        try {
            bdq.put(1);

            // work stealing, if your thread is empty, try steal from other thread!
            bdq.takeFirst();
            bdq.takeLast();
        } catch (InterruptedException e) {

        }
    }

}
