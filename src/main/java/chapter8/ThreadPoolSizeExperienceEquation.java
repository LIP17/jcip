package chapter8;

/**
    Notice, your thread pool config may be also limited by shared resources, like
        if all threads share a db driver, then the thread pool in DB may also
        affect available cores in your thread pool config.
 */
public class ThreadPoolSizeExperienceEquation {

    private final int N_CPU = Runtime.getRuntime().availableProcessors();
    private final int CPU_USAGE_EXPECTATION = 0; // 0 <= USAGE <=1

    private final int WAIT_TIME_RATIO = 0; // RATIO = WAIT_TIME / COMPUTE TIME
    private final int THREAD_POOL_SIZE = N_CPU * CPU_USAGE_EXPECTATION * (1 + WAIT_TIME_RATIO);

}
