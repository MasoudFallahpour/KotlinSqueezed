package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.*

fun main() {

    /*********************
     *  Coroutine Scope  *
     *********************/

    /**
     * Assume that our application has an object with a lifecycle, but that object is not a coroutine.
     * For example, we are writing an Android application and launch various coroutines in the context of
     * an Android activity to perform asynchronous operations to fetch and update data, do animations, etc.
     * All of these coroutines must be cancelled when the activity is destroyed to avoid memory leaks. We,
     * of course, can manipulate contexts and jobs manually to tie the lifecycles of the activity and its
     * coroutines, but kotlinx.coroutines provides an abstraction encapsulating that: CoroutineScope.
     *
     * We manage the lifecycles of our coroutines by creating an instance of CoroutineScope tied to the
     * lifecycle of our activity. A CoroutineScope instance can be created by the CoroutineScope() or
     * MainScope() factory functions. The former creates a general-purpose scope, while the latter creates
     * a scope for UI applications and uses Dispatchers.Main as the default dispatcher.
     *
     * Take a look at the following class.
     */
    class Activity {

        private val mainScope = CoroutineScope(Dispatchers.Default)

        fun destroy() {
            mainScope.cancel()
        }

        fun doSomething() {
            // launch ten coroutines for a demo, each working for a different time
            repeat(10) { i ->
                mainScope.launch {
                    delay((i + 1) * 200L) // variable delay 200ms, 400ms, ... etc
                    println("Coroutine $i is done")
                }
            }
        }

    }

    /**
     * Inside class Activity we have defined a CoroutineScope named mainScope using MainScope().
     * Inside function doSomething we launch 10 coroutines in the scope of mainScope. When method
     * destroy gets called then all coroutines inside scope mainScope are cancelled as the following
     * example demonstrates.
     */
    runBlocking {
        val activity = Activity()
        activity.doSomething()
        println("Launched coroutines")
        delay(500L)
        println("Destroying activity!")
        activity.destroy() // cancels all coroutines
        delay(1000) // visually confirm that they don't work
    }

}