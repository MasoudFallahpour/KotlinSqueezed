package ir.fallahpoor.kotlinsqueezed._5coroutines._7coroutineExceptionsHandling

import kotlinx.coroutines.*
import java.io.IOException

fun main() {

    /**
     * When multiple children of a coroutine fail with an exception, the general rule is "the first exception
     * wins", so the first exception gets handled. All additional exceptions that happen after the first one
     * are attached to the first exception as suppressed ones.
     */

    runBlocking {
        val handler = CoroutineExceptionHandler { _, exception ->
            println("CoroutineExceptionHandler got $exception with suppressed ${exception.suppressed.contentToString()}")
        }
        val job = GlobalScope.launch(handler) {
            launch {
                try {
                    delay(Long.MAX_VALUE) // it gets cancelled when another sibling fails with IOException
                } finally {
                    throw ArithmeticException() // the second exception
                }
            }
            launch {
                delay(100)
                throw IOException() // the first exception
            }
            delay(Long.MAX_VALUE)
        }
        job.join()
    }

}