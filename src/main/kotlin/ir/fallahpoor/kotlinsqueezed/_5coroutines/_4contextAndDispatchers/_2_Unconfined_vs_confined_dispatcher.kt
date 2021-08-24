package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * The Dispatchers.Unconfined coroutine dispatcher starts a coroutine in the caller thread, but
     * only until the first suspension point. After suspension, it resumes the coroutine in the thread
     * that is fully determined by the suspending function that was invoked. The unconfined dispatcher
     * is appropriate for coroutines which neither consume CPU time nor update any shared data (like UI)
     * confined to a specific thread.
     */

    runBlocking {
        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined      : I'm working in thread ${Thread.currentThread().name}")
            delay(500)
            println("Unconfined      : After delay in thread ${Thread.currentThread().name}")
        }
        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking: I'm working in thread ${Thread.currentThread().name}")
            delay(1000)
            println("main runBlocking: After delay in thread ${Thread.currentThread().name}")
        }
    }

    /**
     * In the above code, the coroutine with the context inherited from runBlocking {...} continues
     * to execute in the main thread, while the unconfined one resumes in the default executor thread
     * that the delay function is using.
     */

}