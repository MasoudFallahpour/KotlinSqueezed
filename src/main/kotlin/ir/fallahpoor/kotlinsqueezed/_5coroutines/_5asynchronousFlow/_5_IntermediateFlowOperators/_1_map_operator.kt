package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._5_IntermediateFlowOperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Flows can be transformed with operators, just as you would with collections and sequences.
     * Intermediate operators are applied to an upstream flow and return a downstream flow. These
     * operators are cold, just like flows are.
     *
     * The basic operators have familiar names like map and filter. The important difference to sequences is
     * that blocks of code inside these operators can call suspending functions.
     */

    runBlocking {
        (1..3).asFlow()
            .map { request -> performRequest(request) }
            .collect { response -> println(response) }
    }

}

private suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}