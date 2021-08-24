package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._5_IntermediateFlowOperators

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.transform
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Among the flow transformation operators, the most general one is called transform. It can be used
     * to imitate simple transformations like map and filter, as well as implement more complex
     * transformations. Using the transform operator, we can emit arbitrary values an arbitrary number of
     * times.
     */

    runBlocking {
        (1..3).asFlow()
            .transform { request ->
                emit("Making request $request")
                emit(performRequest(request))
            }
            .collect { response -> println(response) }
    }

}

private suspend fun performRequest(request: Int): String {
    delay(1000) // imitate long-running asynchronous work
    return "response $request"
}