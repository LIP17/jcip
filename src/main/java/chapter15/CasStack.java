package chapter15;

import java.util.concurrent.atomic.AtomicReference;

/**
 * Listing 15.6 a non-blocking stack
 */
public class CasStack<E> {
    private static class Node<E> {
        private Node next;
        public final E value;
        Node(E value) { this.value = value; }
    }

    AtomicReference<Node<E>> top = new AtomicReference<>();

    public void push(E item) {
        Node<E> newHead = new Node(item);
        Node<E> oldHead;

        do {
            oldHead = top.get();
            newHead.next = oldHead;
        } while (!top.compareAndSet(oldHead, newHead));
    }

    public E pop() {
        Node<E> oldHead;
        Node<E> newHead;

        do {
            oldHead = top.get();
            if(oldHead == null) return null;
            newHead = oldHead.next;
        } while (!top.compareAndSet(oldHead, newHead));
        return oldHead.value;
    }
}
