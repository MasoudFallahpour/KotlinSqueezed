package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.util.concurrent.atomic.AtomicInteger

fun main() {

    /*******************************
     * Thread-safe data structures *
     *******************************/

    /**
     * The general solution that works both for threads and for coroutines is to use a thread-safe (aka
     * synchronized, linearizable, or atomic) data structure that provides all the necessarily synchronization
     * for the corresponding operations that needs to be performed on a shared state. In the case of a simple
     * counter we can use AtomicInteger class which has atomic incrementAndGet operations:
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
     * scale to complex state or to complex operations that do not have ready-to-use thread-safe implementations.
     */


}