package ir.fallahpoor.kotlinsqueezed._5coroutines._7exceptionHandlingAndSupervision

import kotlinx.coroutines.*
import java.io.IOException

fun main() {

    /**
     * We already know that cancelled coroutine throws CancellationException in suspension points and that it is
     * ignored by the coroutines' machinery. Here we look at what happens if an exception is thrown during
     * cancellation or multiple children of the same coroutine throw an exception.
     */

    /***************************
     *  Exception propagation  *
     ***************************/

    /**
     * Coroutine builders come in two flavors: propagating exceptions automatically (launch and actor) or exposing
     * them to users (async and produce). When these builders are used to create a root coroutine, that is not a
     * child of another coroutine, the former builders treat exceptions as uncaught exceptions, similar to Java's
     * Thread.uncaughtExceptionHandler, while the latter are relying on the user to consume the final exception.
     */
    runBlocking {
        val job = GlobalScope.launch { // root coroutine with launch
            println("Throwing exception from launch")
            throw IndexOutOfBoundsException() // Will be printed to the console by Thread.defaultUncaughtExceptionHandler
        }
        job.join()
        println("Joined failed job")
        val deferred = GlobalScope.async { // root coroutine with async
            println("Throwing exception from async")
            throw ArithmeticException() // Nothing is printed, relying on user to call await
        }
        try {
            deferred.await()
            println("Unreached")
        } catch (e: ArithmeticException) {
            println("Caught ArithmeticException")
        }
    }

    /*******************************
     *  CoroutineExceptionHandler  *
     *******************************/

    /**
     * It is possible to customize the default behavior of printing uncaught exceptions to the console.
     * CoroutineExceptionHandler context element on a root coroutine can be used as generic catch block for this
     * root coroutine and all its children where custom exception handling may take place. You cannot recover from
     * the exception in the CoroutineExceptionHandler. The coroutine had already completed with the corresponding
     * exception when the handler is called. Normally, the handler is used to log the exception, show some kind of
     * error message, terminate, and/or restart the application.
     *
     * CoroutineExceptionHandler is invoked only on uncaught exceptions — exceptions that were not handled in any
     * other way. In particular, all children coroutines (coroutines created in the context of another Job) delegate
     * handling of their exceptions to their parent coroutine, which also delegates to the parent, and so on until
     * the root, so the CoroutineExceptionHandler installed in their context is never used. In addition to that,
     * async builder always catches all exceptions and represents them in the resulting Deferred object, so its
     * CoroutineExceptionHandler has no effect either.
     */
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(handler) { // root coroutine, running in GlobalScope
            throw AssertionError()
        }
        val deferred = GlobalScope.async(handler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        joinAll(job, deferred)
    }

    /*********************************
     *  Cancellation and exceptions  *
     *********************************/

    /**
     * Cancellation is closely related to exceptions. Coroutines internally use CancellationException for
     * cancellation, these exceptions are ignored by all handlers. When a coroutine is cancelled using Job.cancel,
     * it terminates, but it does not cancel its parent.
     */
    runBlocking {
        val job = launch {
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
        job.join()
    }

    /**
     * If a coroutine encounters an exception other than CancellationException, it cancels its parent with that
     * exception. This behaviour cannot be overridden and is used to provide stable coroutines hierarchies for
     * structured concurrency.
     */

    /**
     * The original exception is handled by the parent only when all its children terminate, which is demonstrated
     * by the following example.
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

    /****************************
     *  Exceptions aggregation  *
     ****************************/

    /**
     * When multiple children of a coroutine fail with an exception, the general rule is "the first exception wins",
     * so the first exception gets handled. All additional exceptions that happen after the first one are attached
     * to the first exception as suppressed ones.
     */
    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler) {
            launch {
                try {
                    delay(Long.MAX_VALUE) // it gets cancelled when another sibling fails with IOException
                } finally {
                    throw ArithmeticException() // the second exception
                }
            }
            launch {
                delay(100)
                throw IOException() // the first exception
            }
            delay(Long.MAX_VALUE)
        }
        job.join()
    }

}