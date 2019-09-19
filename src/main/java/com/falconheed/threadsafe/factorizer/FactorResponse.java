package com.falconheed.threadsafe.factorizer;

import java.math.BigInteger;

public class FactorResponse {

    private final BigInteger[] factors;

    public FactorResponse(final BigInteger[] factors) {
        this.factors = factors;
    }

    public BigInteger[] getFactors() {
        return factors;
    }
}