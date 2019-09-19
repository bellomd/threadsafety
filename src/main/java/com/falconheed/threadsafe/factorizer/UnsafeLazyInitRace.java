package com.falconheed.threadsafe.factorizer;

import java.math.BigInteger;

/**
 * Race conditions
 * ---------------
 *
 * in other words, when getting the right answer relies on lucky timing. The most common type of race condition is check-then-act
 * If LazyInitRace is used to instantiate an application-wide registry, having it return different instances from multiple invocations could cause registrations
 * to be lost or multiple activities to have inconsistent views of the set of registered objects.
 */
public class UnsafeLazyInitRace {

    private FactorResponse factorResponse = null;

    public FactorResponse getInstance() {
        if (factorResponse == null) {
            factorResponse = new FactorResponse(new BigInteger[]{});
        }
        return factorResponse;
    }
}