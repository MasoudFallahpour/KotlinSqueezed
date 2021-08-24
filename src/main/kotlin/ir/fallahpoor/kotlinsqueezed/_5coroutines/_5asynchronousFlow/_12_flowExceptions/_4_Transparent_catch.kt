package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._12_flowExceptions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The catch intermediate operator, honoring exception transparency, catches only upstream
     * exceptions (that is an exception from all the operators above catch, but not below it).
     * If the block in collect { ... } (placed below catch) throws an exception then it escapes.
     */

    runBlocking {
        foo()
            .catch { e -> println("Caught $e") } // does not catch downstream exceptions
            .collect { value ->
                check(value <= 1) { "Collected $value" }
                println(value)
            }
    }

}

private fun foo(): Flow<Int> =
    flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i)
        }
    }