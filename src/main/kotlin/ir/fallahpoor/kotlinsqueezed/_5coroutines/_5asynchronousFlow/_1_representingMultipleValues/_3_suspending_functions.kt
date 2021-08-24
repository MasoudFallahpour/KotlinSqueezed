package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._1_representingMultipleValues

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * In the previous example, function foo blocks the main thread that is running the code. When the
     * values are computed by asynchronous code we can mark the function with a 'suspend' modifier, so
     * that it can perform its work without blocking and return the result as a list.
     */

    runBlocking {
        foo().forEach { value -> println(value) }
    }

}

private suspend fun foo(): List<Int> {
    delay(1000) // pretend we are doing something asynchronous here
    return listOf(1, 2, 3)
}