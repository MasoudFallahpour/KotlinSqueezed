package ir.fallahpoor.kotlinsqueezed._5coroutines._3composingSuspendingFunctions

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Let us take the 'Concurrent using async' example and extract a function that concurrently
     * performs doSomethingUsefulOne and doSomethingUsefulTwo and returns the sum of their results.
     * Because the async coroutine builder is defined as an extension on CoroutineScope, we need to
     * have it in the scope and that is what the coroutineScope function provides.
     */

    runBlocking {
        val time = measureTimeMillis {
            println("The answer is ${concurrentSum()}")
        }
        println("Completed in $time ms")
    }

    /**
     * This way, if something goes wrong inside the code of the concurrentSum function and it throws an
     * exception, all the coroutines that were launched in its scope will be cancelled.
     */

}

private suspend fun concurrentSum(): Int = coroutineScope {
    val one = async { doSomethingUsefulOne() }
    val two = async { doSomethingUsefulTwo() }
    one.await() + two.await()
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}