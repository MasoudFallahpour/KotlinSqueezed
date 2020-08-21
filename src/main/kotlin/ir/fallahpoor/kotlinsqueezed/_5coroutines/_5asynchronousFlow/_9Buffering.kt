package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking
import kotlin.system.measureTimeMillis

fun main() {

    /***************
     *  Buffering  *
     ***************/

    /**
     * Running different parts of a flow in different coroutines can be helpful from the standpoint of
     * the overall time it takes to collect the flow, especially when long-running asynchronous operations
     * are involved. For example, consider a case when the emission by foo() flow is slow, taking 100 ms
     * to produce an element; and collector is also slow, taking 300 ms to process an element. Let's see
     * how long it takes to collect such a flow with three numbers.
     */
    fun foo(): Flow<Int> = flow {
        for (i in 1..3) {
            delay(100) // pretend we are asynchronously waiting 100 ms
            emit(i) // emit next value
        }
    }

    runBlocking {
        val time = measureTimeMillis {
            foo().collect { value ->
                delay(300) // pretend we are processing it for 300 ms
                println(value)
            }
        }
        println("Collected in $time ms")
    }

    /**
     * The whole collection takes around 1200 ms (three numbers, 400 ms for each).
     */

    /**
     * We can use a buffer operator on a flow to run emitting code of foo() concurrently with collecting
     * code, as opposed to running them sequentially.
     */
    runBlocking {
        val time = measureTimeMillis {
            foo()
                .buffer() // buffer emissions, don't wait
                .collect { value ->
                    delay(300) // pretend we are processing it for 300 ms
                    println(value)
                }
        }
        println("Collected in $time ms")
    }

    /**
     * It produces the same numbers just faster, as we have effectively created a processing pipeline,
     * having to only wait 100 ms for the first number and then spending only 300 ms to process each
     * number. This way it takes around 1000 ms to run.
     *
     * Note that the flowOn operator uses the same buffering mechanism when it has to change a
     * CoroutineDispatcher, but here we explicitly request buffering without changing the execution
     * context.
     */

    /****************
     *  Conflation  *
     ****************/

    /**
     * When a flow represents partial results of the operation or operation status updates, it may not
     * be necessary to process each value, but instead, only most recent ones. In this case, the conflate
     * operator can be used to skip intermediate values when a collector is too slow to process them.
     * Run the following code.
     */
    runBlocking {
        val time = measureTimeMillis {
            foo()
                .conflate() // conflate emissions, don't process each one
                .collect { value ->
                    delay(300) // pretend we are processing it for 300 ms
                    println(value)
                }
        }
        println("Collected in $time ms")
    }

    /**
     * You can see that while the first number was still being processed the second, and third were
     * already produced, so the second one was conflated and only the most recent (the third one) was
     * delivered to the collector.
     */

    /*********************************
     *  Processing the latest value  *
     *********************************/

    /**
     * Conflation is one way to speed up processing when both the emitter and collector are slow. It
     * does it by dropping emitted values. The other way is to cancel a slow collector and restart it
     * every time a new value is emitted. There is a family of xxxLatest operators that perform the
     * same essential logic of a xxx operator, but cancel the code in their block on a new value. Let's
     * try changing conflate to collectLatest in the previous example.
     */
    runBlocking {
        val time = measureTimeMillis {
            foo()
                .collectLatest { value -> // cancel & restart on the latest value
                    println("Collecting $value")
                    delay(300) // pretend we are processing it for 300 ms
                    println("Done $value")
                }
        }
        println("Collected in $time ms")
    }

    /**
     * Since the body of collectLatest takes 300 ms, but new values are emitted every 100 ms, we see that
     * the block is run on every value, but completes only for the last value:
     */

}