package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._13_flowCompletion

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Another difference with 'catch' operator is that 'onCompletion' sees all exceptions and receives a
     * null exception only on successful completion of the upstream flow (without cancellation or failure).
     */

    runBlocking {
        (1..3).asFlow()
            .onCompletion { cause -> println("Flow completed with $cause") }
            .collect { value ->
                check(value <= 1) { "Collected $value" }
                println(value)
            }
    }

}