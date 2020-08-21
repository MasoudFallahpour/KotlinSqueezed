package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.runBlocking

fun main() {

    /*********************************
     *  Intermediate flow operators  *
     *********************************/

    /**
     * Flows can be transformed with operators, just as you would with collections and sequences.
     * Intermediate operators are applied to an upstream flow and return a downstream flow. These operators
     * are cold, just like flows are.
     *
     * The basic operators have familiar names like map and filter. The important difference to sequences is
     * that blocks of code inside these operators can call suspending functions.
     */
    suspend fun performRequest(request: Int): String {
        delay(1000) // imitate long-running asynchronous work
        return "response $request"
    }

    runBlocking {
        (1..3).asFlow()
                .map { request -> performRequest(request) }
                .collect { response -> println(response) }
    }

    /************************
     *  Transform operator  *
     ************************/

    /**
     * Among the flow transformation operators, the most general one is called transform. It can be used
     * to imitate simple transformations like map and filter, as well as implement more complex
     * transformations. Using the transform operator, we can emit arbitrary values an arbitrary number of
     * times.
     */
    runBlocking {
        (1..3).asFlow()
                .transform { request ->
                    emit("Making request $request")
                    emit(performRequest(request))
                }
                .collect { response -> println(response) }
    }

    /*****************************
     *  Size-limiting operators  *
     *****************************/

    /**
     * Size-limiting intermediate operators like take() cancel the execution of the flow when the
     * corresponding limit is reached. Cancellation in coroutines is always performed by throwing an
     * exception, so that all the resource-management functions (like try { ... } finally { ... } blocks)
     * operate normally in case of cancellation.
     */
    fun numbers(): Flow<Int> = flow {
        try {
            emit(1)
            emit(2)
            println("This line will not execute")
            emit(3)
        } finally {
            println("Finally in numbers")
        }
    }

    runBlocking {
        numbers()
                .take(2) // take only the first two
                .collect { value -> println(value) }
    }

}