package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The first example mixes non-blocking delay() and blocking Thread.sleep() in the same code. It is
     * easy to lose track of which one is blocking and which one is not. Let's be explicit about blocking
     * using the 'runBlocking' coroutine builder.
     */

    GlobalScope.launch {
        delay(2000L)
        println("World!")
    }
    println("Hello,")
    runBlocking { // this expression blocks the main thread
        delay(3000L)
    }

    /**
     * The result of the above code is the same as the first example, but this code uses only non-blocking
     * delay. The main thread invoking 'runBlocking' blocks until the code inside runBlocking completes.
     */

    /**
     * Some notes about runBlocking:
     * - It is a coroutine builder that bridges the non-coroutine and the coroutine worlds.
     * - The thread that runs it (in this case — the main thread) gets blocked for the duration
     *   of the call, until all the coroutines inside runBlocking { ... } complete their execution.
     */

    /**
     * HERE GOES MY THOUGHT
     *
     * I think the documentation of runBlocking is not clear. It says that runBlocking BLOCKS the
     * current thread meaning that the current thread should wait idle until the code block inside
     * the runBlocking is finished. But the weired thing is that the thread that runs the code block
     * inside the runBlocking is the current thread!
     */

}