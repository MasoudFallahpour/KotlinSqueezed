package ir.fallahpoor.kotlinsqueezed._4collections._9plusMinus

fun main() {

    /**
     * In Kotlin, plus (+) and minus (-) operators are defined for collections. They take a collection as
     * the first operand; the second operand can be either an element or another collection. The return
     * value is a new read-only collection:
     * - The result of plus contains the elements from the original collection and from the second operand.
     * - The result of minus contains the elements of the original collection except the elements from the
     *   second operand. If it's an element, minus removes its first occurrence; if it's a collection, all
     *   occurrences of its elements are removed.
     */
    val numbers = listOf("one", "two", "three", "four")

    val plusList = numbers + "five"
    val minusList = numbers - listOf("three", "four")
    println(plusList)
    println(minusList)

    /**
     * The augmented assignment operators plusAssign (+=) and minusAssign (-=) are also defined for
     * collections.
     */
    val names = mutableListOf("Jake", "Jane", "John", "Joe")
    names += "Joel"
    println(names)
    names -= listOf("Jake", "Jane")
    println(names)

}