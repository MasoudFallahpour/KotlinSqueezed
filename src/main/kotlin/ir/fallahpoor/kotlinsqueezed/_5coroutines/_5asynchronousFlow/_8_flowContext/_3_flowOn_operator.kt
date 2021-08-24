package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._8_flowContext

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The correct way to change the context of a flow is to use the flowOn operator as shown in the
     * example below, which also prints the names of the corresponding threads to show how it all works.
     */

    fun foo(): Flow<Int> = flow {
        for (i in 1..3) {
            Thread.sleep(100) // pretend we are computing it in CPU-consuming way
            log("Emitting $i")
            emit(i)
        }
    }.flowOn(Dispatchers.Default) // RIGHT way to change context for CPU-consuming code in flow builder

    runBlocking {
        foo().collect { value ->
            log("Collected $value")
        }
    }

    /**
     * In the above example collection happens on the main thread while emitting happens on another thread.
     */

}

private fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")