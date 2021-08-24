package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._9_buffering

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Running different parts of a flow in different coroutines can be helpful from the standpoint of
     * the overall time it takes to collect the flow, especially when long-running asynchronous operations
     * are involved. For example, consider a case when the emission by foo() flow is slow, taking 100 ms
     * to produce an element; and collector is also slow, taking 300 ms to process an element. Let's see
     * how long it takes to collect such a flow with three numbers.
     */

    fun foo(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100)
            emit(i)
        }
    }

    runBlocking {
        val time = measureTimeMillis {
            foo().collect { value ->
                delay(300)
                println(value)
            }
        }
        println("Collected in $time ms")
    }

    /**
     * The whole collection takes around 1200 ms (three numbers, 400 ms for each).
     */

}