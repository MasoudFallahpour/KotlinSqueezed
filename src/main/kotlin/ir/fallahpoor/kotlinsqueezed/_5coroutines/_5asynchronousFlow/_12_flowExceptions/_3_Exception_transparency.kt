package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._12_flowExceptions

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * But how can code of the emitter encapsulate its exception handling behavior?
     *
     * Flows must be transparent to exceptions, and it is a violation of the exception transparency to emit
     * values in the flow { ... } builder from inside a try/catch block. This guarantees that a collector
     * throwing an exception can always catch it using try/catch as in the previous example.
     *
     * The emitter can use a catch operator that preserves this exception transparency and allows encapsulation
     * of its exception handling. For example, let us emit the text on catching an exception.
     */
    runBlocking {
        foo()
            .catch { e -> emit("Caught $e") } // emit on exception
            .collect { value -> println(value) }
    }

    /**
     * The output of the above example is the same as one before, even though we do not have try/catch around the
     * code anymore.
     */

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