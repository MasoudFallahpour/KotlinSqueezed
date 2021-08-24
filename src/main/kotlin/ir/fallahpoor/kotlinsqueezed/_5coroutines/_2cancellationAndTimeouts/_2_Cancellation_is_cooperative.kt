package ir.fallahpoor.kotlinsqueezed._5coroutines._2cancellationAndTimeouts

import kotlinx.coroutines.*

fun main() {

    /**
     * A coroutine code has to cooperate to be cancellable. All the suspending functions in
     * kotlinx.coroutines are cancellable. They check for cancellation of coroutine and throw
     * CancellationException when cancelled. However, if a coroutine is working in a computation
     * and does not check for cancellation, then it cannot be cancelled, like the following example
     * shows.
     *
     * Run it to see that it continues to print "I'm sleeping" even after calling cancel().
     */
    runBlocking {
        val startTime = System.currentTimeMillis()
        val job = launch(Dispatchers.Default) {
            var nextPrintTime = startTime
            var i = 0
            while (i < 5) {
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

}