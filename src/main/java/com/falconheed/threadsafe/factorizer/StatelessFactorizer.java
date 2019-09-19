package com.falconheed.threadsafe.factorizer;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Thread Safety (StatelessFactorizer)
 * -------------
 *
 * Writing thread-safe code is, at its core, about managing access to state, and in particular to shared, mutable state.
 *
 * Shared: means a variable could be accessed by multiple threads;
 * Mutable: means that its value could change during its lifetime.
 *
 * Thread safety: means protecting data from uncontrolled concurrent access.
 *
 * ---
 * Making an object thread-safe requires using synchronization to coordinate access to its mutable state; failing to do so could result in data corruption and other undesirable consequences.
 * The primary mechanism for synchronization in Java is the synchronized keyword, which provides exclusive locking, but the term “synchronization” also includes the use of volatile variables, explicit locks, and atomic variables.
 *
 * If multiple threads access the same mutable state variable without appropriate
 * synchronization, your program is broken. There are three ways to
 * fix it:
 * • Don’t share the state variable across threads;
 * • Make the state variable immutable; or
 * • Use synchronization whenever accessing the state variable.
 *
 * It is far easier to design a class to be thread-safe than to retrofit it for thread safety later.
 *
 * ---
 * Correctness means that a class conforms to its specification. A good specification defines invariants constraining an object’s state and postconditions describing the effects of its operations.
 *
 * A class is thread-safe if it behaves correctly when accessed from multiple threads, regardless of the scheduling or interleaving of the execution of
 * those threads by the runtime environment, and with no additional synchronization or other coordination on the part of the calling code.
 *
 * Thread-safe classes encapsulate any needed synchronization so that clients need not provide their own.
 *
 *
 * StatelessFactorizer is, like most servlets, stateless: it has no fields and references no fields from other classes.
 * Stateless objects are always thread-safe.
 */
public class StatelessFactorizer {

    public BigInteger[] factorize(final BigInteger value) {

        final BigInteger[] factors = factor(value);
        return factors;
    }

    private BigInteger[] factor(final BigInteger i) {
        return null;
    }
}