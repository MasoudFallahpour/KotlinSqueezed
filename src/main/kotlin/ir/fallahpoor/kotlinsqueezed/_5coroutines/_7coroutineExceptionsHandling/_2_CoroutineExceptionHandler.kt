package ir.fallahpoor.kotlinsqueezed._5coroutines._7coroutineExceptionsHandling

import kotlinx.coroutines.*

fun main() {

    /**
     * It is possible to customize the default behavior of printing uncaught exceptions to the console.
     * CoroutineExceptionHandler context element on a root coroutine can be used as generic catch block
     * for this root coroutine and all its children where custom exception handling may take place. You
     * cannot recover from the exception in the CoroutineExceptionHandler. The coroutine is already
     * completed with the corresponding exception when the handler is called. Normally, the handler is
     * used to log the exception, show some kind of error message, terminate, and/or restart the application.
     *
     * CoroutineExceptionHandler is invoked only on uncaught exceptions — exceptions that were not handled
     * in any other way. In particular, all children coroutines delegate handling of their exceptions to
     * their parent coroutine, which also delegates to the parent, and so on until the root, so the
     * CoroutineExceptionHandler installed in their context is never used. In addition to that, async builder
     * always catches all exceptions and represents them in the resulting Deferred object, so its
     * CoroutineExceptionHandler has no effect either.
     */

    runBlocking {
        val globalHandler = CoroutineExceptionHandler { _, exception ->
            println("GlobalExceptionHandler got $exception")
        }
        val job = GlobalScope.launch(globalHandler) { // root coroutine, running in GlobalScope
            val innerHandler = CoroutineExceptionHandler { _, exception ->
                println("LocalExceptionHandler got $exception")
            }
            launch(innerHandler) {
                throw IllegalArgumentException()
            }
        }
        val deferred = GlobalScope.async(globalHandler) { // also root, but async instead of launch
            throw ArithmeticException() // Nothing will be printed, relying on user to call deferred.await()
        }
        joinAll(job, deferred)
    }

}