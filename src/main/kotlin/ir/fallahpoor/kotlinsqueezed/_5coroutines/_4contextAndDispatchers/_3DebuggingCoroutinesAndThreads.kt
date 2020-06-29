package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking

fun main() {

    /**************************************
     *  Debugging coroutines and threads  *
     **************************************/

    /**
     * Coroutines can suspend on one thread and resume on another thread. When using coroutines, the
     * thread name alone does not give much of a context, so kotlinx.coroutines includes debugging
     * facilities to make it easier.
     *
     * Run the following code with -Dkotlinx.coroutines.debug JVM option.
     */
    fun log(msg: String) = println("[${Thread.currentThread().name}] $msg")

    runBlocking {
        val a = async {
            log("I'm computing a piece of the answer")
            6
        }
        val b = async {
            log("I'm computing another piece of the answer")
            7
        }
        log("The answer is ${a.await() * b.await()}")
    }

    /**
     * The log function prints the name of the thread in square brackets, and you can see that it is
     * the main thread with the identifier of the currently executing coroutine appended to it.
     */

}