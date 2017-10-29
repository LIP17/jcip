package chapter7;

import org.omg.PortableServer.THREAD_POLICY_ID;

import java.util.*;
import java.util.concurrent.AbstractExecutorService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Listing 7.21
 *
 * With this executor you can track the started but not completed task.
 *
 * TODO race condition may happen when you save the cancelled task and it may be already finished
 * this is because the timing issue between process and interrupt call. Handle it!
 * */
public abstract class TrackingCancelledTaskExecutor extends AbstractExecutorService {
    private final ExecutorService exec = Executors.newSingleThreadExecutor();
    private final Set<Runnable> taskCancelledAtShutDown = Collections.synchronizedSet(new HashSet<>());

    public List<Runnable> getCancelledTasks() {
        if(!exec.isTerminated()) throw new IllegalStateException("service still running");
        return new ArrayList<>(taskCancelledAtShutDown);
    }

    @Override
    public void execute(final Runnable command) {
        exec.execute(() -> {
            try {
                command.run();
            } finally {
                if(isShutdown() && Thread.currentThread().isInterrupted())
                    taskCancelledAtShutDown.add(command);
            }
        });
    }
}
