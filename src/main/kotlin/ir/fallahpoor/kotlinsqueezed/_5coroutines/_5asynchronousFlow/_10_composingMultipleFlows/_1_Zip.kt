package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._10_composingMultipleFlows

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.zip
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Flows have a zip operator that combines the corresponding values of two flows. The resulting
     * flow completes as soon as one of the flows completes and cancel is called on the remaining
     * flow.
     */

    runBlocking {
        val nums = (1..3).asFlow()
        val strs = flowOf("one", "two", "three")
        nums.zip(strs) { a: Int, b: String -> "$a -> $b" }
            .collect { println(it) }
    }

}