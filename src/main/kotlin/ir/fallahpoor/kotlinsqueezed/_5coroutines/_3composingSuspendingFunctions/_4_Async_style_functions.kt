package ir.fallahpoor.kotlinsqueezed._5coroutines._3composingSuspendingFunctions

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * We can define async-style functions that invoke doSomethingUsefulOne and doSomethingUsefulTwo
     * asynchronously using the async coroutine builder with an explicit GlobalScope reference. We
     * name such functions with the "…Async" suffix to highlight the fact that they only start
     * asynchronous computation and one needs to use the resulting deferred value to get the result.
     */

    val time = measureTimeMillis {
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // Waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")

    /**
     * This programming style with async functions is provided here only for illustration, because it is
     * a popular style in other programming languages. Using this style with Kotlin coroutines is strongly
     * discouraged for the reason explained below.
     *
     * Consider what happens if between the val one = somethingUsefulOneAsync() line and one.await()
     * expression there is some logic error in the code and the program throws an exception and the
     * operation that was being performed by the program aborts. Normally, a global error-handler could
     * catch this exception, log and report the error for developers, but the program could otherwise
     * continue doing other operations. But here we have somethingUsefulOneAsync still running in the
     * background, even though the operation that initiated it was aborted. This problem does not happen
     * with structured concurrency.
     */

}

private fun somethingUsefulOneAsync() = GlobalScope.async {
    doSomethingUsefulOne()
}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private fun somethingUsefulTwoAsync() = GlobalScope.async {
    doSomethingUsefulTwo()
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}