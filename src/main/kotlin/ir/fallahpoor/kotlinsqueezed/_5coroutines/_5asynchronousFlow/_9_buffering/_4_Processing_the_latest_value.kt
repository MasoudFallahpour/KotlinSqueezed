package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._9_buffering

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Conflation is one way to speed up processing when both the emitter and collector are slow. It
     * does it by dropping emitted values. The other way is to cancel a slow collector and restart it
     * every time a new value is emitted.
     *
     * There is a family of xxxLatest operators that perform the same essential logic of a xxx operator,
     * but cancel the code in their block on a new value. Let's try changing conflate to collectLatest in
     * the previous example.
     */

    runBlocking {
        val time = measureTimeMillis {
            foo().collectLatest { value -> // cancel & restart on the latest value
                println("Collecting $value")
                delay(300)
                println("Done $value")
            }
        }
        println("Collected in $time ms")
    }

    /**
     * Since the body of collectLatest takes 300 ms, but new values are emitted every 100 ms, we see that
     * the block is run on every value, but completes only for the last value:
     */

}

private fun foo(): Flow<Int> = flow {
    for (i in 1..3) {
        delay(100)
        emit(i)
    }
}