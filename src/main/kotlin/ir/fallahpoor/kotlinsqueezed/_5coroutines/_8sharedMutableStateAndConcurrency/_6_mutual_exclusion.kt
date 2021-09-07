package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Mutual exclusion solution to the problem is to protect all modifications of the shared state with a
     * critical section that is never executed concurrently. In a blocking world you'd typically use
     * synchronized or ReentrantLock for that. Coroutine's alternative is called Mutex. It has lock and
     * unlock functions to delimit a critical section. The key difference is that Mutex.lock() is a
     * suspending function. It does not block a thread.
     *
     * There is also withLock extension function that conveniently represents the pattern:
     * mutex.lock()
     * try {
     *     ...
     * }
     * finally {
     *     mutex.unlock()
     * }
     */

    val mutex = Mutex()
    var counter = 0

    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                // protect each increment with lock
                mutex.withLock {
                    counter++
                }
            }
        }
        println("Counter = $counter")
    }

    /**
     * The locking in this example is fine-grained, so it pays the price. However, it is a good choice for
     * some situations where you absolutely must modify some shared state periodically, but there is no
     * natural thread that this state is confined to.
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