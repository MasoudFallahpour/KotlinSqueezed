package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.Job
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The coroutine's Job is part of its context.
     */
    runBlocking {
        println("My job is ${coroutineContext[Job]}")
    }

}