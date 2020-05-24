package ir.fallahpoor.kotlinsqueezed._1basics._1basicTypes

fun main() {

    /**
     * The type Boolean represents booleans, and has two values: true and false.
     */

    val b1: Boolean = true
    val b2 = false

    val logicalOr = b1 || b2

    println("$b1 OR $b2 => $logicalOr")

    val logicalAnd = b1 && b2

    println("$b1 AND $b2 => $logicalAnd")

    val logicalNot = !b1

    println("NOT $b1 => $logicalNot")

}