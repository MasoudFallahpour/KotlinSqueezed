package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * By using GlobalScope.launch(), we create a top-level coroutine. If we forget to keep a
     * reference to the newly launched coroutine, there is no way to control it. What if the code
     * in the coroutine hangs (for example, we erroneously delay for too long)? Having to manually
     * keep references to all the launched coroutines is error-prone.
     *
     * Instead of launching coroutines in the GlobalScope, we can launch them in the specific scope
     * of the operation we are performing.
     *
     * Every coroutine builder, including 'runBlocking', has a scope. We can launch coroutines in
     * this scope without having to join them explicitly, because an outer coroutine (runBlocking
     * in our example) does not complete until all the coroutines launched in its scope complete.
     * Thus, we can make our example simpler.
     */
    runBlocking {
        launch { // Instead of GlobalScope.launch we use 'launch'. 'launch' starts a new coroutine in the scope of runBlocking
            delay(2000L)
            println("World!")
        }
        println("Hello,")
    }

}