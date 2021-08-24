package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {

    /**
     * Run the following code with the -Dkotlinx.coroutines.debug JVM option.
     */

    newSingleThreadContext("ctx1").use { ctx1 ->
        newSingleThreadContext("ctx2").use { ctx2 ->
            runBlocking(ctx1) {
                log("Started in ctx1")
                withContext(ctx2) {
                    log("Working in ctx2")
                }
                log("Back to ctx1")
            }
        }
    }

    /**
     * The above code demonstrates several new techniques:
     * - Using runBlocking with an explicitly specified context.
     * - Using the withContext function to change the context of a coroutine while still staying in
     *   the same coroutine.
     * - Using the 'use' function from the Kotlin standard library to release threads created with
     *   newSingleThreadContext when they are no longer needed.
     */

}

private fun log(msg: String) = println("[" + Thread.currentThread().name + "] " + msg)