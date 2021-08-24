package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._15_flowCancellationChecks

import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.cancellable
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.runBlocking

fun main() {

    /**
     * In the case where you have a busy loop with coroutines you must explicitly check for cancellation.
     * You can add .onEach { currentCoroutineContext().ensureActive() }, but there is a ready-to-use
     * cancellable operator provided to do that.
     */

    runBlocking {
        (1..5).asFlow()
            .cancellable()
            .collect { value ->
                if (value == 3) {
                    cancel()
                }
                println(value)
            }
    }

}