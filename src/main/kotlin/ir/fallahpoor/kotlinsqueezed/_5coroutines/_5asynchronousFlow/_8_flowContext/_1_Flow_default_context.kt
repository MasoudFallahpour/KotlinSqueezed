package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._8_flowContext

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Collecting a flow always happens in the context of the calling coroutine. For example, if there
     * is a foo flow, then the following code runs in the context specified by the author of this code,
     * regardless of the implementation details of the foo flow.
     *
     * withContext(context) {
     *     foo.collect { value ->
     *         println(value) // run in the specified context
     *     }
     * }
     *
     * This property of a flow is called context preservation.
     *
     * So, by default, code in the flow { ... } builder runs in the context that is provided by a
     * collector of the corresponding flow. For example, consider the implementation of foo1 that prints
     * the thread it is called on and emits three numbers.
     */

    fun foo(): Flow<Int> = flow {
        log("Started foo1 flow")
        for (i in 1..3) {
            emit(i)
        }
    }

    runBlocking {
        foo().collect { value -> log("Collected $value") }
    }

    /**
     * Since foo1().collect is called from the main thread, the body of foo1's flow is also called in
     * the main thread.
     */

}

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")