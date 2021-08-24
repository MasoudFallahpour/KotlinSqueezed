package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._5_IntermediateFlowOperators

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.take
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Size-limiting intermediate operators like take() cancel the execution of the flow when the
     * corresponding limit is reached. Cancellation in coroutines is always performed by throwing an
     * exception, so that all the resource-management functions (like try { ... } finally { ... } blocks)
     * operate normally in case of cancellation.
     */

    runBlocking {
        numbers()
            .take(2)
            .collect { value -> println(value) }
    }

}

private fun numbers(): Flow<Int> = flow {
    try {
        emit(1)
        emit(2)
        println("This line will not execute")
        emit(3)
    } finally {
        println("Finally in numbers")
    }
}