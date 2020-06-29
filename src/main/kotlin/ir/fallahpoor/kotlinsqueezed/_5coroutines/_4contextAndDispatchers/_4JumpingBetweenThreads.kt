package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

fun main() {

    /*****************************
     *  Jumping between threads  *
     *****************************/

    /**
     * Run the following code with the -Dkotlinx.coroutines.debug JVM option.
     */
    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

    newSingleThreadContext("Ctx1").use { ctx1 ->
        newSingleThreadContext("Ctx2").use { ctx2 ->
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
     * It demonstrates several new techniques. One is using runBlocking with an explicitly specified
     * context, and the other one is using the withContext function to change the context of a
     * coroutine while still staying in the same coroutine.
     */

}