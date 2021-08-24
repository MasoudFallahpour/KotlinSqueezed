package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._9_buffering

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * When a flow represents partial results of the operation or operation status updates, it may not
     * be necessary to process each value, but instead, only most recent ones. In this case, the conflate
     * operator can be used to skip intermediate values when a collector is too slow to process them.
     * Run the following code.
     */

    runBlocking {
        val time = measureTimeMillis {
            foo()
                .conflate() // conflate emissions, skip processing a value if needed.
                .collect { value ->
                    delay(300)
                    println(value)
                }
        }
        println("Collected in $time ms")
    }

    /**
     * You can see that while the first number was still being processed the second, and third were
     * already produced, so the second one was conflated and only the most recent (the third one) was
     * delivered to the collector.
     */

}

private fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}