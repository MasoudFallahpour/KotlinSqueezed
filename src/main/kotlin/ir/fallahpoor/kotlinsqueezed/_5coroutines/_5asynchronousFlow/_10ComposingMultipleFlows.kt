package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**********
     *   Zip  *
     **********/

    /**
     * Flows have a zip operator that combines the corresponding values of two flows.
     */
    runBlocking {
        val nums = (1..3).asFlow()
        val strs = flowOf("one", "two", "three")
        nums.zip(strs) { a: Int, b: String -> "($a, $b)" } // compose a single string
            .collect { println(it) }
    }

    /*************
     *  Combine  *
     *************/

    /**
     * When flow represents the most recent value of a variable or operation (see also the related section on
     * conflation), it might be needed to perform a computation that depends on the most recent values of the
     * corresponding flows and to recompute it whenever any of the upstream flows emit a value. The corresponding
     * family of operators is called combine.
     */
    runBlocking {
        val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
        val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
        nums.combine(strs) { a, b -> "($a, $b)" }
            .collect { value ->
                println(value)
            }
    }

}