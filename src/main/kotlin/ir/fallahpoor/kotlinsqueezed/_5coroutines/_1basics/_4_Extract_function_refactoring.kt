package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Let's extract the block of code inside launch { ... } into a separate function. When you perform
     * "Extract function" refactoring on this code, you get a new function with the 'suspend' modifier.
     * Suspending functions can be used inside coroutines just like regular functions, but their additional
     * feature is that they can, in turn, use other suspending functions (like 'delay' in this example) to
     * suspend execution of a coroutine.
     */

    runBlocking {
        launch { doWorld() }
        println("Hello,")
    }

}

private suspend fun doWorld() {
    delay(2000L)
    println("World!")
}