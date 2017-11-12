package chapter11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashSet;
import java.util.Set;

/**
 *
 */
@ThreadSafe
public class NarrowLockGranularity {

    @GuardedBy("this") public final Set<String> users = new HashSet<>();
    @GuardedBy("this") public final Set<String> queries = new HashSet<>();

    /**
     * mutual exclusive method use same lock.
     * */
    public synchronized void addUser(String u) { users.add(u); }
    public synchronized void addQuery(String q) { queries.add(q); }

    /**
     * split lock per usage
     * */

    public void addUserImproved(String u) {
        synchronized (users) {
            users.add(u);
        }
    }

    public void addQueryImproved(String q) {
        synchronized (queries) {
            queries.add(q);
        }
    }
}
