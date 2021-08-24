package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._11_flattetingFlows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * In a similar way to the collectLatest operator, there is the corresponding "Latest" flattening
     * mode where a collection of the previous flow is cancelled as soon as new flow is emitted. It is
     * implemented by the flatMapLatest operator.
     */

    runBlocking {
        (1..3).asFlow()
            .onEach { delay(100) }
            .flatMapLatest { requestFlow(it) }
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