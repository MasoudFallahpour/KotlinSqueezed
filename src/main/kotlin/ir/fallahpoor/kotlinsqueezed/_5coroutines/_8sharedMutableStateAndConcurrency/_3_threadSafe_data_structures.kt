package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * The general solution that works both for threads and for coroutines is to use a thread-safe
     * (aka synchronized, linearizable, or atomic) data structure that provides all the necessary
     * synchronization for the corresponding operations that needs to be performed on a shared state.
     * In the case of a simple counter we can use AtomicInteger class which has atomic incrementAndGet
     * operations.
     */

    val counter = AtomicInteger()

    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.incrementAndGet()
            }
        }
        println("Counter = $counter")
    }

    /**
     * This is the fastest solution for this particular problem. It works for plain counters, collections,
     * queues and other standard data structures and basic operations on them. However, it does not easily
     * scale to complex state or to complex operations that do not have ready-to-use thread-safe
     * implementations.
     */

}

private suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // number of times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}