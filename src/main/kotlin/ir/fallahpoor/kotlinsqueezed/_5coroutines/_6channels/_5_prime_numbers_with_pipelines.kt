package ir.fallahpoor.kotlinsqueezed._5coroutines._6channels

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Let's take pipelines to the extreme with an example that generates prime numbers using a pipeline of
     * coroutines. We start with an infinite sequence of numbers.
     */

    fun CoroutineScope.numbersFrom(start: Int) = produce {
        var x = start
        while (true) {
            send(x++)  // infinite stream of integers from start
        }
    }

    /**
     * The following stage of the pipeline filters an incoming stream of numbers, removing all the numbers
     * that are divisible by the given prime number.
     */

    fun CoroutineScope.filter(numbers: ReceiveChannel<Int>, primeNumber: Int) = produce {
        for (x in numbers) {
            if (x % primeNumber != 0) {
                send(x)
            }
        }
    }

    /**
     * Now we build our pipeline by starting a stream of numbers from 2, taking a prime number from the current
     * channel, and launching new a pipeline stage for each prime number found:
     * numbersFrom(2) -> filter(2) -> filter(3) -> filter(5) -> filter(7) ...
     *
     * The following example prints the first ten prime numbers, running the whole pipeline in the context of
     * the main thread. Since all the coroutines are launched in the scope of the main runBlocking coroutine
     * we don't have to keep an explicit list of all the coroutines we have started. We use cancelChildren
     * extension function to cancel all the children coroutines after we have printed the first ten prime
     * numbers.
     */
    runBlocking {
        var cur = numbersFrom(2)
        repeat(10) {
            val prime = cur.receive()
            println(prime)
            cur = filter(cur, prime)
        }
        coroutineContext.cancelChildren() // cancel all children to let main finish
    }

    /**
     * Note that you can build the same pipeline using iterator coroutine builder from the standard library.
     * Replace 'produce' with 'iterator', 'send' with 'yield', 'receive' with 'next', 'ReceiveChannel' with
     * 'Iterator', and get rid of the coroutine scope. You will not need runBlocking either. However, the
     * benefit of a pipeline that uses channels as shown above is that it can actually use multiple CPU cores
     * if you run it in Dispatchers.Default context.
     */

}