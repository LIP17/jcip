package chapter1;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

/**
 * Created by lip on 8/24/17.
 */

@ThreadSafe
public class StatelessFactorizer {
    @GuardedBy("this") private int nextvalue;
}
