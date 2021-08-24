package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._13_flowCompletion

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * When flow collection completes (normally or exceptionally) it may need to execute an action. As you
     * may have already noticed, it can be done in two ways: imperative or declarative.
     */

    /**
     * In addition to try/catch, a collector can also use a 'finally' block to execute an action upon collect
     * completion.
     */

    runBlocking {
        try {
            (1..3).asFlow()
                .collect { value -> println(value) }
        } finally {
            println("Done")
        }
    }

}