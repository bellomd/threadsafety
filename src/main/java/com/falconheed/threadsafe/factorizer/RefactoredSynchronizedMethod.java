package com.falconheed.threadsafe.factorizer;

import java.math.BigInteger;

/**
 * Liveness and performance
 * ------------------------
 *
 * The synchronization policy for SynchronizedMethod is to guard each state variable with the object’s intrinsic lock. This simple, coarse-grained approach restored safety, but at a high price.
 *
 * >>> SynchronizedMethod <<< refactored.
 * The portions of code that are outside the synchronized blocks operate exclusively on local (stack-based) variables, which are not shared across threads and therefore do not require synchronization.
 *
 * ---
 * Avoid holding locks during lengthy computations or operations at risk of not completing quickly such as network or console I/O.
 */
public class RefactoredSynchronizedMethod {

    private BigInteger lastNumber;
    private BigInteger[] lastFactors;

    // Add new shared variable
    private Long requestCount = 0L;

    public BigInteger[] factorize(final BigInteger value) {

        // Operate exclusively on local (stack-based) variables
        BigInteger validateValue = validate(value); //This may take too much time
        BigInteger[] factors = null;

        synchronized (this) {
            ++requestCount;
            if (validateValue.equals(lastNumber)) {
                return lastFactors;
            }
        }

        if (factors == null) {
            factors = factor(validateValue); //This may take too much time
            synchronized (this) {
                lastNumber = validateValue;
                lastFactors = factors.clone();
            }
        }

        return lastFactors;
    }

    private BigInteger validate(final BigInteger value) {
        // Do some validation
        return value;
    }

    private BigInteger[] factor(final BigInteger i) {
        return new BigInteger[]{BigInteger.ZERO, BigInteger.ONE, BigInteger.TEN};
    }

    public synchronized Long getRequestCount() {
        return requestCount;
    }
}
