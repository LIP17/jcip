package chapter3;

import net.jcip.annotations.NotThreadSafe;

@NotThreadSafe
public class ThisEscape {

    private final int cnt;

    public ThisEscape(EventSource source) {
        source.registerListener(
                /**
                 * !! Never instantiate inner class in this way.
                 * 1. Never instantiate anonymous class in constructor.
                 * 2. Never instantiate anonymous class with private method or variable
                 *    of outer class.
                 * */
                new EventListener() {
                    public void onEvent(Event e) {
                        doSomething();
                    }
                }
        );
        cnt = 42;
    }

    private void doSomething() {
        /**
         * Race condition will be here, when we calling this, the inner
         * EventListener instance publish with a ref of the outer class (ThisEscape)
         * if you see the compiled code. And if the cnt is not instantiated, which
         * is possible, then the cnt will be some value other then 42.
         *
         * */
        if(cnt != 42) System.out.println("Race condition happened");
    }
}

interface EventSource {
    void registerListener(EventListener listener) ;
}

interface EventListener {
    void onEvent(Event e);
}

interface Event {

}
