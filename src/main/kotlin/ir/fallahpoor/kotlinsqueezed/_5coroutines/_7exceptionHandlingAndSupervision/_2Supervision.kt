package ir.fallahpoor.kotlinsqueezed._5coroutines._7exceptionHandlingAndSupervision

import kotlinx.coroutines.*

fun main() {

    /**
     * As we have studied before, cancellation is a bidirectional relationship propagating through the whole
     * hierarchy of coroutines. Let us take a look at the case when unidirectional cancellation is required.
     *
     * A good example of such a requirement is a UI component with the job defined in its scope. If any of the UI's
     * child tasks have failed, it is not always necessary to cancel (effectively kill) the whole UI component, but
     * if UI component is destroyed (and its job is cancelled), then it is necessary to fail all child jobs as their
     * results are no longer needed.
     *
     * Another example is a server process that spawns multiple child jobs and needs to supervise their execution,
     * tracking their failures and only restarting the failed ones.
     */

    /*********************
     *  Supervision job  *
     *********************/

    /**
     * The SupervisorJob can be used for these purposes. It is similar to a regular Job with the only exception that
     * cancellation is propagated only downwards. This can easily be demonstrated using the following example.
     */
    runBlocking {
        val supervisor = SupervisorJob()
        with(CoroutineScope(coroutineContext + supervisor)) {
            // launch the first child -- its exception is ignored for this example (don't do this in practice!)
            val firstChild = launch(CoroutineExceptionHandler { _, _ -> }) {
                println("The first child is failing")
                throw AssertionError("The first child is cancelled")
            }
            // launch the second child
            val secondChild = launch {
                firstChild.join()
                // Cancellation of the first child is not propagated to the second child
                println("The first child is cancelled: ${firstChild.isCancelled}, but the second one is still active")
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    // But cancellation of the supervisor is propagated
                    println("The second child is cancelled because the supervisor was cancelled")
                }
            }
            // wait until the first child fails & completes
            firstChild.join()
            println("Cancelling the supervisor")
            supervisor.cancel()
            secondChild.join()
        }
    }

    /***********************
     *  Supervision scope  *
     ***********************/

    /**
     * Instead of coroutineScope, we can use supervisorScope for scoped concurrency. It propagates the cancellation
     * in one direction only and cancels all its children only if it failed itself. It also waits for all children
     * before completion just like coroutineScope does.
     */
    runBlocking {
        try {
            supervisorScope {
                launch {
                    try {
                        println("The child is sleeping")
                        delay(Long.MAX_VALUE)
                    } finally {
                        println("The child is cancelled")
                    }
                }
                // Give our child a chance to execute and print using yield
                yield()
                println("Throwing an exception from the scope")
                throw AssertionError()
            }
        } catch (e: AssertionError) {
            println("Caught an assertion error")
        }
    }

    /*****************************************
     *  Exceptions in supervised coroutines  *
     *****************************************/

    /**
     * Another crucial difference between regular and supervisor jobs is exception handling. Every child should
     * handle its exceptions by itself via the exception handling mechanism. This difference comes from the fact
     * that child's failure does not propagate to the parent. It means that coroutines launched directly inside
     * the supervisorScope do use the CoroutineExceptionHandler that is installed in their scope in the same way
     * as root coroutines do.
     */
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        supervisorScope {
            launch(handler) {
                println("The child throws an exception")
                throw AssertionError()
            }
            println("The scope is completing")
        }
        println("The scope is completed")
    }

}