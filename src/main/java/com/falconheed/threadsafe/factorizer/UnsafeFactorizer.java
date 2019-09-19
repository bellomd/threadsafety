package com.falconheed.threadsafe.factorizer;

import java.math.BigInteger;

/**
 * Atomicity
 * ---------
 *
 * What happens when we add one element of state to what was a stateless object?
 *
 * Unfortunately, UnsafeCountingFactorizer is not thread-safe, even though it would work just fine in a single-threaded environment.
 * While the increment operation, ++count, may look like a single action because of its compact syntax, it is not atomic, which means that it does not execute as a single,
 * indivisible operation. Instead, it is a shorthand for a sequence of three discrete operations: fetch the current value, add one to it, and write the new value back.
 * >>> read-modify-write <<<
 *
 *
 * Race conditions
 * ---------------
 *
 * in other words, when getting the right answer relies on lucky timing. The most common type of race condition is check-then-act
 */
public class UnsafeFactorizer {

    private long counter = 0;

    public BigInteger[] factorize(final BigInteger value) {

        final BigInteger[] factors = factor(value);
        ++counter;
        return factors;
    }

    private BigInteger[] factor(final BigInteger i) {
        return null;
    }

    public long getCounter() {
        return counter;
    }
}