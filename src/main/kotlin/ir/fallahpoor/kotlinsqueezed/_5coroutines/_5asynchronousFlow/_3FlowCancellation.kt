package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {

    /***********************
     *  Flow cancellation  *
     ***********************/

    /**
     * Flow adheres to the general cooperative cancellation of coroutines. However, flow infrastructure
     * does not introduce additional cancellation points. It is fully transparent for cancellation. As
     * usual, flow collection can be cancelled when the flow is suspended in a cancellable suspending
     * function (like delay), and cannot be cancelled otherwise.
     *
     * The following example shows how the flow gets cancelled on a timeout when running in a
     * withTimeoutOrNull block and stops executing its code.
     */
    fun foo(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            println("Emitting $i")
            emit(i)
        }
    }

    runBlocking {
        withTimeoutOrNull(250) { // Timeout after 250ms
            foo().collect { value -> println(value) }
        }
        println("Done")
    }

}