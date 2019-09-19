package com.falconheed.threadsafe.factorizer;

import java.math.BigInteger;
import java.util.Random;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Race conditions
 * ---------------
 *
 * in other words, when getting the right answer relies on lucky timing. The most common type of race condition is check-then-act
 * If LazyInitRace is used to instantiate an application-wide registry, having it return different instances from multiple invocations could cause registrations
 * to be lost or multiple activities to have inconsistent views of the set of registered objects.
 *
 * Compound actions
 * ----------------
 *
 * An atomic operation is one that is atomic with respect to all operations, including itself, that operate on the same state.
 * java.util.concurrent.atomic
 *
 * We use an existing thread-safe class to manage the counter state, AtomicLong.
 */
public class SafeFactorizer {

    private final AtomicLong counter = new AtomicLong(0);

    public BigInteger[] factorize(final BigInteger value) {

        final BigInteger[] factors = factor(value);
        counter.incrementAndGet();
        return factors;
    }

    private BigInteger[] factor(final BigInteger i) {
        return new BigInteger[] {BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN};
    }

    public long getCounter() {
        return counter.get();
    }
}