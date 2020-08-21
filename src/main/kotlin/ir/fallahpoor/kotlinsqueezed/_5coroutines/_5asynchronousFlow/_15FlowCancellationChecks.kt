package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /******************************
     *  Flow Cancellation Checks  *
     ******************************/

    /**
     * For convenience, the flow builder performs additional 'ensureActive' checks for cancellation on each emitted
     * value. It means that a busy loop emitting from a flow { ... } is cancellable:
     */
    fun foo(): Flow<Int> = flow {
        for (i in 1..5) {
            println("Emitting $i")
            emit(i)
        }
    }

    runBlocking {
        foo().collect { value ->
            if (value == 3) cancel()
            println(value)
        }
    }

    /**
     * However, most other flow operators do not do additional cancellation checks on their own for performance
     * reasons.
     */

    /**********************************
     *  Making Busy Flow Cancellable  *
     **********************************/

    /**
     * In the case where you have a busy loop with coroutines you must explicitly check for cancellation. You can
     * add .onEach { currentCoroutineContext().ensureActive() }, but there is a ready-to-use cancellable operator
     * provided to do that:
     */
    runBlocking {
        (1..5).asFlow()
            .cancellable()
            .collect { value ->
                if (value == 3) cancel()
                println(value)
            }
    }

}