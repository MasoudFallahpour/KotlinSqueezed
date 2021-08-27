package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.channels.ticker
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull

fun main() {

    /**
     * Ticker channel is a special rendezvous channel that produces 'Unit' every time given delay passes
     * since last consumption from this channel. Though it may seem to be useless standalone, it is a
     * useful building block to create complex time-based produce pipelines and operators that do windowing
     * and other time-dependent processing.
     *
     * To create such channel use a factory method ticker. To indicate that no further elements are needed
     * use ReceiveChannel.cancel() method on it.
     */

    runBlocking {
        val tickerChannel = ticker(delayMillis = 100, initialDelayMillis = 0) // create ticker channel
        var nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Initial element is available immediately: $nextElement") // no initial delay

        nextElement = withTimeoutOrNull(50) { tickerChannel.receive() } // all subsequent elements have 100ms delay
        println("Next element is not ready in 50 ms: $nextElement")

        nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
        println("Next element is ready in 100 ms: $nextElement")

        // Emulate large consumption delays
        println("Consumer pauses for 150ms")
        delay(150)
        // Next element is available immediately
        nextElement = withTimeoutOrNull(1) { tickerChannel.receive() }
        println("Next element is available immediately after large consumer delay: $nextElement")
        // Note that the pause between `receive` calls is taken into account and next element arrives faster
        nextElement = withTimeoutOrNull(60) { tickerChannel.receive() }
        println("Next element is ready in 50ms after consumer pause in 150ms: $nextElement")

        tickerChannel.cancel()
    }

    /**
     * Note that ticker is aware of possible consumer pauses and, by default, adjusts next produced element
     * delay if a pause occurs, trying to maintain a fixed rate of produced elements.
     */

}