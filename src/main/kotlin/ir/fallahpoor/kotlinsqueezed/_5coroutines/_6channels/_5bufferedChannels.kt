package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /***********************
     *  Buffered Channels  *
     ***********************/

    /**
     * The channels shown so far had no buffer. Unbuffered channels transfer elements when sender and receiver meet
     * each other (aka rendezvous). If send is invoked first, then it is suspended until receive is invoked, if
     * receive is invoked first, it is suspended until send is invoked.
     *
     * Both Channel() factory function and 'produce' builder take an optional capacity parameter to specify buffer
     * size. Buffer allows senders to send multiple elements before suspending.
     */
    runBlocking {
        val channel = Channel<Int>(4) // create buffered channel
        val sender = launch { // launch sender coroutine
            repeat(10) {
                println("Sending $it") // print before sending each element
                channel.send(it) // will suspend when buffer is full
            }
        }
        // don't receive anything... just wait....
        delay(1000)
        sender.cancel() // cancel sender coroutine
    }

    /**
     * The above code prints "sending" five times using a buffered channel with capacity of four. The first four
     * elements are added to the buffer and the sender suspends when trying to send the fifth one.
     */

}