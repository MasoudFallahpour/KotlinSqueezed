package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._10_composingMultipleFlows

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * When flow represents the most recent value of a variable or operation (see also the related
     * section on conflation), it might be needed to perform a computation that depends on the most
     * recent values of the corresponding flows and to recompute it whenever any of the upstream
     * flows emit a value. The corresponding family of operators is called combine.
     */

    runBlocking {
        val nums = (1..3).asFlow().onEach { delay(300) } // numbers 1..3 every 300 ms
        val strs = flowOf("one", "two", "three").onEach { delay(400) } // strings every 400 ms
        val startTime = System.currentTimeMillis()
        nums.combine(strs) { a, b -> "$a -> $b" }
            .collect { value ->
                println("$value at ${System.currentTimeMillis() - startTime} ms from start")
            }
    }

}