package chapter3;

import net.jcip.annotations.ThreadSafe;

/**
 * Corresponding correct version to ThisEscape.java. What
 * this do is to not start a thread in constructor in case the
 * thread start before the constructor did not return fully.
 *
 * The pattern is use Factory Pattern, and call a separate start
 * method after constructor return fully.
 * */
@ThreadSafe
public class SafeListener {
    private final EventListener listener;

    private SafeListener() {
        listener = new EventListener() {
            public void onEvent(Event e) {
                // DO SOMETHING
            }
        };
    }

    public static SafeListener newInstance(EventSource eventSource) {
        SafeListener safe = new SafeListener();
        eventSource.registerListener(safe.listener);
        return safe;
    }
}
