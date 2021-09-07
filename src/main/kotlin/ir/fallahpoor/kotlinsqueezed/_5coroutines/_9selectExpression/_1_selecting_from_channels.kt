package ir.fallahpoor.kotlinsqueezed._5coroutines._9selectExpression

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancelChildren
import kotlinx.coroutines.channels.ReceiveChannel
import kotlinx.coroutines.channels.produce
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.selects.select

fun main() {

    /**
     * Select expression makes it possible to await multiple suspending functions simultaneously and select
     * the first one that becomes available.
     *
     * Let us have two producers of strings: fizz and buzz. The fizz produces "Fizz" string every 300 ms,
     * and the buzz produces "Buzz!" string every 500 ms.
     */

    fun CoroutineScope.fizz() = produce {
        while (true) {
            delay(300)
            send("Fizz")
        }
    }

    fun CoroutineScope.buzz() = produce {
        while (true) {
            delay(500)
            send("Buzz!")
        }
    }

    /**
     * Using 'receive' suspending function we can receive either from one channel or the other. But select
     * expression allows us to receive from both simultaneously using its 'onReceive' clauses.
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

}