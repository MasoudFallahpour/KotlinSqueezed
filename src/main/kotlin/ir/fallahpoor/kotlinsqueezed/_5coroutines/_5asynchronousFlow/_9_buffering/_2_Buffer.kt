package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._9_buffering

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * We can use a buffer operator on a flow to run emitting code of foo() concurrently with collecting
     * code, as opposed to running them sequentially.
     */

    runBlocking {
        val time = measureTimeMillis {
            foo()
                .buffer() // buffer emissions, don't wait for each value to be consumed
                .collect {
                    delay(300)
                    println(it)
                }
        }
        println("Collected in $time ms")
    }

    /**
     * It produces the same numbers just faster, as we have effectively created a processing pipeline,
     * having to only wait 100 ms for the first number and then spending only 300 ms to process each
     * number. This way it takes around 1000 ms to run.
     *
     * Note that the flowOn operator uses the same buffering mechanism when it has to change a
     * CoroutineDispatcher, but here we explicitly request buffering without changing the execution
     * context.
     */

}

private fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}