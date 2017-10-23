package chapter6;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * Created by lip on 10/22/17.
 */
public class ShutdownHookTest {
    public static void main(String[] args) {
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            // notice kill -9 will ignore this hook
            System.out.println("doing shutdown hook");
        }));

        while (true) {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt();
            }
            System.out.println("in progress");
        }
    }
}
