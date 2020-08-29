package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /********************
     *  Channel Basics  *
     ********************/

    /**
     * Channels provide a way to transfer a stream of values between coroutines. In other words, it is a
     * communication channel between coroutines.
     *
     * A Channel is conceptually very similar to a BlockingQueue. One key difference is that instead of a blocking
     * 'put' operation it has a suspending 'send', and instead of a blocking 'take' operation it has a suspending
     * 'receive'.
     */
    runBlocking {
        val channel = Channel<Int>()
        launch {
            // this might be heavy CPU-consuming computation or async logic, we'll just send five squares
            for (x in 1..5) channel.send(x * x)
        }
        // here we print five received integers:
        repeat(5) { println(channel.receive()) }
        println("Done!")
    }

    /*****************************************
     *  Closing and Iteration Over Channels  *
     *****************************************/

    /**
     * Unlike a queue, a channel can be closed to indicate that no more elements are coming. On the receiver side
     * it is convenient to use a regular for loop to receive elements from the channel.
     *
     * Conceptually, a close is like sending a special close token to the channel. The iteration stops as soon as
     * this close token is received, so there is a guarantee that all previously sent elements before the close are
     * received.
     */
    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) channel.send(x * x)
            channel.close() // we're done sending
        }
        // here we print received values using `for` loop (until the channel is closed)
        for (y in channel) println(y)
        println("Done!")
    }

    /********************************
     *  Building channel producers  *
     ********************************/

    /**
     * The pattern where a coroutine is producing a sequence of elements is quite common. This is a part of
     * producer-consumer pattern that is often found in concurrent code.
     *
     * There is a convenient coroutine builder named produce that makes it easy to do it right on producer side,
     * and an extension function 'consumeEach', that replaces a for loop on the consumer side.
     */
    fun CoroutineScope.produceSquares(): ReceiveChannel<Int> = produce {
        for (x in 1..5) send(x * x)
    }

    runBlocking {
        val squares = produceSquares()
        squares.consumeEach { println(it) }
        println("Done!")
    }

}