package ir.fallahpoor.kotlinsqueezed._5coroutines._3composingSuspendingFunctions

import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * What if there are no dependencies between invocations of doSomethingUsefulOne and
     * doSomethingUsefulTwo, and we want to get the answer faster, by doing both concurrently?
     * This is where 'async' comes to help.
     *
     * Conceptually, 'async' is just like 'launch'. It starts a coroutine. The difference is that
     * 'launch' returns a Job and does not carry any resulting value, while 'async' returns a
     * Deferred — a light-weight non-blocking future that represents a promise to provide a result
     * later. You can use .await() on a Deferred to get its eventual result. Deferred is also a
     * Job, so you can cancel it if needed.
     */

    runBlocking {
        val time = measureTimeMillis {
            val one = async {
                println("Running doSomethingUsefulOne()")
                doSomethingUsefulOne()
            }
            val two = async {
                println("Running doSomethingUsefulTwo()")
                doSomethingUsefulTwo()
            }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    /**
     * This is twice as fast, because the two coroutines execute concurrently.
     */

}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}