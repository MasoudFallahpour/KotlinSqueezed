package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._12_flowExceptions

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Flow collection can complete with an exception when an emitter or code inside the operators throw
     * an exception. There are several ways to handle these exceptions.
     *
     * One way is to use Kotlin's try/catch block to handle exceptions.
     */

    runBlocking {
        try {
            foo().collect { value ->
                println(value)
                check(value <= 1) { "Collected $value" }
            }
        } catch (e: Throwable) {
            println("Caught $e")
        }
    }

}

private fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        println("Emitting $i")
        emit(i) // emit next value
    }
}