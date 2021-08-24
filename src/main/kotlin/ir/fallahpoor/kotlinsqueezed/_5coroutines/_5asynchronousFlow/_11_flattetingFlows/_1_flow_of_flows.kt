package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._11_flattetingFlows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Flows represent asynchronously received sequences of values, so it is quite easy to get in a
     * situation where each value triggers a request for another sequence of values. For example, we
     * can have the following function that returns a flow of two strings 500 ms apart.
     */

    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500)
        emit("$i: Second")
    }

    /**
     * Now if we have a flow of three integers and call requestFlow for each of them like the following,
     * then we end up with a flow of flows (Flow<Flow<String>>).
     */

    runBlocking {
        (1..3).asFlow()
            .onEach { delay(100) }
            .map { requestFlow(it) }
            .collect { flow: Flow<String> ->
                flow.collect {
                    println(it)
                }
            }
    }

    /**
     * It could be very convenient to flatten Flow<Flow<String>> to a Flow<String> to make it easier for
     * further processing.
     */

}