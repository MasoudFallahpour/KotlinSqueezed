package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Flows represent asynchronously received sequences of values, so it is quite easy to get in a situation where
     * each value triggers a request for another sequence of values. For example, we can have the following function
     * that returns a flow of two strings 500 ms apart.
     */
    fun requestFlow(i: Int): Flow<String> = flow {
        emit("$i: First")
        delay(500) // wait 500 ms
        emit("$i: Second")
    }

    /**
     * Now if we have a flow of three integers and call requestFlow for each of them like the following, then we
     * end up with a flow of flows (Flow<Flow<String>>) that needs to be flattened into a single flow for further
     * processing.
     */
    (1..3).asFlow().map { requestFlow(it) }

    /*******************
     *  FlatMapConcat  *
     *******************/

    /**
     * Concatenating mode is implemented by flatMapConcat and flattenConcat operators. They wait for the inner flow
     * to complete before starting to collect the next one as the following example shows.
     */
    runBlocking {
        (1..3).asFlow()
            .onEach { delay(100) } // a number every 100 ms
            .flatMapConcat { requestFlow(it) }
            .collect { value ->
                println(value)
            }
    }

    /******************
     *  FlatMapMerge  *
     ******************/

    /**
     * Another flattening mode is to concurrently collect all the incoming flows and merge their values into a
     * single flow so that values are emitted as soon as possible. It is implemented by flatMapMerge and flattenMerge
     * operators.
     */
    runBlocking {
        (1..3).asFlow()
            .onEach { delay(100) } // a number every 100 ms
            .flatMapMerge { requestFlow(it) }
            .collect { value ->
                println(value)
            }
    }

    /*******************
     *  FlatMapLatest  *
     *******************/

    /**
     * In a similar way to the collectLatest operator, there is the corresponding "Latest" flattening mode where a
     * collection of the previous flow is cancelled as soon as new flow is emitted. It is implemented by the
     * flatMapLatest operator.
     */
    runBlocking<Unit> {
        (1..3).asFlow().onEach { delay(100) } // a number every 100 ms
            .flatMapLatest { requestFlow(it) }
            .collect { value ->
                println(value)
            }
    }

}