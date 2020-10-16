package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Coroutines can be executed concurrently using a multi-threaded dispatcher like the Dispatchers.Default.
     * It presents all the usual concurrency problems. The main problem being synchronization of access to shared
     * mutable state.
     */

    /*****************
     *  The problem  *
     ****************/

    /**
     * Let us launch a hundred coroutines all doing the same action thousand times. We'll also measure their
     * completion time for further comparisons.
     * We start with a very simple action that increments a shared mutable variable using multi-threaded
     * Dispatchers.Default.
     */
    var counter = 0

    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
            }
        }
        println("Counter = $counter")
    }

    /**
     * What does it print at the end? It is highly unlikely to ever print "Counter = 100000", because a hundred
     * coroutines increment the counter concurrently from multiple threads without any synchronization.
     */

}

suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // times an action is repeated by each coroutine
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