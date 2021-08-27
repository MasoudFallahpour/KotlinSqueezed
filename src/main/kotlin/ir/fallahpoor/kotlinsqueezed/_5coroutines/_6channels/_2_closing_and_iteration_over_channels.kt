package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Unlike a queue, a channel can be closed to indicate that no more elements are coming. On the receiver
     * side it is convenient to use a regular for loop to receive elements from the channel.
     *
     * Conceptually, a close is like sending a special close token to the channel. The iteration stops as soon
     * as this close token is received, so there is a guarantee that all previously sent elements before the
     * close are received.
     */

    runBlocking {
        val channel = Channel<Int>()
        launch {
            for (x in 1..5) {
                channel.send(x * x)
            }
            channel.close() // we're done sending
        }
        // here we print received values using `for` loop (until the channel is closed)
        for (y in channel) {
            println(y)
        }
        println("Done!")
    }

}