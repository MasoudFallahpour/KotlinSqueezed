package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

fun main() {

    /*******************
     *  Flow builders  *
     *******************/

    /**
     * The flow { ... } builder is the most basic one. There are other builders for easier declaration of
     * flows:
     * - flowOf builder that defines a flow emitting a fixed set of values.
     * - Various collections and sequences can be converted to flows using .asFlow() extension functions.
     */
    runBlocking {
        (1..3).asFlow().collect { value -> println(value) }
    }

}