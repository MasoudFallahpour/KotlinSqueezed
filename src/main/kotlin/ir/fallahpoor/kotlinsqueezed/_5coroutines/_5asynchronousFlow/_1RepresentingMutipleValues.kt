package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**********************************
     *  Representing multiple values  *
     **********************************/

    /**
     * Multiple values can be represented in Kotlin using collections. For example, we can have a function
     * foo1() that returns a List of three numbers and then print them all using forEach.
     */
    fun foo1(): List<Int> = listOf(1, 2, 3)
    foo1().forEach { value -> println(value) }

    /**
     * If we are computing the numbers with some CPU-consuming blocking code (each computation taking
     * 100ms), then we can represent the numbers using a Sequence.
     */
    fun foo2(): Sequence<Int> = sequence { // sequence builder
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it
            yield(i) // yield next value
        }
    }

    foo2().forEach { value -> println(value) }

    /**
     * However, function foo2 blocks the main thread that is running the code. When these values are
     * computed by asynchronous code we can mark the function with a suspend modifier, so that it can
     * perform its work without blocking and return the result as a list.
     */
    suspend fun foo3(): List<Int> {
        delay(1000) // pretend we are doing something asynchronous here
        return listOf(1, 2, 3)
    }

    runBlocking {
        foo3().forEach { value -> println(value) }
    }

    /**
     * Using the List<Int> result type, means we can only return all the values at once. To represent the
     * stream of values that are being asynchronously computed, we can use a Flow<Int> type just like we
     * would the Sequence<Int> type for synchronously computed values.
     */
    fun foo4(): Flow<Int> = flow { // flow builder
        for (i in 1..3) {
            delay(100) // pretend we are doing something useful here
            emit(i) // emit next value
        }
    }

    runBlocking {
        // Launch a concurrent coroutine to check if the main thread is blocked
        launch {
            for (k in 1..3) {
                println("I'm not blocked $k")
                delay(100)
            }
        }
        // Collect the flow
        foo4().collect { value -> println(value) }
    }

    /**
     * Notice the following differences in the code with the Flow from the earlier examples:
     * - A builder function for Flow type is called flow.
     * - Code inside the flow { ... } builder block can suspend.
     * - The function foo4() is no longer marked with suspend modifier.
     * - Values are emitted from the flow using emit function.
     * - Values are collected from the flow using collect function.
     */

}