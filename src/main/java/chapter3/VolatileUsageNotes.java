package chapter3;

public class VolatileUsageNotes {
    // volatile
    /**
     * volatile type promise visibility, but not guarantee atomicity
     *
     * Usage criteria of volatile:
     *
     * 1. Writes to the variable do not depend on its current value, or you can ensure that only
     * a single thread updates it.
     * 2. The variable does not participate in invariants with other state variable.
     * 3. And, locking is not required for any other reason while the variable is being accessed.
     *
     * */

}
