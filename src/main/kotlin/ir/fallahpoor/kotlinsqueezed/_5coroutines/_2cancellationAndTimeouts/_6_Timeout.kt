package ir.fallahpoor.kotlinsqueezed._5coroutines._2cancellationAndTimeouts

import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeout
import kotlinx.coroutines.withTimeoutOrNull

fun main() {

    /**
     * The most obvious practical reason to cancel execution of a coroutine is because its execution time
     * has exceeded some timeout. While you can manually track the reference to the corresponding Job and
     * launch a separate coroutine to cancel the tracked one after delay, there is a ready to use
     * withTimeout() function that does it.
     */

    runBlocking {
        withTimeout(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
    }

    /**
     * If you run the above code you will see a TimeoutCancellationException that is thrown by withTimeout().
     * It is a subclass of CancellationException. We have not seen its stack trace printed on the console
     * before. That is because inside a cancelled coroutine CancellationException is considered to be a
     * normal reason for coroutine completion. However, in this example we have used withTimeout() right
     * inside the main function.
     */

    /**
     * Since cancellation is just an exception, all resources are closed in the usual way. You can wrap the
     * code with timeout in a try {...} catch (e: TimeoutCancellationException) {...} block if you need to
     * do some additional action specifically on any kind of timeout or use the withTimeoutOrNull() function
     * that is similar to withTimeout() but returns null on timeout instead of throwing an exception.
     */

    runBlocking {
        val result = withTimeoutOrNull(1300L) {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
            "Done" // will get cancelled before it produces this result
        }
        println("Result is $result")
    }

}