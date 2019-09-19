Thread Safety (StatelessFactorizer)

-------------

 

Writing thread-safe code is, at its core, about managing access to state, and in particular to shared, mutable state.

 

Shared: means a variable could be accessed by multiple threads;

Mutable: means that its value could change during its lifetime.

 

Thread safety: means protecting data from uncontrolled concurrent access.

 

---

Making an object thread-safe requires using synchronization to coordinate access to its mutable state; failing to do so could result in data corruption and other undesirable consequences.

The primary mechanism for synchronization in Java is the synchronized keyword, which provides exclusive locking, but the term “synchronization” also includes the use of volatile variables, explicit locks, and atomic variables.

 

If multiple threads access the same mutable state variable without appropriate synchronization, your program is broken. There are three ways to

fix it:

• Don’t share the state variable across threads;

• Make the state variable immutable; or

• Use synchronization whenever accessing the state variable.

 

It is far easier to design a class to be thread-safe than to retrofit it for thread safety later.

 

---

Correctness means that a class conforms to its specification. A good specification defines invariants constraining an object’s state and postconditions describing the effects of its operations.

 

A class is thread-safe if it behaves correctly when accessed from multiple threads, regardless of the scheduling or interleaving of the execution of

those threads by the runtime environment, and with no additional synchronization or other coordination on the part of the calling code.

 

Thread-safe classes encapsulate any needed synchronization so that clients need not provide their own.

 

 

StatelessFactorizer is, like most servlets, stateless: it has no fields and references no fields from other classes.

Stateless objects are always thread-safe.

 

 

Atomicity (UnsafeFactorizer, UnsafeLazyInitRace)

---------

 

What happens when we add one element of state to what was a stateless object?

 

Unfortunately, UnsafeCountingFactorizer is not thread-safe, even though it would work just fine in a single-threaded environment.

While the increment operation, ++count, may look like a single action because of its compact syntax, it is not atomic, which means that it does not execute as a single,

indivisible operation. Instead, it is a shorthand for a sequence of three discrete operations: fetch the current value, add one to it, and write the new value back.

>>> read-modify-write <<<

 

 

Race conditions

---------------

 

in other words, when getting the right answer relies on lucky timing. The most common type of race condition is check-then-act

If LazyInitRace is used to instantiate an application-wide registry, having it return different instances from multiple invocations could cause registrations

to be lost or multiple activities to have inconsistent views of the set of registered objects.

 

Compound actions

----------------

 

An atomic operation is one that is atomic with respect to all operations, including itself, that operate on the same state.

java.util.concurrent.atomic

 

We use an existing thread-safe class to manage the counter state, AtomicLong.

 

Locking

-------

Is it possible to always use existing object/class to manage object states?

Asuming we want cache a number and it factor

 

Unfortunately, this approach does not work. When multiple variables participate in an invariant, they are not independent.

Thus when updating one, you must update the others in the same atomic operation.

 

Using atomic references, we cannot update both lastNumber and lastFactors simultaneously, even though each call to set is atomic; there is still a window

of vulnerability when one has been modified and the other has not, and during that time other threads could see that the invariant does not hold.

To preserve state consistency, update related state variables in a single atomic operation.

 

Intrinsic locks

---------------

A synchronized block has two parts: a reference to an object that will serve as the lock, and a block of code to be guarded by that lock.

Every Java object can implicitly act as a lock for purposes of synchronization; these built-in locks are called intrinsic locks or monitor locks.

Intrinsic locks in Java act as mutexes (or mutual exclusion locks), which means that at most one thread may own the lock. When thread A attempts to acquire a lock held by thread B, A must wait, or block, until B releases it.

Using synchronized block at method may result in unacceptably poor responsiveness.


Reentrancy
----------

Reentrancy means that locks are acquired on a per-thread rather than per-invocation basis.

When a thread acquires a previously unheld lock, the JVM records the owner and sets the acquisition count to one. If that same thread acquires the lock again, the count is incremented, and when the owning thread exits the synchronized block, the count is decremented. When the count reaches zero, the lock is released.

Without reentrancy, a child class overriding it parent method that is synchronized and calling the parent method within the override method will result in deadlock.


Guarding state with locks
-------------------------

Compound actions on shared state, such as incrementing a hit counter (readmodify- write) or lazy initialization (check-then-act), must be made atomic to avoid race conditions.

Further, when using locks to coordinate access to a variable, the same lock must be used wherever that variable is accessed. 

Acquiring the lock associated with an object does not prevent other threads from accessing that object—the only thing that acquiring a lock prevents any other thread from doing is acquiring that same lock.

Every shared, mutable variable should be guarded by exactly one lock.

Liveness and performance
------------------------

The synchronization policy for SynchronizedMethod is to guard each state variable with the object’s intrinsic lock. This simple, coarse-grained approach restored safety, but at a high price.

>>> SynchronizedMethod <<< refactored.
The portions of code that are outside the synchronized blocks operate exclusively on local (stack-based) variables, which are not shared across threads and therefore do not require synchronization.

---
Avoid holding locks during lengthy computations or operations at risk of not completing quickly such as network or console I/O.