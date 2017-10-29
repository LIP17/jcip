package chapter7;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.io.IOException;
import java.net.Socket;
import java.util.concurrent.*;

/**
 * Listing 7.12: Encapsulating nonstandard cancellation in a task with newTaskFor
 */
public class InterruptNonInterruptableUsingTaskFor {

    private interface CancellableTask<T> extends Callable<T> {
        void cancel();
        RunnableFuture<T> newTask();
    }

    @ThreadSafe
    public class CancellingExecutor extends ThreadPoolExecutor {
        public CancellingExecutor(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
            super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
        }

        @Override
        protected <T> RunnableFuture<T> newTaskFor(Callable<T> callable) {
            if (callable instanceof CancellableTask)
                return ((CancellableTask<T>) callable).newTask();
            else
                return super.newTaskFor(callable);
        }
    }

    abstract class SocketUsingTask<T> implements CancellableTask<T> {
        @GuardedBy("this") private Socket socket;

        protected synchronized void setSocket(Socket s) { socket = s; }

        public synchronized void cancel() {
            try {
                if(socket != null) socket.close();
            } catch (IOException ignored) { }
        }

        @Override
        public RunnableFuture<T> newTask() {
            return new FutureTask<T>(this) {
                @Override
                public boolean cancel(boolean mayInterruptIfRunning) {
                    try {
                        SocketUsingTask.this.cancel();
                    } finally {
                        return super.cancel(mayInterruptIfRunning);
                    }
                }
            };
        }
    }
}
