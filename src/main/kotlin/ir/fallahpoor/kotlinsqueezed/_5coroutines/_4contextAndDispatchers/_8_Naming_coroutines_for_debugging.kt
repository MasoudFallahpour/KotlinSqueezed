package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * When a coroutine is tied to the processing of a specific request or doing some specific background
     * task, it is good to name it explicitly for debugging purposes. The CoroutineName context element
     * serves the same purpose as the thread name. It is included in the thread name that is executing
     * this coroutine when the debugging mode is turned on.
     */

    runBlocking(CoroutineName("main")) {
        log("Started main coroutine")
        val v1 = async(CoroutineName("v1coroutine")) {
            delay(500)
            log("Computing v1")
            252
        }
        val v2 = async(CoroutineName("v2coroutine")) {
            delay(1000)
            log("Computing v2")
            6
        }
        log("The answer for v1 / v2 = ${v1.await() / v2.await()}")
    }

}

private fun log(msg: String) = println("[" + Thread.currentThread().name + "] " + msg)