package ir.fallahpoor.kotlinsqueezed._5coroutines._2cancellationAndTimeouts

import kotlinx.coroutines.*

fun main() {

    /**
     * Any attempt to use a suspending function in a 'finally' block of the previous example causes
     * CancellationException, because the coroutine running this code is cancelled. Usually, this is
     * not a problem, since all well-behaving closing operations (e.g. closing a file) are usually
     * non-blocking and do not involve any suspending functions. However, in the rare case when you
     * need to suspend in a cancelled coroutine you can wrap the corresponding code in
     * withContext(NonCancellable) {...} using withContext function and NonCancellable context as the
     * following example shows.
     */

    runBlocking {
        val job = launch {
            try {
                repeat(1000) { i ->
                    println("job: I'm sleeping $i ...")
                    delay(500L)
                }
            } finally {
                withContext(NonCancellable) {
                    println("job: I'm running finally")
                    delay(1000L)
                    println("job: And I've just delayed for 1 sec because I'm non-cancellable")
                }
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancelAndJoin()
        println("main: Now I can quit.")
    }

}