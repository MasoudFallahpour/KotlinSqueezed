package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._12_flowExceptions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The previous example actually catches any exception happening in the emitter or in any intermediate
     * or terminal operators.
     *
     * Let's change the code so that emitted values are mapped to strings, but the corresponding code produces
     * an exception.
     */

    runBlocking {
        try {
            foo().collect { value -> println(value) }
        } catch (e: Throwable) {
            println("Caught $e")
        }
    }

}

private fun foo(): Flow<String> =
    flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }.map { value ->
        check(value <= 1) { "Crashed on $value" }
        "string $value"
    }