package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * When a coroutine is launched in the CoroutineScope of another coroutine, it inherits its context
     * via CoroutineScope.coroutineContext and the Job of the new coroutine becomes a child of the parent
     * coroutine's job. When the parent coroutine is cancelled, all its children are recursively cancelled,
     * too.
     *
     * This parent-child relation can be explicitly overridden in one of two ways:
     * - When a different scope is explicitly specified when launching a coroutine (for example,
     *   GlobalScope.launch), then it does not inherit a Job from the parent scope.
     * - When a different Job object is passed as the context for the new coroutine, then it overrides
     *   the Job of the parent scope.
     *
     * In both cases, the launched coroutine is not tied to the scope it was launched from and operates
     * independently.
     */

    runBlocking {
        val request = launch {
            launch(Job()) {
                println("job1: I run in my own Job and execute independently!")
                delay(1000)
                println("job1: I am not affected by cancellation of the request")
            }
            launch {
                delay(100)
                println("job2: I am a child of the 'request' coroutine")
                delay(1000)
                println("job2: I will not execute this line if my parent request is cancelled")
            }
        }
        delay(500)
        request.cancel()
        delay(1000)
        println("main: Who has survived request cancellation?")
    }

}