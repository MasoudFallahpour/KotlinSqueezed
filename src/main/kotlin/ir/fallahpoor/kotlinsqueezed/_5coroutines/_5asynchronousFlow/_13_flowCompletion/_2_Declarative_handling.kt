package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._13_flowCompletion

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * For the declarative approach, flow has 'onCompletion' intermediate operator that is invoked when
     * the flow has completely collected.
     */

    runBlocking {
        (1..3).asFlow()
            .onCompletion { println("Done") }
            .collect { value -> println(value) }
    }

    /**
     * The key advantage of onCompletion is a nullable Throwable parameter of the lambda that can be used
     * to determine whether the flow collection was completed normally or exceptionally. In the following
     * example the simple flow throws an exception after emitting the number 1:
     */

    runBlocking {
        foo()
            .onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
            .catch { println("Caught exception") }
            .collect { value -> println(value) }
    }

    /**
     * The onCompletion operator, unlike catch, does not handle the exception. As we can see from the above
     * example code, the exception still flows downstream.
     */

}

private fun foo(): Flow<Int> = flow {
    emit(1)
    throw RuntimeException()
}