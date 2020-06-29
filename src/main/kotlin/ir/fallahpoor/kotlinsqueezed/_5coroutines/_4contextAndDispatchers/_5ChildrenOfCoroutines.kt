package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /*****************************
     *  Children of a coroutine  *
     *****************************/

    /**
     * When a coroutine is launched in the CoroutineScope of another coroutine, it inherits its context
     * via CoroutineScope.coroutineContext and the Job of the new coroutine becomes a child of the parent
     * coroutine's job. When the parent coroutine is cancelled, all its children are recursively cancelled,
     * too.
     *
     * However, when GlobalScope is used to launch a coroutine, there is no parent for the job of the new
     * coroutine. It is therefore not tied to the scope it was launched from and operates independently.
     */
    runBlocking {
        // launch a coroutine to process some kind of incoming request
        val request = launch {
            // it spawns two other jobs, one with GlobalScope
            GlobalScope.launch {
                println("job1: I run in GlobalScope and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }
            // and the other inherits the parent context
            launch {
                delay(100)
                println("job2: I am a child of the request coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500)
        request.cancel() // cancel processing of the request
        delay(1000) // delay a second to see what happens
        println("main: Who has survived request cancellation?")
    }

}