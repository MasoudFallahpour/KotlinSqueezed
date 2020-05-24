package ir.fallahpoor.kotlinsqueezed._1basics._3controlFlow

fun main() {

    /**
     * 'for' loop iterates through anything that provides an iterator.
     */

    val numbers = IntArray(5) { (it + 1) * 10 }
    for (n in numbers) {
        print("$n ")
    }

    println()

    for (i in 1..10) {
        print("$i ")
    }

    println()

    for (i in 6 downTo 0 step 2) {
        print("$i ")
    }

    /**
     * Under the hood a 'for' loop over a range or an array is compiled to an index-based
     * loop that does not create an iterator object.
     */

}