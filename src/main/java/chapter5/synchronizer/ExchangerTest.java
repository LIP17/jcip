package chapter5.synchronizer;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.Exchanger;

/**
 */
public class ExchangerTest {
    private class QueueProducer implements Runnable {

        private Exchanger<Queue> exchanger;
        private Queue<Integer> queue;
        private int maxSize;

        QueueProducer(Exchanger ex, int queueSize) {
            this.exchanger = ex;
            this.maxSize = queueSize;
            this.queue = new LinkedList<>();
        }


        @Override
        public void run() {
            for (int i = 0; i < 2 * maxSize; i++) {
                queue.offer(i);
                if(queue.size() == maxSize) {
                    try {
                        this.queue = this.exchanger.exchange(this.queue);
                    } catch (InterruptedException ie) {

                    }
                }
            }
        }
    }

    private class QueueConsumer implements Runnable {

        private Exchanger<Queue> exchanger;
        private Queue<Integer> queue;

         QueueConsumer(Exchanger ex) {
            this.exchanger = ex;
            this.queue = new LinkedList<>();
        }

        @Override
        public void run() {
            while (true) {

                if(queue.isEmpty()) {
                    try {
                        this.queue = this.exchanger.exchange(this.queue);
                     } catch (InterruptedException ie) {

                    }
                }
                while(!queue.isEmpty()) {
                    System.out.println(queue.poll());
                }
            }
        }
    }

    public static void main(String[] args) {
        ExchangerTest et = new ExchangerTest();
        Exchanger ex = new Exchanger();
        QueueProducer qp = et.new QueueProducer(ex, 3);
        QueueConsumer qc = et.new QueueConsumer(ex);

        new Thread(qp).start();
        new Thread(qc).start();
    }


}
