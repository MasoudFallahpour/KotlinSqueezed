package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /********************
     *  Launching Flow  *
     ********************/

    /**
     * It is easy to use flows to represent asynchronous events that are coming from some source. In this case,
     * we need an analogue of the addEventListener function that registers a piece of code with a reaction for
     * incoming events and continues further work. The onEach operator can serve this role. However, onEach is
     * an intermediate operator. We also need a terminal operator to collect the flow.
     *
     * If we use the collect terminal operator after onEach, then the code after it will wait until the flow is
     * collected:
     */
    fun events(): Flow<Int> = (1..3).asFlow().onEach { delay(100) }

    runBlocking {
        events()
            .onEach { event -> println("Event: $event") }
            .collect() // <--- Collecting the flow waits
        println("Done")
    }

    /**
     * The launchIn terminal operator comes in handy here. By replacing collect with launchIn we can launch a
     * collection of the flow in a separate coroutine, so that execution of further code immediately continues:
     */
    runBlocking {
        events()
            .onEach { event -> println("Event: $event") }
            .launchIn(this) // <--- Launching the flow in a separate coroutine
        println("Done")
    }

    /**
     * The required parameter to launchIn must specify a CoroutineScope in which the coroutine to collect the flow
     * is launched.
     */

    /**
     * This way the pair of onEach { ... }.launchIn(scope) works like the addEventListener. However, there is no
     * need for the corresponding removeEventListener function, as cancellation and structured concurrency serve
     * this purpose.
     *
     * Note that launchIn also returns a Job, which can be used to cancel the corresponding flow collection coroutine
     * only without cancelling the whole scope or to join it.
     */

}