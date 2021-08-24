package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._11_flattetingFlows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Concatenating mode is implemented by flatMapConcat and flattenConcat operators. They wait for the
     * inner flow to complete before starting to collect the next one as the following example shows.
     */

    runBlocking {
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapConcat { requestFlow(it) }
            .collect { value ->
                println(value)
            }
    }

    /**
     * Although the above code works exactly like the below code, but the above code is more readable.
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

}

private fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500)
    emit("$i: Second")
}