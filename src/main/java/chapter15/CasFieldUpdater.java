package chapter15;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/**
 * Listing 15.8
 */
public class CasFieldUpdater {
    private class Node<E> {
        private final E item;
        private volatile Node<E> next;

        public Node(E item) { this.item = item; }
    }

    private static AtomicReferenceFieldUpdater<Node, Node> nextUpdater
            = AtomicReferenceFieldUpdater.newUpdater(Node.class, Node.class, "next");

}
