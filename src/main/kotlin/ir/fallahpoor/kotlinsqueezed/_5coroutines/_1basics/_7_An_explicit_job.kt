package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * A launch coroutine builder returns a Job object that is a handle to the launched coroutine and
     * can be used to explicitly wait for its completion. For example, you can wait for completion of
     * the child coroutine and then print 'Done'.
     */

    runBlocking {
        val job = launch {
            delay(1000L)
            println("World!")
        }
        println("Hello")
        job.join() // wait until child coroutine completes
        println("Done")
    }

}