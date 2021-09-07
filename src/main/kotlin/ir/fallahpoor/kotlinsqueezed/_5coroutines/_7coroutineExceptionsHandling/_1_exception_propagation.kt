package ir.fallahpoor.kotlinsqueezed._5coroutines._7coroutineExceptionsHandling

import kotlinx.coroutines.*

@OptIn(DelicateCoroutinesApi::class)
fun main() {

    /**
     * We already know that cancelled coroutine throws CancellationException in suspension points and that
     * it is ignored by the coroutines' machinery. Here we look at what happens if an exception is thrown
     * during cancellation or multiple children of the same coroutine throw an exception.
     */

    /**
     * Coroutine builders come in two flavors:
     * - Propagating exceptions ('launch' and 'actor'). This means the exception goes up the hierarchy until
     *   a coroutine catches the exception or no coroutine catches the exception and there will be an uncaught
     *   exception.
     * - Exposing them to users ('async' and 'produce').
     *
     * When these builders are used to create a root coroutine, that is not a child of another coroutine,
     * the former builders treat exceptions as uncaught exceptions, while the latter are relying on the user
     * to consume the final exception.
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

}