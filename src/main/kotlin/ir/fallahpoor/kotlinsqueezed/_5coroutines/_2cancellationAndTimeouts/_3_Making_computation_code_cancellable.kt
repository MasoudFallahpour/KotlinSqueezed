package ir.fallahpoor.kotlinsqueezed._5coroutines._2cancellationAndTimeouts

import kotlinx.coroutines.*

fun main() {

    /**
     * There are two approaches to making computation code cancellable. The first one is to periodically
     * invoke a suspending function that checks for cancellation. There is a yield() function that is a
     * good choice for that purpose. The other one is to explicitly check the cancellation status. Let us
     * try the latter approach.
     */
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (isActive) { // cancellable computation loop
                if (System.currentTimeMillis() >= nextPrintTime) {
                    println("job: I'm sleeping ${i++} ...")
                    nextPrintTime += 500L
                }
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

    /**
     * The above coroutine is cancellable. isActive is an extension property available inside the coroutine
     * via the CoroutineScope object.
     */

}