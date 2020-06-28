package ir.fallahpoor.kotlinsqueezed._5coroutines._3composingSuspendingFunctions

import kotlinx.coroutines.*
import kotlin.system.measureTimeMillis

fun main() {

    /***************************
     *  Sequential by default  *
     ***************************/

    /**
     * Assume that we have two suspending functions defined elsewhere that do something useful.
     * What do we do if we need them to be invoked sequentially and compute the sum of their results?
     * We use a normal sequential invocation, because the code in the coroutine, just like in the
     * regular code, is sequential by default. The following example demonstrates it by measuring the
     * total time it takes to execute both suspending functions.
     */
    suspend fun doSomethingUsefulOne(): Int {
        delay(1000L) // pretend we are doing something useful here
        return 13
    }

    suspend fun doSomethingUsefulTwo(): Int {
        delay(1000L) // pretend we are doing something useful here, too
        return 29
    }

    runBlocking {
        val time = measureTimeMillis {
            val one = doSomethingUsefulOne()
            val two = doSomethingUsefulTwo()
            println("The answer is ${one + two}")
        }
        println("Completed in $time ms")
    }

    /****************************
     *  Concurrent using async  *
     ****************************/

    /**
     * What if there are no dependencies between invocations of doSomethingUsefulOne and
     * doSomethingUsefulTwo and we want to get the answer faster, by doing both concurrently? This is
     * where 'async' comes to help.
     *
     * Conceptually, 'async' is just like 'launch'. It starts a separate coroutine which is a
     * light-weight thread that works concurrently with all the other coroutines. The difference is
     * that 'launch' returns a Job and does not carry any resulting value, while 'async' returns a
     * Deferred — a light-weight non-blocking future that represents a promise to provide a result
     * later. You can use .await() on a Deferred to get its eventual result, but Deferred is also a
     * Job, so you can cancel it if needed.
     */
    runBlocking {
        val time = measureTimeMillis {
            val one = async { doSomethingUsefulOne() }
            val two = async { doSomethingUsefulTwo() }
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    /**
     * This is twice as fast, because the two coroutines execute concurrently.
     * NOTE: concurrency with coroutines is always explicit.
     */

    /**************************
     *  Lazily started async  *
     **************************/

    /**
     * Optionally, async can be made lazy by setting its start parameter to CoroutineStart.LAZY. In
     * this mode it only starts the coroutine when its result is required by await, or if its Job's
     * 'start' function is invoked.
     */
    runBlocking {
        val time = measureTimeMillis {
            println("Defining lazy coroutines")
            val one = async(start = CoroutineStart.LAZY) {
                println("Running doSomethingUsefulOne()")
                doSomethingUsefulOne()
            }
            val two = async(start = CoroutineStart.LAZY) {
                println("Running doSomethingUsefulTwo()")
                doSomethingUsefulTwo()
            }
            one.start()
            two.start()
            println("Coroutines not started yet!")
            println("The answer is ${one.await() + two.await()}")
        }
        println("Completed in $time ms")
    }

    /**
     * The use-case for async(start = CoroutineStart.LAZY) is a replacement for the standard 'lazy'
     * function in cases when computation of the value involves suspending functions.
     */

    /***************************
     *  Async-style functions  *
     ***************************/

    /**
     * We can define async-style functions that invoke doSomethingUsefulOne and doSomethingUsefulTwo
     * asynchronously using the async coroutine builder with an explicit GlobalScope reference. We
     * name such functions with the "…Async" suffix to highlight the fact that they only start
     * asynchronous computation and one needs to use the resulting deferred value to get the result.
     */
    // The result type of somethingUsefulOneAsync is Deferred<Int>
    fun somethingUsefulOneAsync() = GlobalScope.async {
        doSomethingUsefulOne()
    }

    // The result type of somethingUsefulTwoAsync is Deferred<Int>
    fun somethingUsefulTwoAsync() = GlobalScope.async {
        doSomethingUsefulTwo()
    }

    /**
     * Note that these xxxAsync functions are not suspending functions. They can be used from anywhere.
     * The following example shows their use outside of coroutine.
     */
    val time = measureTimeMillis {
        // we can initiate async actions outside of a coroutine
        val one = somethingUsefulOneAsync()
        val two = somethingUsefulTwoAsync()
        // but waiting for a result must involve either suspending or blocking.
        // here we use `runBlocking { ... }` to block the main thread while waiting for the result
        runBlocking {
            println("The answer is ${one.await() + two.await()}")
        }
    }
    println("Completed in $time ms")

    /**
     * This programming style with async functions is provided here only for illustration, because it is
     * a popular style in other programming languages. Using this style with Kotlin coroutines is strongly
     * discouraged for the reason explained below.
     *
     * Consider what happens if between the val one = somethingUsefulOneAsync() line and one.await()
     * expression there is some logic error in the code and the program throws an exception and the
     * operation that was being performed by the program aborts. Normally, a global error-handler could
     * catch this exception, log and report the error for developers, but the program could otherwise
     * continue doing other operations. But here we have somethingUsefulOneAsync still running in the
     * background, even though the operation that initiated it was aborted. This problem does not happen
     * with structured concurrency.
     */

    /***************************************
     *  Structured concurrency with async  *
     ***************************************/

    /**
     * Let us take the 'Concurrent using async' example and extract a function that concurrently
     * performs doSomethingUsefulOne and doSomethingUsefulTwo and returns the sum of their results.
     * Because the async coroutine builder is defined as an extension on CoroutineScope, we need to
     * have it in the scope and that is what the coroutineScope function provides.
     */
    suspend fun concurrentSum(): Int = coroutineScope {
        val one = async { doSomethingUsefulOne() }
        val two = async { doSomethingUsefulTwo() }
        one.await() + two.await()
    }

    runBlocking {
        val time = measureTimeMillis {
            println("The answer is ${concurrentSum()}")
        }
        println("Completed in $time ms")
    }

    /**
     * This way, if something goes wrong inside the code of the concurrentSum function and it throws an
     * exception, all the coroutines that were launched in its scope will be cancelled.
     */

}