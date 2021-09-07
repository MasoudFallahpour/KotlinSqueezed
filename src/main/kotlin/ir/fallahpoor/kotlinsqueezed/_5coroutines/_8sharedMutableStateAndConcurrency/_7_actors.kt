package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.actor
import kotlin.system.measureTimeMillis

fun main() {

    /**
     * An actor is an entity made up of a combination of a coroutine, the state that is confined and
     * encapsulated into this coroutine, and a channel to communicate with other coroutines.
     *
     * There is an actor coroutine builder that conveniently combines actor's mailbox channel into its
     * scope to receive messages from and combines the send channel into the resulting job object, so
     * that a single reference to the actor can be carried around as its handle.
     *
     * The first step of using an actor is to define a class of messages that an actor is going to process.
     * Kotlin's sealed classes are well suited for that purpose. We define CounterMsg sealed class with
     * IncCounter message to increment a counter and GetCounter message to get its value. The latter needs
     * to send a response. A CompletableDeferred communication primitive, that represents a single value
     * that will be known (communicated) in the future, is used here for that purpose.
     *
     * Then we define a function that launches an actor using an actor coroutine builder.
     */

    fun CoroutineScope.counterActor() = actor<CounterMsg> {
        var counter = 0 // actor state
        for (msg in channel) { // iterate over incoming messages
            when (msg) {
                is IncCounter -> counter++
                is GetCounter -> msg.response.complete(counter)
            }
        }
    }

    runBlocking<Unit> {
        val counter = counterActor()
        withContext(Dispatchers.Default) {
            massiveRun {
                counter.send(IncCounter)
            }
        }
        val response = CompletableDeferred<Int>()
        counter.send(GetCounter(response))
        println("Counter = ${response.await()}")
        counter.close() // shutdown the actor
    }

}

sealed class CounterMsg
object IncCounter : CounterMsg()
class GetCounter(val response: CompletableDeferred<Int>) : CounterMsg()

private suspend fun massiveRun(action: suspend () -> Unit) {
    val n = 100  // number of coroutines to launch
    val k = 1000 // number of times an action is repeated by each coroutine
    val time = measureTimeMillis {
        coroutineScope { // scope for coroutines
            repeat(n) {
                launch {
                    repeat(k) { action() }
                }
            }
        }
    }
    println("Completed ${n * k} actions in $time ms")
}