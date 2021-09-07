package ir.fallahpoor.kotlinsqueezed._5coroutines._7coroutineExceptionsHandling

import kotlinx.coroutines.*

fun main() {

    /**
     * So far we know that when a child coroutine throws an exception it causes the cancellation of all its
     * sibling coroutines and its parent coroutine. Sometimes we need to change this behavior so that everything
     * is not cancelled.
     *
     * A good example of such a requirement is a UI component with the job defined in its scope. If any of
     * the UI's child tasks have failed, it is not always necessary to cancel (effectively kill) the whole UI
     * component, but if the UI component is destroyed (and its job is cancelled), then it is necessary to
     * cancel all child jobs as their results are no longer needed.
     *
     * Another example is a server process that spawns multiple child jobs and needs to SUPERVISE their
     * execution, tracking their failures and only restarting the failed ones.
     *
     * The SupervisorJob can be used for these purposes. It is similar to a regular Job with the only
     * exception that cancellation is propagated only downwards.
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

}