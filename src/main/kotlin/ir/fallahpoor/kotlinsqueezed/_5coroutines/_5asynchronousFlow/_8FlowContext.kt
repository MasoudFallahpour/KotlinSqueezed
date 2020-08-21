package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

fun main() {

    /******************
     *  Flow context  *
     ******************/

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
    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

    fun foo1(): Flow<Int> = flow {
        log("Started foo flow")
        for (i in 1..3) {
            emit(i)
        }
    }

    runBlocking {
        foo1().collect { value -> log("Collected $value") }
    }

    /**
     * Since foo1().collect is called from the main thread, the body of foo1's flow is also called in
     * the main thread.
     */

    /********************************
     *  Wrong emission withContext  *
     ********************************/

    /**
     * However, the long-running CPU-consuming code might need to be executed in the context of
     * Dispatchers.Default and UI-updating code might need to be executed in the context of
     * Dispatchers.Main. Usually, withContext is used to change the context in the code using Kotlin
     * coroutines, but code in the flow { ... } builder has to honor the context preservation property
     * and is not allowed to emit from a different context.
     *
     * If you try to run the following code you will get a IllegalStateException exception. Thr reason
     * is that collecting is happening on main thread and emitting is happening on another thread.
     */
    fun foo2(): Flow<Int> = flow {
        // The WRONG way to change context for CPU-consuming code in flow builder
        kotlinx.coroutines.withContext(Dispatchers.Default) {
            for (i in 1..3) {
                Thread.sleep(100) // pretend we are computing it in CPU-consuming way
                emit(i) // emit next value
            }
        }
    }

    runBlocking {
        foo2().collect { value -> println(value) }
    }

    /*********************
     *  flowOn operator  *
     *********************/

    /**
     * The correct way to change the context of a flow is to use the flowOn operator as shown in the
     * example below, which also prints the names of the corresponding threads to show how it all works.
     */
    fun foo3(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            log("Emitting $i")
            emit(i) // emit next value
        }
    }.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

    runBlocking {
        foo3().collect { value ->
            log("Collected $value")
        }
    }

    /**
     * In the above example collection happens on main thread while emitting happens on a worker thread.
     */

}