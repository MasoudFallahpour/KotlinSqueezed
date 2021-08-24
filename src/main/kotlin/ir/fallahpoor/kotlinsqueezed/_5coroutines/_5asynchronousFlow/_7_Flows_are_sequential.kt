package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * Each emitted value of a flow is processed by all the intermediate operators from upstream to
     * downstream and is then delivered to the terminal operator.
     *
     * See the following example that filters the even integers and maps them to strings.
     */

    runBlocking {
        (1..5).asFlow()
            .filter {
                println("Filter $it")
                it % 2 == 0
            }
            .map {
                println("Map $it")
                "string $it"
            }.collect {
                println("Collect $it")
            }
    }

}