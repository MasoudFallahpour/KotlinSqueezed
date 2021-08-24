package ir.fallahpoor.kotlinsqueezed._5coroutines._3composingSuspendingFunctions

import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * Optionally, async can be made lazy by setting its start parameter to CoroutineStart.LAZY. In
     * this mode it only starts the coroutine when its result is required by await, or if its Job's
     * 'start' function is invoked.
     */

    runBlocking {
        val time = measureTimeMillis {
            val one = async(start = CoroutineStart.LAZY) {
                println("Running doSomethingUsefulOne()")
                doSomethingUsefulOne()
            }
            val two = async(start = CoroutineStart.LAZY) {
                println("Running doSomethingUsefulTwo()")
                doSomethingUsefulTwo()
            }
            one.start()
            two.start()
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    /**
     * NOTE: if we just call await in println without first calling start on individual
     * coroutines, this will lead to sequential behavior, since await starts the coroutine
     * execution and waits for its finish.
     */

}

private suspend fun doSomethingUsefulOne(): Int {
    delay(1000L) // pretend we are doing something useful here
    return 13
}

private suspend fun doSomethingUsefulTwo(): Int {
    delay(1000L) // pretend we are doing something useful here, too
    return 29
}