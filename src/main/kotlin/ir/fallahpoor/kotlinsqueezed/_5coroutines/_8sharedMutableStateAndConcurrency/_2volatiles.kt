package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@Volatile // in Kotlin `volatile` is an annotation
private var counter1 = 0

fun main() {

    /******************************
     *  Volatiles are of no help  *
     ******************************/

    /**
     * There is a common misconception that making a variable volatile solves the concurrency problem. Let us try it:
     */
    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter1++
            }
        }
        println("Counter = $counter1")
    }

    /**
     * This code works slower, but we still don't get "Counter = 100000" at the end, because volatile variables
     * guarantee linearizable (this is a technical term for "atomic") reads and writes to the corresponding variable,
     * but do not provide atomicity of larger actions (increment in our case).
     */

}