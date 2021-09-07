package ir.fallahpoor.kotlinsqueezed._5coroutines._9selectExpression

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun main() {

    /**
     * The onReceive clause in select fails when the channel is closed causing the corresponding select to
     * throw an exception. We can use onReceiveOrNull clause to perform a specific action when the channel
     * is closed. The following example also shows that select is an expression that returns the result of
     * its selected clause.
     */

    suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String =
        select {
            a.onReceiveCatching {
                val value = it.getOrNull()
                if (value != null) {
                    "a -> '$value'"
                } else {
                    "Channel 'a' is closed"
                }
            }
            b.onReceiveCatching {
                val value = it.getOrNull()
                if (value != null) {
                    "b -> '$value'"
                } else {
                    "Channel 'b' is closed"
                }
            }
        }

    /**
     * Let's use it with channel 'a' that produces "Hello" 4 times and channel 'b' that produces "World"
     * 4 times.
     */

    runBlocking {
        val a = produce {
            repeat(4) { send("Hello $it") }
        }
        val b = produce {
            repeat(4) { send("World $it") }
        }
        repeat(8) { // print first eight results
            println(selectAorB(a, b))
        }
        coroutineContext.cancelChildren()
    }

    /**
     * There are a couple of observations to make out of it.
     * - select is biased to the first clause. When several clauses are selectable at the same time, the
     *   first one among them gets selected. Here, both channels are constantly producing strings, so
     *   channel 'a', being the first clause in select, wins. However, because we are using unbuffered
     *   channel, channel 'a' gets suspended from time to time on its 'send' invocation and gives a
     *   chance for channel 'b' to send, too.
     * - 'onReceiveCatching' gets immediately selected when the channel is already closed.
     */

}