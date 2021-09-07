package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * In practice, thread confinement is performed in large chunks, e.g. big pieces of state-updating
     * business logic are confined to the single thread. The following example does it like that, running
     * each coroutine in the single-threaded context to start with.
     */

    val counterContext = newSingleThreadContext("CounterContext")
    var counter = 0

    runBlocking {
        // confine everything to a single-threaded context
        withContext(counterContext) {
            massiveRun {
                counter++
            }
        }
        println("Counter = $counter")
    }

    /**
     * This now works much faster and produces the correct result.
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