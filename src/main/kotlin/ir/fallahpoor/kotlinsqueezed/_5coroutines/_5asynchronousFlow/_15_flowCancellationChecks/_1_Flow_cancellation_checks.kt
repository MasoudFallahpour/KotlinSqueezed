package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._15_flowCancellationChecks

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * For convenience, the flow builder performs additional 'ensureActive' checks for cancellation on
     * each emitted value. It means that a busy loop emitting from a flow { ... } is cancellable.
     */

    runBlocking {
        foo().collect { value ->
            if (value == 3) {
                cancel()
            }
            println(value)
        }
    }

    /**
     * However, most flow operators do not do additional cancellation checks on their own for performance
     * reasons. For example, if you use IntRange.asFlow extension to write the same busy loop and don't
     * suspend anywhere, then there are no checks for cancellation.
     */

    runBlocking {
        (1..5).asFlow()
            .collect { value ->
                if (value == 3) {
                    cancel()
                }
                println(value)
            }
    }

    /**
     * In the above code, all numbers from 1 to 5 are collected and cancellation gets detected only before
     * returning from runBlocking.
     */

}

private fun foo(): Flow<Int> = flow {
    for (i in 1..5) {
        println("Emitting $i")
        emit(i)
    }
}