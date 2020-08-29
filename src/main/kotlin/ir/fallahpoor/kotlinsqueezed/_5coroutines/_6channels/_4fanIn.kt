package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /************
     *  Fan-in  *
     ************/

    /**
     * Multiple coroutines may send to the same channel. For example, let us have a channel of strings, and a
     * suspending function that repeatedly sends a specified string to this channel with a specified delay:
     */
    suspend fun sendString(channel: SendChannel<String>, s: String, time: Long) {
        while (true) {
            delay(time)
            channel.send(s)
        }
    }

    /**
     * Now, let us see what happens if we launch a couple of coroutines sending strings (in this example we launch
     * them in the context of the main thread as main coroutine's children):
     */
    runBlocking {
        val channel = Channel<String>()
        launch { sendString(channel, "foo", 200L) }
        launch { sendString(channel, "BAR!", 500L) }
        repeat(6) { // receive first six
            println(channel.receive())
        }
        coroutineContext.cancelChildren() // cancel all children to let main finish
    }

}