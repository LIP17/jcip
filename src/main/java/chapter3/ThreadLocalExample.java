package chapter3;

import java.sql.Connection;
import java.sql.DriverManager;

public class ThreadLocalExample {

    /**
     * When you call ThreadLocal, every thread will has its
     * local instance independently.
     * */
    private static ThreadLocal<Connection> connectionHolder
        = new ThreadLocal<Connection>() {
            public Connection initialValue() {
                try {
                   return DriverManager.getConnection("url");
                } catch (Exception e) {
                    return null;
                }

            }
    };

    /**
     * When you call get first, it will run `initialValue` method first.
     * */
    public static Connection getConnection() {
        return connectionHolder.get();
    }
}
