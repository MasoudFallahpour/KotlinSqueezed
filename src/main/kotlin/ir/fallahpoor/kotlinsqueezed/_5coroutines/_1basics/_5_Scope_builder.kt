package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * In addition to the coroutine scope provided by different builders, it is possible to declare your
     * own scope using the 'coroutineScope' builder. It creates a coroutine scope and does not complete
     * until all launched children complete.
     *
     * 'runBlocking' and 'coroutineScope' may look similar because they both wait for their body and all
     * their children to complete. The main difference is that the 'runBlocking' method blocks the current
     * thread for waiting, while 'coroutineScope' just suspends, releasing the underlying thread. Because
     * of that difference, 'runBlocking' is a regular function and 'coroutineScope' is a suspending function.
     *
     * You can use coroutineScope from any suspending function. For example, you can move the concurrent
     * printing of 'Hello' and 'World!' into a suspend function.
     */

    runBlocking {
        doWorld()
    }

}

private suspend fun doWorld() = coroutineScope {
    launch {
        delay(1000L)
        println("World!")
    }
    println("Hello")
}