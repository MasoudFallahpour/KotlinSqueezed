package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._8_flowContext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {

    /**
     * However, the long-running CPU-consuming code might need to be executed in the context of
     * Dispatchers.Default and UI-updating code might need to be executed in the context of
     * Dispatchers.Main. Usually, withContext is used to change the context in the code using Kotlin
     * coroutines, but code in the flow { ... } builder has to honor the context preservation property
     * and is not allowed to emit from a different context.
     *
     * If you try to run the following code you will get a IllegalStateException exception. The reason
     * is that collection is happening on the main thread and emitting is happening on another thread.
     */

    fun foo(): Flow<Int> = flow {
        // The WRONG way to change context for CPU-consuming code in flow builder
        withContext(Dispatchers.Default) {
            for (i in 1..3) {
                Thread.sleep(100) // pretend we are computing it in CPU-consuming way
                emit(i)
            }
        }
    }

    runBlocking {
        foo().collect { value -> println(value) }
    }

}