package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {

    /***********************************
     * Thread confinement fine grained *
     ***********************************/

    /**
     * Thread confinement is an approach to the problem of shared mutable state where all access to the particular
     * shared state is confined to a single thread. It is typically used in UI applications, where all UI state is
     * confined to the single event-dispatch/application thread. It is easy to apply with coroutines by using a
     * single-threaded context.
     */
    val counterContext = newSingleThreadContext("CounterContext")
    var counter = 0

    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                // confine each increment to a single-threaded context
                withContext(counterContext) {
                    counter++
                }
            }
        }
        println("Counter = $counter")
    }

    /**
     * This code works very slowly, because it does fine-grained thread-confinement. Each individual increment
     * switches from multi-threaded Dispatchers.Default context to the single-threaded context using
     * withContext(counterContext) block.
     */

}