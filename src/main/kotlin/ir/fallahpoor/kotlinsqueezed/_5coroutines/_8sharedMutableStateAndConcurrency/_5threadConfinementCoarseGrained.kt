package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {

    /*************************************
     * Thread confinement coarse grained *
     *************************************/

    /**
     * In practice, thread confinement is performed in large chunks, e.g. big pieces of state-updating business
     * logic are confined to the single thread. The following example does it like that, running each coroutine
     * in the single-threaded context to start with.
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
     * This now works much faster and produces correct result.
     */

}