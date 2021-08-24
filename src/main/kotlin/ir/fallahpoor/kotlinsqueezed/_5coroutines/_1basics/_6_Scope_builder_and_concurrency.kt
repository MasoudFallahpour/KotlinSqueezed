package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * A coroutineScope builder can be used inside any suspending function to perform multiple concurrent
     * operations. Let's launch two concurrent coroutines inside a doWorld suspending function:
     */

    runBlocking {
        doWorld()
        println("Done")
    }

    /**
     * In doWorld() function, both pieces of code inside launch { ... } blocks execute concurrently,
     * with World1 printed first, after a second from start, and World2 printed next, after two
     * seconds from start. The coroutineScope in doWorld() completes only after both coroutines are
     * complete, so doWorld() returns and allows 'Done' to be printed.
     */

}

private suspend fun doWorld() = coroutineScope {
    launch {
        delay(2000L)
        println("World2")
    }
    launch {
        delay(1000L)
        println("World1")
    }
    println("Hello")
}