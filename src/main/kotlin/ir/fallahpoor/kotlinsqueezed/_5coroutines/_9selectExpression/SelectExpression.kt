package ir.fallahpoor.kotlinsqueezed._5coroutines._9selectExpression

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.*
import kotlinx.coroutines.selects.select
import kotlin.random.Random

fun main() {

    /**
     * Select expression makes it possible to await multiple suspending functions simultaneously and select the first
     * one that becomes available.
     */

    /*****************************
     *  Selecting from channels  *
     *****************************/

    /**
     * Let us have two producers of strings: fizz and buzz. The fizz produces "Fizz" string every 300 ms, and And the
     * buzz produces "Buzz!" string every 500 ms.
     */
    fun CoroutineScope.fizz() = produce {
        while (true) { // sends "Fizz" every 300 ms
            delay(300)
            send("Fizz")
        }
    }

    fun CoroutineScope.buzz() = produce {
        while (true) { // sends "Buzz!" every 500 ms
            delay(500)
            send("Buzz!")
        }
    }

    /**
     * Using receive suspending function we can receive either from one channel or the other. But select expression
     * allows us to receive from both simultaneously using its onReceive clauses:
     */
    suspend fun selectFizzBuzz(fizz: ReceiveChannel<String>, buzz: ReceiveChannel<String>) {
        select<Unit> { // <Unit> means that this select expression does not produce any result
            fizz.onReceive { value ->  // this is the first select clause
                println("fizz -> '$value'")
            }
            buzz.onReceive { value ->  // this is the second select clause
                println("buzz -> '$value'")
            }
        }
    }

    /**
     * Let us run it 7 times
     */
    runBlocking {
        val fizz = fizz()
        val buzz = buzz()
        repeat(7) {
            selectFizzBuzz(fizz, buzz)
        }
        coroutineContext.cancelChildren() // cancel fizz & buzz coroutines
    }

    /************************
     *  Selecting on close  *
     ************************/

    /**
     * The onReceive clause in select fails when the channel is closed causing the corresponding select to throw an
     * exception. We can use onReceiveOrNull clause to perform a specific action when the channel is closed. The
     * following example also shows that select is an expression that returns the result of its selected clause:
     */
    suspend fun selectAorB(a: ReceiveChannel<String>, b: ReceiveChannel<String>): String =
        select<String> {
            a.onReceiveOrNull { value ->
                if (value == null)
                    "Channel 'a' is closed"
                else
                    "a -> '$value'"
            }
            b.onReceiveOrNull { value ->
                if (value == null)
                    "Channel 'b' is closed"
                else
                    "b -> '$value'"
            }
        }

    /**
     * Let's use it with channel a that produces "Hello" string four times and channel b that produces "World" four
     * times:
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
     * There are couple of observations to make out of it. First of all, select is biased to the first clause. When
     * several clauses are selectable at the same time, the first one among them gets selected. Here, both channels
     * are constantly producing strings, so channel a, being the first clause in select, wins. However, because we are
     * using unbuffered channel, the a gets suspended from time to time on its send invocation and gives a chance for b
     * to send, too. The second observation, is that onReceiveOrNull gets immediately selected when the channel is
     * already closed.
     */

    /***********************
     *  Selecting to send  *
     ***********************/

    /**
     * Select expression has onSend clause that can be used for a great good in combination with a biased nature of
     * selection.
     *
     * Let us write an example of producer of integers that sends its values to a side channel when the consumers on
     * its primary channel cannot keep up with it:
     */
    fun CoroutineScope.produceNumbers(side: SendChannel<Int>) = produce<Int> {
        for (num in 1..10) { // produce 10 numbers from 1 to 10
            delay(100) // every 100 ms
            select<Unit> {
                onSend(num) {} // Send to the primary channel
                side.onSend(num) {} // or to the side channel
            }
        }
    }

    /**
     * Consumer is going to be quite slow, taking 250 ms to process each number:
     */
    runBlocking {
        val side = Channel<Int>() // allocate side channel
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

    /*******************************
     *  Selecting deferred values  *
     *******************************/

    /**
     * Deferred values can be selected using onAwait clause. Let us start with an async function that returns a
     * deferred string value after a random delay:
     */
    fun CoroutineScope.asyncString(time: Int) = async {
        delay(time.toLong())
        "Waited for $time ms"
    }

    /**
     * Let us start a dozen of them with a random delay.
     */
    fun CoroutineScope.asyncStringsList(): List<Deferred<String>> {
        val random = Random(3)
        return List(12) { asyncString(random.nextInt(1000)) }
    }

    /**
     * Now the main function awaits for the first of them to complete and counts the number of deferred values that
     * are still active. Note that we've used here the fact that select expression is a Kotlin DSL, so we can provide
     * clauses for it using an arbitrary code. In this case we iterate over a list of deferred values to provide
     * onAwait clause for each deferred value.
     */
    runBlocking {
        val list = asyncStringsList()
        val result = select<String> {
            list.withIndex().forEach { (index, deferred) ->
                deferred.onAwait { answer ->
                    "Deferred $index produced answer '$answer'"
                }
            }
        }
        println(result)
        val countActive = list.count { it.isActive }
        println("$countActive coroutines are still active")
    }

}