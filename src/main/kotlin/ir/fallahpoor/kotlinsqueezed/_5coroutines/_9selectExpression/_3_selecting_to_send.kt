package ir.fallahpoor.kotlinsqueezed._5coroutines._9selectExpression

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.SendChannel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.selects.select

fun main() {

    /**
     * 'Select' expression has onSend clause that can be used for a great good in combination with a biased
     * nature of selection.
     *
     * Let us write an example of a producer of integers that sends its values to a side channel when the
     * consumers on its primary channel cannot keep up with it.
     */

    fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
        for (num in 1..10) {
            delay(100)
            select<Unit> {
                onSend(num) {} // Send to the primary channel
                side.onSend(num) {} // or to the side channel
            }
        }
    }

    /**
     * the consumer is going to be quite slow, taking 250 ms to process each number.
     */

    runBlocking {
        val side = Channel<Int>()
        launch { // this is a very fast consumer for the side channel
            side.consumeEach { println("Side channel has $it") }
        }
        produceNumbers(side).consumeEach {
            println("Consuming $it")
            delay(250) // let us digest the consumed number properly, do not hurry
        }
        println("Done consuming")
        coroutineContext.cancelChildren()
    }

}