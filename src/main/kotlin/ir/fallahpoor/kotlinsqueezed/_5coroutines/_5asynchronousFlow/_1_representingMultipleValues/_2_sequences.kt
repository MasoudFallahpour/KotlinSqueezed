package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._1_representingMultipleValues

fun main() {

    /**
     * If we are computing the numbers with some CPU-consuming blocking code (each computation taking
     * 100ms), then we can represent the numbers using a Sequence.
     */

    foo().forEach { value -> println(value) }

}


private fun foo(): Sequence<Int> = sequence { // sequence builder
    for (i in 1..3) {
        Thread.sleep(100) // pretend we are computing it
        yield(i) // yield next value
    }
}