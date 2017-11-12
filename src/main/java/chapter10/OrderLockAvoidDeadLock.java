package chapter10;

/**
 * Listing 10.3 use ordering to lock in case of deadlock
 */
public class OrderLockAvoidDeadLock {


    private static final Object tieLock = new Object();

    private interface Account {

        // nothing is implemented here
        int getBalance();
        int credit();
        int debit();
    }

    public void transferMoney(Account from, Account to, final int amount) {
        class Helper {
            public void transfer() throws RuntimeException {
                if(from.getBalance() < amount) throw new RuntimeException("not enough fund");
                else {
                    from.debit();
                    to.credit();
                }
            }
        }

        // use system object hash as a order for locking later
        int fromHash = System.identityHashCode(from);
        int toHash = System.identityHashCode(to);

        if(fromHash < toHash) {
            synchronized (from) {
                synchronized (to) {
                    new Helper().transfer();
                }
            }
        } else if(fromHash > toHash) {
            synchronized (to) {
                synchronized (from) {
                    new Helper().transfer();
                }
            }
        } else {
            // in case of rare hash collision.
            synchronized (tieLock) {
                synchronized (from) {
                    synchronized (to) {
                        new Helper().transfer();
                    }
                }
            }
        }
    }
}
