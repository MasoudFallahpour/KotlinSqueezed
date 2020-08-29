package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /*************
     *  Fan-Out  *
     *************/

    /**
     * Multiple coroutines may receive from the same channel, distributing work between themselves. Let us start
     * with a producer coroutine that is periodically producing integers (ten numbers per second):
     */
    fun CoroutineScope.produceNums() = produce<Int> {
        var x = 1 // start from 1
        while (true) {
            send(x++) // produce next
            delay(100) // wait 0.1s
        }
    }

    /**
     * Then we can have several processor coroutines. In this example, they just print their id and received number:
     */
    fun CoroutineScope.launchProcessor(id: Int, channel: ReceiveChannel<Int>) = launch {
        for (msg in channel) {
            println("Processor #$id received $msg")
        }
    }

    /**
     * Now let us launch five processors and let them work for almost a second. See what happens:
     */

    runBlocking {
        val producer = produceNums()
        repeat(5) { launchProcessor(it, producer) }
        delay(950)
        producer.cancel() // cancel producer coroutine and thus kill them all
    }

    /**
     * Pay attention to how we explicitly iterate over the channel with a for loop to perform fan-out in
     * launchProcessor code.
     */

}