package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /*********************
     *  Flow Exceptions  *
     *********************/

    /**
     * Flow collection can complete with an exception when an emitter or code inside the operators throw an exception.
     */
    fun simple(): Flow<Int> = flow {
        for (i in 1..3) {
            println("Emitting $i")
            emit(i) // emit next value
        }
    }

    runBlocking {
        try {
            simple().collect { value ->
                println(value)
                check(value <= 1) { "Collected $value" }
            }
        } catch (e: Throwable) {
            println("Caught $e")
        }
    }

    /**
     * The previous example actually catches any exception happening in the emitter or in any intermediate or
     * terminal operators.
     *
     * Let's change the code so that emitted values are mapped to strings, but the corresponding code produces an
     * exception:
     */
    fun simple1(): Flow<String> =
        flow {
            for (i in 1..3) {
                println("Emitting $i")
                emit(i)
            }
        }
            .map { value ->
                check(value <= 1) { "Crashed on $value" }
                "string $value"
            }

    runBlocking {
        try {
            simple1().collect { value -> println(value) }
        } catch (e: Throwable) {
            println("Caught $e")
        }
    }

    /****************************
     *  Exception Transparency  *
     ****************************/

    /**
     * But how can code of the emitter encapsulate its exception handling behavior?
     *
     * Flows must be transparent to exceptions and it is a violation of the exception transparency to emit values in
     * the flow { ... } builder from inside of a try/catch block. This guarantees that a collector throwing an
     * exception can always catch it using try/catch as in the previous example.
     *
     * The emitter can use a catch operator that preserves this exception transparency and allows encapsulation of
     * its exception handling. For example, let us emit the text on catching an exception:
     */
    runBlocking {
        simple1()
            .catch { e -> emit("Caught $e") } // emit on exception
            .collect { value -> println(value) }
    }

    /**
     * The output of the above example is the same as one before, even though we do not have try/catch around the
     * code anymore.
     */

    /**
     * The catch intermediate operator, honoring exception transparency, catches only upstream exceptions (that is
     * an exception from all the operators above catch, but not below it). If the block in collect { ... } (placed
     * below catch) throws an exception then it escapes.
     */
    runBlocking {
        simple()
            .catch { e -> println("Caught $e") } // does not catch downstream exceptions
            .collect { value ->
                check(value <= 1) { "Collected $value" }
                println(value)
            }
    }

    /**
     * We can combine the declarative nature of the catch operator with a desire to handle all the exceptions, by
     * moving the body of the collect operator into onEach and putting it before the catch operator. Collection of
     * this flow must be triggered by a call to collect() without parameters:
     */
    runBlocking {
        simple()
            .onEach { value ->
                check(value <= 1) { "Collected $value" }
                println(value)
            }
            .catch { e -> println("Caught $e") }
            .collect()
    }

}