package chapter11;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 */
@ThreadSafe
public class NarrowLockScope {
    @GuardedBy("this")
    private final Map<String, String> attributes = new HashMap<>();

    // synchronized scope is too large
    public synchronized boolean userLocationMatches(String name, String regEx) {
        String key = "users." + name + ".location";
        String location = attributes.get(key);
        if (location == null)
            return false;
        else
            return Pattern.matches(regEx, location);
    }

    // synchronized scope limited
    public boolean userLocationMatchesImproved(String name, String regEx) {
        String key = "users." + name + ".location";
        String location;
        synchronized (this) {
            location = attributes.get(key);
        }
        if (location == null)
            return false;
        else
            return Pattern.matches(regEx, location);
    }
}