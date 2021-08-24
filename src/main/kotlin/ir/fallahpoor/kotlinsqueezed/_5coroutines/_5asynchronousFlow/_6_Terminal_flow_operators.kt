package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.reduce
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Terminal operators on flows are suspending functions that start the collection of the flow. The
     * collect() operator is the most basic one, but there are other terminal operators, which can make
     * it easier to do the following:
     * - Conversion to various collections like toList and toSet.
     * - Operators to get the first value and to ensure that a flow emits a single value.
     * - Reducing a flow to a value with 'reduce' and 'fold'.
     */

    runBlocking {
        val sum = (1..5).asFlow()
            .map { it * it } // squares of numbers from 1 to 5
            .reduce { a, b -> a + b } // sum them (terminal operator)
        println(sum)
    }

}