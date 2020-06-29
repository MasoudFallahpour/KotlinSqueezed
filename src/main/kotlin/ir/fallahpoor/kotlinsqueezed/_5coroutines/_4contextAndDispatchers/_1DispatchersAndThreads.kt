package ir.fallahpoor.kotlinsqueezed._5coroutines._4contextAndDispatchers

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.newSingleThreadContext
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Coroutines always execute in some context represented by a value of the CoroutineContext type.
     * The coroutine context is a set of various elements. The main elements are the Job of the coroutine,
     * and its dispatcher.
     */

    /*****************************
     *  Dispatchers and threads  *
     *****************************/

    /**
     * The coroutine context includes a coroutine dispatcher that determines what thread or threads the
     * corresponding coroutine uses for execution. The coroutine dispatcher can confine coroutine execution
     * to a specific thread, dispatch it to a thread pool, or let it run unconfined.
     * All coroutine builders like 'launch' and 'async' accept an optional CoroutineContext parameter that
     * can be used to explicitly specify the dispatcher for the new coroutine and other context elements.
     */
    runBlocking {
        launch { // context of the parent, main runBlocking coroutine
            println("main runBlocking      : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Unconfined) { // not confined -- will work with main thread
            println("Unconfined            : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(Dispatchers.Default) { // will get dispatched to DefaultDispatcher
            println("Default               : I'm working in thread ${Thread.currentThread().name}")
        }
        launch(newSingleThreadContext("MyOwnThread")) { // will get its own new thread
            println("newSingleThreadContext: I'm working in thread ${Thread.currentThread().name}")
        }
    }

    /**
     * When launch { ... } is used without parameters, it inherits the context (and thus dispatcher)
     * from the CoroutineScope it is being launched from. In the example above, it inherits the context
     * of the main runBlocking coroutine which runs in the main thread.
     *
     * Dispatchers.Unconfined is a special dispatcher that also appears to run in the main thread, but
     * it is, in fact, a different mechanism that is explained later.
     *
     * The default dispatcher that is used when coroutines are launched in GlobalScope is represented
     * by Dispatchers.Default and uses a shared background pool of threads, so
     * launch(Dispatchers.Default) { ... } uses the same dispatcher as GlobalScope.launch { ... }.
     *
     * newSingleThreadContext creates a thread for the coroutine to run.
     */

}