package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._12_flowExceptions

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * We can combine the declarative nature of the catch operator with a desire to handle all the
     * exceptions, by moving the body of the collect operator into onEach and putting it before the
     * catch operator. Collection of this flow must be triggered by a call to collect() without
     * parameters.
     */

    runBlocking {
        foo()
            .onEach { value ->
                check(value <= 1) { "Collected $value" }
                println(value)
            }
            .catch { e -> println("Caught $e") }
            .collect()
    }

}

private fun foo(): Flow<Int> =
    flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }