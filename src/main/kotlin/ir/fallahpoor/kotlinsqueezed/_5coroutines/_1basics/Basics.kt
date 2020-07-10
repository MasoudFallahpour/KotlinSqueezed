package ir.fallahpoor.kotlinsqueezed._5coroutines._1basics

import kotlinx.coroutines.*

fun main() {

    /*********************
     *  First coroutine  *
     *********************/

    /**
     * Consider the following piece of code
     */
    GlobalScope.launch { // launch a new coroutine in background and continue
        delay(2000L) // non-blocking delay for 1 second (default time unit is ms)
        println("World!") // print after delay
    }
    println("Hello,") // main thread continues while coroutine is delayed
    Thread.sleep(3000L) // block main thread for 3 seconds to keep JVM alive

    /**
     * In the above code we launched a coroutine! If you run the code you will see that "Hello," is
     * printed, then after 2 seconds (i.e. 2000 milliseconds) "World!" is printed.
     *
     * Here is how the above code works (simplified):
     * - Main thread reaches line 14 and wants to execute it. Line 14 launches a coroutine. Launching
     *   a coroutine is not blocking, meaning that the body of the coroutine (lines 15 and 16) will be
     *   executed on a thread other than thread Main (call the new thread Worker).
     * - Main thread reaches line 18. As of now we have two threads running: Main and Worker.
     *   Line 18 prints "Hello," and line 19 puts thread Main to sleep for 3 seconds.
     * - Meanwhile thread Worker reaches line 15 and delay(2000L) releases the thread Worker. After 2
     *   seconds thread Worker is reassigned to run line 16 which prints "World!" and coroutine is done,
     *   Hence thread Worker terminates.
     * - Thread Main is awaken after 3 seconds and it terminates too.
     */

    /**
     * Essentially, coroutines are light-weight THREADS. They are launched with 'launch' coroutine
     * builder in a context of some 'CoroutineScope'. So each and every coroutine belongs to some
     * CoroutineScope. Here we launched a new coroutine in the GlobalScope, meaning that the lifetime
     * of the coroutine is limited by the lifetime of the whole application.
     */

    /***********************************************
     *  Bridging blocking and non-blocking worlds  *
     ***********************************************/

    /**
     * The first example mixes non-blocking delay() and blocking Thread.sleep() in the same code. It is
     * easy to lose track of which one is blocking and which one is not. Let's be explicit about blocking
     * using the 'runBlocking' coroutine builder.
     */
    GlobalScope.launch {
        delay(2000L)
        println("World!")
    }
    println("Hello,")
    runBlocking { // this expression blocks the main thread
        delay(3000L) // we delay for 3 seconds to keep JVM alive
    }

    /**
     * The result of the above code is the same as fist example, but this code uses only non-blocking
     * delay. The main thread invoking 'runBlocking' blocks until the code inside runBlocking completes.
     */

    /***********************
     *  Waiting for a job  *
     **********************/

    /**
     * Delaying for a time while another coroutine is working is not a good approach. Let's explicitly
     * wait (in a non-blocking way) until the background Job that we have launched is complete.
     */
    runBlocking {
        val job = GlobalScope.launch { // launch a new coroutine and keep a reference to its Job
            delay(2000L)
            println("World!")
        }
        println("Hello,")
        job.join() // wait until child coroutine completes
    }

    /**
     * The result of the above code is still the same, but the code of the main coroutine is not tied
     * to the duration of the background job in any way, and that's much better.
     */

    /****************************
     *  Structured concurrency  *
     ****************************/

    /**
     * When we use GlobalScope.launch(), we create a top-level coroutine. Even though it is light-weight,
     * it still consumes some memory resources while it runs. If we forget to keep a reference to the
     * newly launched coroutine, it still runs. What if the code in the coroutine hangs (for example,
     * we erroneously delay for too long)? Having to manually keep references to all the launched
     * coroutines and join them is error-prone.
     *
     * Instead of launching coroutines in the GlobalScope, we can launch coroutines in the specific
     * scope of the operation we are performing.
     *
     * Every coroutine builder, including 'runBlocking', adds an instance of CoroutineScope to the
     * scope of its code block. We can launch coroutines in this scope without having to join them
     * explicitly, because an outer coroutine (runBlocking in our example) does not complete until
     * all the coroutines launched in its scope complete. Thus, we can make our example simpler.
     */
    runBlocking {
        launch { // Instead of GlobalScope.launch we use 'launch'. 'launch' launches a new coroutine in the scope of runBlocking
            delay(1000L)
            println("World!")
        }
        println("Hello,")
    }

    /*******************
     *  Scope builder  *
     *******************/

    /**
     * In addition to the coroutine scope provided by different builders, it is possible to declare your
     * own scope using the 'coroutineScope' builder. It creates a coroutine scope and does not complete
     * until all launched children complete.
     *
     * 'runBlocking' and 'coroutineScope' may look similar because they both wait for their body and all
     * its children to complete. The main difference is that the 'runBlocking' method blocks the current
     * thread for waiting, while 'coroutineScope' just suspends, releasing the underlying thread for other
     * usages. Because of that difference, 'runBlocking' is a regular function and 'coroutineScope' is a
     * suspending function.
     */
    runBlocking {
        launch {
            delay(200L)
            println("Task from runBlocking")
        }

        coroutineScope { // Creates a coroutine scope
            launch {
                delay(500L)
                println("Task from nested launch")
            }

            delay(100L)
            println("Task from coroutine scope") // This line will be printed before the nested launch
        }

        println("Coroutine scope is over") // This line is not printed until the nested launch completes
    }

    /**********************************
     *  Extract function refactoring  *
     **********************************/

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

    /*********************************
     *  Coroutines are light-weight  *
     *********************************/

    /**
     * The following code launches 100K coroutines and, after a second, each coroutine prints a dot.
     * If you try it with threads most likely your code will produce some sort of out-of-memory error.
     */
    runBlocking {
        repeat(100_000) { // launch a lot of coroutines
            launch {
                delay(1000L)
                print(".")
            }
        }
    }

    /***********************************************
     *  Global coroutines are like daemon threads  *
     ***********************************************/

    /**
     * The following code launches a long-running coroutine in GlobalScope that prints "I'm sleeping"
     * twice a second and then returns from the main function after some delay.
     *
     * You can run and see that it prints three lines and terminates. Active coroutines that were launched
     * in GlobalScope do not keep the process alive. They are like daemon threads.
     */
    runBlocking {
        GlobalScope.launch {
            repeat(1000) { i ->
                println("I'm sleeping $i ...")
                delay(500L)
            }
        }
        delay(1300L) // just quit after delay
    }

}

suspend fun doWorld() {
    delay(1000L)
    println("World!")
}