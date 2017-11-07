package chapter8;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * This is demonstrate how thread pool executor config*ed.
 */
public class ThreadPoolExecutorInterface {

    /**
     *
     * ThreadPoolExecutor(
     *  corePoolSize: The size to maintain even there is no task to execute, this will increase when
     *                  the working queue is full.
     *  maximumPoolSize: The maximum size of the thread pool.
     *  keepAliveTime, unit: The time to wait to reap a thread if current pool size is larger than corePoolSize.
     *  workQueue: queue for task
     *  threadFactory:
     *  rejectedExecutionHandler:
     * )
     *
     * */

    /**
     * Notice: when you set corePoolSize to 0, except for SynchronousQueue or queue size is 1, no task will be started
     * until your queue is full because of the underlying policy to populate pool size.
     *
     * */


}
