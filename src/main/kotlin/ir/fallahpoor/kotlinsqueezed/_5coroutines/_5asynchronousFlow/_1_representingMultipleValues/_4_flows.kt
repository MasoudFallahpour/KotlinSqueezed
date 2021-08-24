package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._1_representingMultipleValues

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Using the List<Int> result type, means we can only return all the values at once. To represent a
     * stream of values that are being asynchronously computed, we can use a Flow<Int> type just like we
     * would the Sequence<Int> type for synchronously computed values.
     */

    runBlocking {
        // Launch a concurrent coroutine to confirm that the main thread is not blocked
        launch {
            for (k in 1..3) {
                println("I'm not blocked $k")
                delay(100)
            }
        }
        foo().collect { value -> println(value) }
    }

    /**
     * The above code waits 100ms before printing each number without blocking the main thread. This
     * is verified by printing "I'm not blocked" every 100ms from a separate coroutine that is running
     * on the main thread.
     */

    /**
     * Notice the following differences in the code with the Flow from the earlier examples:
     * - A builder function for Flow type is called flow.
     * - Code inside the flow { ... } builder block can suspend.
     * - The function foo4() is no longer marked with suspend modifier.
     * - Values are emitted from the flow using emit function.
     * - Values are collected from the flow using collect function.
     */

}

private fun foo(): Flow<Int> = flow { // flow builder
    for (i in 1..3) {
        delay(100) // pretend we are doing something useful here
        emit(i)
    }
}