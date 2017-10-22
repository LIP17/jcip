package chapter5;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 */
public class ConcurrentList {
    /**
     * See code, CopyOnWriteArrayList implements immutability by
     * everytime you edit the collection, it will create a new collection and
     * replace the existing one. So you will never have ConcurrentModificationException.
     *
     * */

    /**
     * CopyOnWriteArrayList is used for case when reading is far often than modification.
     *
     * */
    public static void main(String[] args) {
        List<Integer> l = new CopyOnWriteArrayList<>();
        // the underlying array has already changed when you call this.
        l.add(1);
    }
}
