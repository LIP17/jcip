package chapter3;

import net.jcip.annotations.NotThreadSafe;

/**
 * Listing 3.14, Listing 3.15
 * This may not be correct if not properly published.
 *
 * A safe class may become not thread-safe if published unsafely
 *
 * @LINK https://stackoverflow.com/questions/46385877/why-this-class-may-not-be-publish-safely
 * */
@NotThreadSafe
public class UnsafePublication {

    public Holder holder;

    public void initialize() {
        holder = new Holder(1);
    }
}

/**
 * This class looks safe for instantiate, but may become not thread safe
 * when published un-safely.
 * */
class Holder {

    private int n;

    public Holder(int n) {
        this.n = n;
    }
    /**
     * [Footnote 16]：While it may seem that field values set in a constructor are the first values written to those fields and therefore that there are no "older"
     values to see as stale values, the Object constructor first writes the default values to all fields before subclass constructors run. It is therefore
     possible to see the default value for a field as a stale value.
     * */
    public void assertSanity() {
        if (n != n) throw new AssertionError("This statement is false.");
    }
}

class RunTest {

    public static void main(String[] args) {
        UnsafePublication up = new UnsafePublication();
        up.initialize();
        new Thread(() -> up.holder.assertSanity()).start();
    }

}