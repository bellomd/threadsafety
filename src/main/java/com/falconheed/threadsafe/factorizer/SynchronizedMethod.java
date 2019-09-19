package com.falconheed.threadsafe.factorizer;

import java.math.BigInteger;

/**
 * Intrinsic locks
 *
 * ---------------
 *
 * A synchronized block has two parts: a reference to an object that will serve as the lock, and a block of code to be guarded by that lock.
 *
 * Every Java object can implicitly act as a lock for purposes of synchronization; these built-in locks are called intrinsic locks or monitor locks.
 *
 * Intrinsic locks in Java act as mutexes (or mutual exclusion locks), which means that at most one thread may own the lock. When thread A attempts to acquire a lock held by thread B, A must wait, or block, until B releases it.
 *
 * Using synchronized block at method may result in unacceptably poor responsiveness.
 */
public class SynchronizedMethod {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;

    public synchronized BigInteger[] factorize(final BigInteger value) {

        final BigInteger validateValue = validate(value);

        if (validateValue.equals(lastNumber)) {
            return lastFactors;
        } else {
            final BigInteger[] factors = factor(validateValue);
            lastNumber = validateValue;
            lastFactors = factors;
            return lastFactors;
        }
    }

    private BigInteger validate(final BigInteger value) {
        // Do some validation
        return value;
    }

    private BigInteger[] factor(final BigInteger i) {
        return new BigInteger[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN};
    }
}
