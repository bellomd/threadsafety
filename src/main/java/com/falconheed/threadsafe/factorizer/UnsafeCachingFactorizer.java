package com.falconheed.threadsafe.factorizer;

import java.math.BigInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

/**
 * Locking
 * -------
 * Is it possible to always use existing object/class to manage object states?
 * Asuming we want cache a number and it factor
 *
 * Unfortunately, this approach does not work. When multiple variables participate in an invariant, they are not independent.
 * Thus when updating one, you must update the others in the same atomic operation.
 *
 * Using atomic references, we cannot update both lastNumber and lastFactors simultaneously, even though each call to set is atomic; there is still a window
 * of vulnerability when one has been modified and the other has not, and during that time other threads could see that the invariant does not hold.
 * To preserve state consistency, update related state variables in a single atomic operation.
 */
public class UnsafeCachingFactorizer {

    private final AtomicReference<BigInteger> lastNumber = new AtomicReference<>();
    private final AtomicReference<BigInteger[]> lastFactors = new AtomicReference<>();

    public AtomicReference<BigInteger[]> factorize(final BigInteger value) {
        if (value.equals(lastNumber.get())) {
            return lastFactors;
        } else {
            final BigInteger[] factors = factor(value);
            lastNumber.set(value);
            lastFactors.set(factors);
            return lastFactors;
        }
    }

    private BigInteger[] factor(final BigInteger i) {
        return new BigInteger[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN};
    }
}
