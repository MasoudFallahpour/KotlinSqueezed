package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Channels allow coroutines to communicate with each other.
     *
     * A Channel is conceptually similar to a queue. One key difference is that instead of a blocking
     * 'put' operation it has a suspending 'send', and instead of a blocking 'take' operation it has a
     * suspending 'receive'.
     */

    runBlocking {
        val channel = Channel<Int>()
        launch {
            // this might be heavy CPU-consuming computation or async logic, we'll just send five numbers
            for (x in 1..5) {
                channel.send(x * x)
            }
        }
        // here we print the received integers.
        repeat(5) { println(channel.receive()) }
        println("Done!")
    }


    /**
     * In the above code, we have two coroutines. We are producing values is one and consume it in the other
     * one.
     */

}