package ir.fallahpoor.kotlinsqueezed._5coroutines._7coroutineExceptionsHandling

import kotlinx.coroutines.*

fun main() {

    /**
     * Instead of coroutineScope, we can use supervisorScope for scoped concurrency. It propagates the
     * cancellation in one direction only and cancels all its children only if it failed itself. It also
     * waits for all children before completion just like coroutineScope does.
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
     * Another crucial difference between regular and supervisor jobs is exception handling. Every child
     * should handle its exceptions by itself via the exception handling mechanism. This difference comes
     * from the fact that child's failure does not propagate to the parent. It means that coroutines
     * launched directly inside the supervisorScope do use the CoroutineExceptionHandler that is installed
     * in their scope in the same way as root coroutines do.
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