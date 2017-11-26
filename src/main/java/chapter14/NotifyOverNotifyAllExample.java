package chapter14;

public class NotifyOverNotifyAllExample {
   /** using notify over notifyAll only when meet both of requirement below
    *       1. Uniform waiters: only one predicate condition is associated with condition queue,
    *       and each thread executes the same logic upon returning from wait; and
    *
    *       2. One in, one out: a notification on the condition variable enables at most one thread to make progress.
    *
    * */

}
