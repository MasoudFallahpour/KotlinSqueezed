package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The following code launches 100K coroutines and, after a second, each coroutine prints a dot.
     */
    runBlocking {
        repeat(100_000) {
            launch {
                delay(1000L)
                print(".")
            }
        }
    }

    /**
     * Now, try the above code with threads (remove 'runBlocking', replace 'launch' with 'thread', and
     * replace 'delay' with 'Thread.sleep').
     * Most likely your code will produce some sort of out-of-memory error.
     */

}