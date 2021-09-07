package ir.fallahpoor.kotlinsqueezed._5coroutines._8sharedMutableStateAndConcurrency

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

@Volatile // in Kotlin `volatile` is an annotation
private var counter = 0

fun main() {

    /**
     * There is a common misconception that making a variable volatile solves the concurrency problem. Let
     * us try it.
     */

    runBlocking {
        withContext(Dispatchers.Default) {
            massiveRun {
                counter++
            }
        }
        println("Counter = $counter")
    }

    /**
     * This code works slower, but we still don't get "Counter = 100000" at the end, because volatile
     * variables guarantee linearizable (this is a technical term for "atomic") reads and writes to the
     * corresponding variable, but do not provide atomicity of larger actions (increment in our case).
     */

}

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