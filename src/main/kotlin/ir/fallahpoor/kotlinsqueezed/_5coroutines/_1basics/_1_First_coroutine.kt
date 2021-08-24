package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

fun main() {

    /**
     * A coroutine is an instance of suspendable computation. It is conceptually similar to a thread,
     * in the sense that it takes a block of code to run that works concurrently with the rest of the
     * code. However, a coroutine is not bound to any particular thread. It may suspend its execution
     * in one thread and resume in another one.
     */

    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(2000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!")
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(3000L) // block main thread for 3 seconds to keep the JVM process alive

    /**
     * In the above code we launched a coroutine! If you run the code you will see that "Hello," is
     * printed, then after 2 seconds "World!" is printed.
     *
     * Here is how the above code works:
     * - Main thread reaches line 16 and wants to execute it. Line 16 launches a coroutine. Launching
     *   a coroutine is not blocking, meaning that the body of the coroutine (lines 18 and 19) will be
     *   executed on a thread other than the Main thread (call the new thread Worker1).
     * - Main thread reaches line 20. As of now, we have two threads running: Main and Worker1. Line 20
     *   prints "Hello," and line 21 puts thread Main to sleep for 3 seconds.
     * - Meanwhile thread Worker1 reaches line 17 and delay(2000L) releases Worker1. After 2 seconds
     *   the coroutine continues its execution (using a worker thread like Worker2) and line 18 is
     *   executed that prints "World!" and the coroutine is done, hence thread Worker2 is released.
     * - Thread Main is awakened after 3 seconds, and terminates.
     *
     * NOTE: We used Worker1 and Worker2 as thread names to emphasise that a coroutine is not bound to
     *       any specific thread. It may suspend its execution in one thread and resume in another one.
     */

    /**
     * The above piece of code is, conceptually, the same as the following code.
     *
     * Thread {
     *     Thread.sleep(2000L)
     *     println("World!")
     * }.start()
     * println("Hello,")
     * Thread.sleep(3000L)
     */

    /**
     * 'launch' is a coroutine builder. It starts a new coroutine concurrently with the rest of the code.
     * 'delay' is a special function. It suspends the coroutine for a specific period. Suspending a
     * coroutine does not block the underlying thread, but allows other coroutines to use that thread to
     * run their code.
     * Each and every coroutine belongs to a CoroutineScope. Here we launched a new coroutine in the
     * GlobalScope, meaning that the lifetime of the coroutine is limited by the lifetime of the whole
     * application.
     */

    /**
     * Some notes about GlobalScope:
     * - It is used to launch top-level coroutines which are operating on the whole application lifetime
     *   and are not cancelled prematurely.
     * - Active coroutines launched in GlobalScope do not keep the JVM process alive.
     *   That's the reason for using Thread.sleep(3000L) in the above code to keep the JVM process alive.
     *   If we remove it, the JVM process terminates and "World!" is not printed.
     */
}