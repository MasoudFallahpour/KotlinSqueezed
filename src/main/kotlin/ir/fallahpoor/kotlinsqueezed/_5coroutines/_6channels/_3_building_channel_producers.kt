package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The pattern where a coroutine is producing a sequence of elements is quite common. This is a part of
     * producer-consumer pattern that is often found in concurrent code.
     *
     * There is a convenient coroutine builder named 'produce' that makes it easy to do it right on producer
     * side, and an extension function 'consumeEach', that replaces a for loop on the consumer side.
     */

    runBlocking {
        val squares = produceSquares()
        squares.consumeEach { println(it) }
        println("Done!")
    }

}

private fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
    for (x in 1..5) {
        send(x * x)
    }
}