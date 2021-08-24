package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {

    /**
     * Flow adheres to the general cooperative cancellation of coroutines. Flow collection can be
     * cancelled when the flow is suspended in a cancellable suspending function (like delay).
     *
     * The following example shows how the flow gets cancelled on a timeout when running in a
     * withTimeoutOrNull block and stops executing its code.
     */

    runBlocking {
        withTimeoutOrNull(250) {
            foo().collect { value -> println(value) }
        }
        println("Done")
    }

}

private fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        println("Emitting $i")
        emit(i)
    }
}