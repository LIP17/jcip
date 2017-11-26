package chapter15;

import net.jcip.annotations.ThreadSafe;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Listing 15.7 Michael-Scott non blocking queue algorithm
 */
@ThreadSafe
public class CasLinkedQueue<E> {

    private static class Node <E> {
        final E item;
        final AtomicReference<Node<E>> next;

        public Node(E item, Node<E> next) {
            this.item = item;
            this.next = new AtomicReference<>(next);
        }
    }

    private final Node<E> dummy = new Node<E>(null, null);
    private final AtomicReference<Node<E>> head = new AtomicReference<>(dummy);
    private final AtomicReference<Node<E>> tail = new AtomicReference<>(dummy);

    /**
     * insert into linked queue has step,
     *      1. link a new node to current tail
     *      2. move the pointer of tail to new node
     * if step 1 finished but step 2 is not finished, then it is in intermediate state.
     * */
    public boolean put(E item) {
        Node<E> newNode = new Node<>(item, null);
        while (true) {
            Node<E> curTail = tail.get();
            Node<E> tailNext = curTail.next.get();
            if(curTail == tail.get()) { // only when you ensure you saw the current end
                if(tailNext != null) {
                    /** Queue in intermediate state, other threads is doing insert, and not finished,
                     help them by advancing 1 node*/
                    tail.compareAndSet(curTail, tailNext);
                } else {
                    /** in quiescent state, try inserting new node */
                    if(curTail.next.compareAndSet(null, newNode)) {
                        tail.compareAndSet(curTail, newNode);
                        return true;
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        CasLinkedQueue queue = new CasLinkedQueue();

        queue.put(new Node<Integer>(1, null));
    }
}
