package com.falconheed.threadsafe.factorizer;

/**
 * Reentrancy
 * ----------
 *
 * Reentrancy means that locks are acquired on a per-thread rather than per-invocation basis.
 *
 * When a thread acquires a previously unheld lock, the JVM records the owner and sets the acquisition count to one. If that same thread acquires the lock again, the count is incremented, and when the owning thread exits the synchronized block, the count is decremented. When the count reaches zero, the lock is released.
 *
 * Without reentrancy, a child class overriding it parent method that is synchronized and calling the parent method within the override method will result in deadlock.
 */
public class ReentrantLock {

    public synchronized void lock() {
        //...
    }

    class ReentrantChild extends ReentrantLock {

        @Override
        public synchronized void lock() {
            //Doing some heavy lifting work over...
            super.lock();
        }
    }
}
