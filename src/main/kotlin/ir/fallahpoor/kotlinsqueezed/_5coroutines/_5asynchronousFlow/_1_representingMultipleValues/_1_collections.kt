package ir.fallahpoor.kotlinsqueezed._5coroutines._5asynchronousFlow._1_representingMultipleValues

fun main() {

    /**
     * Multiple values can be represented in Kotlin using collections. For example, we can have a function
     * foo that returns a list of three numbers and then print them all using forEach.
     */

    foo().forEach { value -> println(value) }

}

private fun foo(): List<Int> = listOf(1, 2, 3)