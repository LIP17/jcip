package chapter5;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Listing 5.7 compound action implemented by concurrent map.
 */
public interface ConcurrentMap<K, V> extends Map<K, V> {
    // Insert into map only if no value is mapped from K
    V putIfAbsent(K key, V value);

    // Remove only if K is Mappped to V
    boolean concurrentRemove(K key, V value);

    // Replace value only if K is mapped to old value
    boolean replace(K key, V oldValue, V newValue);

    // Replace value only if K is mapped to some value
    V replace(K key, V newValue);

   static void main(String[] args) {
        Map<String, String> m = new ConcurrentHashMap<>();

        m.putIfAbsent("key", "value");
        m.replace("key", "newValue");
        m.replace("key", "newValue", "newValue1");
        m.remove("key", "newValue");
    }
}


