package ir.fallahpoor.kotlinsqueezed._5coroutines._2cancellationAndTimeouts

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The launch() function returns a Job that can be used to cancel the running coroutine.
     */

    runBlocking {
        val job = launch {
            repeat(1000) { i ->
                println("job: I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L)
        println("main: I'm tired of waiting!")
        job.cancel() // cancels the job
        job.join() // waits for job's completion
        println("main: Now I can quit.")
    }

    /**
     * As soon as job.cancel() is invoked, we don't see any output from the coroutine because it was
     * cancelled. There is also a Job extension function cancelAndJoin() that combines cancel() and
     * join() invocations.
     */

    /**
     * A Job could be in any of the following states:
     * New, Active, Cancelling, Cancelled, Completing, Completed.
     *
     * Calling cancel() causes the job to enter a transient 'Cancelling' state. The job is yet to enter
     * the final 'Cancelled' state and only after the job enters this state it can be considered
     * completely cancelled.
     *
     * After calling cancel() we still need to wait, as the job is truly cancelled only when it reaches
     * the 'Cancelled' state. Hence, we call join() to wait until the job enters the 'Cancelled' state.
     */

}