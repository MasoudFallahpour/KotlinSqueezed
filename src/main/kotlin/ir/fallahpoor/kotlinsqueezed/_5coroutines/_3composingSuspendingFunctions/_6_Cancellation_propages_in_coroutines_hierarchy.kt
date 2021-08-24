package ir.fallahpoor.kotlinsqueezed._5coroutines._3composingSuspendingFunctions

import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Cancellation is always propagated through the coroutine hierarchy. This means that when a
     * coroutine fails , all its sibling coroutines and its parent coroutine get cancelled.
     */

    runBlocking {
        try {
            failingConcurrentSum()
        } catch (e: ArithmeticException) {
            println("Computation failed with ArithmeticException")
        }
    }

}

private suspend fun failingConcurrentSum(): Int = coroutineScope {
    val one = async {
        try {
            delay(Long.MAX_VALUE) // Emulates very long computation
            42
        } finally {
            println("First child was cancelled")
        }
    }
    val two = async<Int> {
        println("Second child throws an exception")
        throw ArithmeticException()
    }
    one.await() + two.await()
}