package ir.fallahpoor.kotlinsqueezed._5coroutines._9selectExpression

import kotlinx.coroutines.*
import kotlinx.coroutines.selects.select
import kotlin.random.Random

fun main() {

    /**
     * Deferred values can be selected using 'onAwait' clause. Let us start with an async function that
     * returns a deferred string value after a random delay.
     */

    fun CoroutineScope.stringAsync(time: Int) = async {
        delay(time.toLong())
        "Waited for $time ms"
    }

    /**
     * Let us start a dozen of them with a random delay.
     */

    fun CoroutineScope.asyncStringsList(): List<Deferred<String>> {
        val random = Random(3)
        return List(12) { stringAsync(random.nextInt(1000)) }
    }

    /**
     * Now the main function awaits for the first of them to complete and counts the number of deferred
     * values that are still active. Note that we've used here the fact that select expression is a Kotlin
     * DSL, so we can provide clauses for it using an arbitrary code. In this case we iterate over a list
     * of deferred values to provide 'onAwait' clause for each deferred value.
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