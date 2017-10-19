package chapter5.synchronizer;

import java.util.concurrent.Semaphore;

/**
 */
public class SemaphoreTest {
    private class Father {
        Semaphore s;

        Father() {
            s = new Semaphore(1);
        }

        void P() {
            try {
                System.out.println("Father: before acquire");
                s.acquire();
                System.out.println("Father: acquired");
            } catch (Exception e) {

            }
        }
        void V() {
            s.release();
        }

        synchronized void reentrant() {

        }
    }

    private class Son extends Father {
        Son() {
            this.s = new Semaphore(1);
        }

        synchronized void reentrant() {
            super.reentrant();
        }

        void P() {
            // if you put a super.V() here, the son will not block,
            // because inside semaphore there is no record for objects
            // but only one counter, so in this case you are giving more
            // permits than initial value, make sure you handle it in code
            // is required for semaphore!
            // super.V();
            super.P();
            try {
                System.out.println("Son: before acquire");
                s.acquire();
                System.out.println("Son: acquired");
            } catch (Exception e) {

            } finally {
                super.V();
            }
        }

        void V() {
            super.V();
            s.release();
        }
    }

    public static void main(String[] args) {
        /**
         * Binary semaphore can be used as lock, but it is not reentrant! who
         * holds the permit will hold the mutex, in this test the son will be
         * blocked by its father.
         *
         * */
        SemaphoreTest s = new SemaphoreTest();
        Son son = s.new Son();
        // this use reentrant lock so will not block
        son.reentrant();
        // this use semaphore will block
        son.P();



    }

}
