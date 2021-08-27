package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * A pipeline is a pattern where one coroutine is producing, a possibly infinite, stream of values.
     */

    fun CoroutineScope.produceNumbers() = produce {
        var x = 1
        while (true) {
            send(x++) // infinite stream of integers starting from 1
        }
    }

    /**
     * And another coroutine or coroutines are consuming that stream, doing some processing, and producing
     * some other results. In the example below, the numbers are just squared.
     */

    fun CoroutineScope.square(numbers: ReceiveChannel<Int>): ReceiveChannel<Int> = produce {
        for (x in numbers) {
            send(x * x)
        }
    }

    runBlocking {
        val numbers = produceNumbers() // produces integers from 1 and on
        val squares = square(numbers) // squares integers
        repeat(5) {
            println(squares.receive()) // print the first five values
        }
        println("Done!") // we are done
        coroutineContext.cancelChildren() // cancel children coroutines
    }

    /**
     * Note that all functions that create channels are defined as extensions on CoroutineScope, so that
     * we can rely on structured concurrency to make sure that we don't have lingering global coroutines
     * in our application.
     */

}