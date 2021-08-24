package ir.fallahpoor.kotlinsqueezed._5coroutines._3composingSuspendingFunctions

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Assume that we have two suspending functions defined elsewhere that do something useful.
     * What do we do if we need them to be invoked sequentially and compute the sum of their results?
     * We use a normal sequential invocation, because the code in the coroutine, just like in the
     * regular code, is sequential by default. The following example demonstrates it by measuring the
     * total time it takes to execute both suspending functions.
     */

    runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")
    }

}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}