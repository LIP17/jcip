This is my second time reading this book after my first year of real
life engineering work. Based on my experience I got better understanding 
of the content in this book, and what I have done not correctly or not perfect.

Brief intro of the content in this book and some exercise I did:

    chapter2
        1. definition of **thread safety**, **atomicity**, **race condition**, 
        2. introduction of lock and reentrancy, liveness and performance.   
        
        
    chapter3
        1. what is visibility, and how **volatile** guarantee visibility.
        2. object **escape** issue on **publication**
        3. Thread confinment (ad-hoc, **thread local**, stack confinment)
        4. Immutability 
        5. Safe publication idiom.
        
        
     chapter4
        1. Composing object and Java Monitor Pattern
     
     chapter5
        1. Synchromized collections and iterator issue for multiple access.
        2. concurrent collection (ConcurrentHashMap, CopyOnWriteArrayList)
        3. Blocking queue and producer-consumer pattern.
        4. Synchronizer: latch (CountDownLatch), barrier (CyclicBarrier), FutureTask, Semphore
     
     chapter6
        1. Executor framework, thread pool and some basic implementation (fixedThreadPool, 
            cachedThreadPool, singleThreadPool, scheduledThreadPool)
        2. Parallelism in program, Future and Callable.
     
     chapter7
        1. Cancellable, interruption and shutdown
        2. Service shutdown (ExecutorService shutdown, poison pill)
        3. JVM shutdown with **shutdown hook**
        
     chapter8
        1. Sizing thread pool properly based on work load, # of CPU
        2. Customize thread pool using **ThreadPoolExecutor** (task queue customization, saturation policy)
        
     chapter10
        1. Deadlock (lock-ordering deadlock, dynamic lock order deadlock, deadlock brought by cooperating objects, 
            open call, resource deadlock)
        2. **Starvation**
        3. Poor responsiveness
        4. **Livelock**
     
     chapter11
        1. Amdahl's law for maximum possible speedup
        2. Cost of multithread (context switch, memory synchronization, blocking)
        3. Reducing lock contention (narrow lock scope, reduce lock granularity, lock stripping, 
            avoid hot zone, non-blocking synchronization, avoid object pool)
     
     chapter12
        1. Test correctness, blocking, race condition, resource management
        2. how to generate interleaving in test
        3. Performance test with timing, responsiveness
        4. Performance test pitfall (garbage collection, dynamic compilation , etc.)
     
     chapter13
        1. explicit lock and tryLock
        2. Fairness of lock
        3. synchronized vs reentrantLock
        4. **readWriteLock**
        
     chapter14
        1. condition queue, **nofify()** **notifyAll()** and **wait()**, 
        2. explicit **Condition**, **signal()**, **signalAll()** and **await()**
        3. AbstractQueuedSynchronier (AQS) framework underlying Java concurrent.
        
     chapter15
        1. compare-and-swap, compare-and-set
        2. atomic variable classes (**AtomicInteger**, **AtomicBoolean**, **AtomicReference**)
        3. Non-blocking algorithm and data structure
     
     chapter16
        1. Java memory model
        2. rules of **_happens-before_**
        3. explanation of safe publication idiom.
        4. initial safety with **final** and constructor.
            
        