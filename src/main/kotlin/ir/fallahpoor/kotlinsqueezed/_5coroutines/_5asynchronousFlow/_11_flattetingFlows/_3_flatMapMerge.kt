package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._11_flattetingFlows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Another flattening mode is to concurrently collect all the incoming flows and merge their values
     * into a single flow so that values are emitted as soon as possible. It is implemented by
     * flatMapMerge and flattenMerge operators.
     */

    runBlocking {
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapMerge { requestFlow(it) }
            .collect { value ->
                println(value)
            }
    }

}

private fun requestFlow(i: Int): Flow<String> = flow {
    emit("$i: First")
    delay(500)
    emit("$i: Second")
}