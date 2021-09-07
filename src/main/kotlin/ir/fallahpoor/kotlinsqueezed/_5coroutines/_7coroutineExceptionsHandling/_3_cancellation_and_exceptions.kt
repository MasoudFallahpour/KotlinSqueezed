package ir.fallahpoor.kotlinsqueezed._5coroutines._7coroutineExceptionsHandling

import kotlinx.coroutines.*

fun main() {

    /**
     * Cancellation is closely related to exceptions. Coroutines internally use CancellationException for
     * cancellation, these exceptions are ignored by all handlers. When a coroutine is cancelled using
     * Job.cancel(), it terminates, but it does not cancel its parent.
     */

    runBlocking {
        val child = launch {
            try {
                delay(Long.MAX_VALUE)
            } finally {
                println("Child is cancelled")
            }
        }
        yield()
        println("Cancelling child")
        child.cancel()
        child.join()
        yield()
        println("Parent is not cancelled")
    }

    /**
     * If a coroutine encounters an exception other than CancellationException, it cancels its parent
     * with that exception. This behaviour cannot be overridden.
     *
     * The original exception is handled by the parent only when all its children terminate.
     */

    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) {
            launch { // the first child
                try {
                    delay(Long.MAX_VALUE)
                } finally {
                    withContext(NonCancellable) {
                        println("Children are cancelled, but exception is not handled until all children terminate")
                        delay(100)
                        println("The first child finished its non cancellable block")
                    }
                }
            }
            launch { // the second child
                delay(10)
                println("Second child throws an exception")
                throw ArithmeticException()
            }
        }
        job.join()
    }

}