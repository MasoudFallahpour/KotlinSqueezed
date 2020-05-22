package ir.fallahpoor.kotlinsqueezed.basics._3controlFlow

import java.lang.Integer.parseInt

fun main() {

    val x = 1

    when (x) {
        1 -> println("x == 1")
        2 -> println("x == 2")
        else -> println("x is neither 1 nor 2")
    }

    /**
     * If 'when' is used as an expression, the else branch is mandatory, unless the compiler can
     * prove that all possible cases are covered with branch conditions (as, for example, with
     * enum class entries and sealed class subtypes).
     */

    /**
     * If many cases should be handled in the same way, the branch conditions may be combined
     * with a comma.
     */
    when (x) {
        0, 1 -> println("x == 0 or x == 1")
        else -> println("otherwise")
    }

    /**
     * We can use arbitrary expressions (not only constants) as branch conditions
     */
    val s = "1"
    when (x) {
        parseInt(s) -> println("s encodes x")
        else -> println("s does not encode x")
    }

    /**
     * We can check a value for being in or !in a range or a collection.
     */
    when (x) {
        in 1..10 -> print("x is in the range")
        !in 10..20 -> print("x is outside the range")
        else -> print("none of the above")
    }

    /**
     * Another possibility is to check that a value is or !is of a particular type.
     * Note that, due to smart casts, you can access the methods and properties of
     * the type without any extra checks.
     */
    fun hasPrefix(x: Any) = when (x) {
        is String -> x.startsWith("prefix")
        else -> false
    }

    /**
     * Since Kotlin 1.3, it is possible to capture 'when' subject in a variable.
     */
    when (val n = 1) {
        1 -> println("n is 1")
        else -> println("n is not 1. It it $n")
    }

}