package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /*********************
     *  Flow Completion  *
     *********************/

    /**
     * When flow collection completes (normally or exceptionally) it may need to execute an action.
     */

    /******************************
     *  Imperative Finally Block  *
     ******************************/

    /**
     * In addition to try/catch, a collector can also use a finally block to execute an action upon collect
     * completion.
     */
    fun simple(): Flow<Int> = (1..3).asFlow()

    runBlocking {
        try {
            simple().collect { value -> println(value) }
        } finally {
            println("Done")
        }
    }

    /**************************
     *  Declarative Handling  *
     **************************/

    /**
     * For the declarative approach, flow has 'onCompletion' intermediate operator that is invoked when the flow
     * has completely collected.
     */
    runBlocking {
        simple()
            .onCompletion { println("Done") }
            .collect { value -> println(value) }
    }

    /**
     * The key advantage of onCompletion is a nullable Throwable parameter of the lambda that can be used to
     * determine whether the flow collection was completed normally or exceptionally. In the following example
     * the simple flow throws an exception after emitting the number 1:
     */
    fun simple1(): Flow<Int> = flow {
        emit(1)
        throw RuntimeException()
    }

    runBlocking {
        simple()
            .onCompletion { cause -> if (cause != null) println("Flow completed exceptionally") }
            .catch { println("Caught exception") }
            .collect { value -> println(value) }
    }

    /**
     * The onCompletion operator, unlike catch, does not handle the exception. As we can see from the above
     * example code, the exception still flows downstream.
     */

}