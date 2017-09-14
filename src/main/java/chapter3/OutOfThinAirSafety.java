package chapter3;

/**
 * out-of-thin-air safety guarantee that without sync, the stale value are placed by one of threads, and
 * cannot be a random value.
 *
 * exception: for double and long type with 64bits, if the code runs on 32bits machine, two separate 32bits
 * operation may from different threads and make the value random.
 *
 * */
public class OutOfThinAirSafety {
    volatile long longValue = 0L;
    volatile double doubleValue = 1L;
}
