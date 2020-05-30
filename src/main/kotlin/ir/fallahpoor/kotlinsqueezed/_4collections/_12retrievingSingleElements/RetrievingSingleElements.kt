package ir.fallahpoor.kotlinsqueezed._4collections._12retrievingSingleElements

fun main() {

    /**
     *  Kotlin collections provide a set of functions for retrieving single elements from collections.
     *  Functions described here apply to both lists and sets.
     */

    /****************************
     *  Retrieving by position  *
     ****************************/

    /**
     * For retrieving an element at a specific position, there is the function elementAt(). Call it with
     * the integer number as an argument, and you'll receive the collection element at the given position.
     * The first element has the position 0, and the last one is (size - 1).
     *
     * elementAt() is useful for collections that do not provide indexed access, or are not statically
     * known to provide one. In case of List, it's more idiomatic to use indexed access operator (get()
     * or []).
     */
    val numbers = linkedSetOf("one", "two", "three", "four", "five")
    println(numbers.elementAt(3))

    val numbersSortedSet = sortedSetOf("one", "two", "three", "four")
    println(numbersSortedSet.elementAt(0)) // elements are stored in the ascending order

    /**
     * There are also useful aliases for retrieving the first and the last element of the collection:
     * first() and last().
     */
    println(numbers.first())
    println(numbers.last())

    /**
     * To avoid exceptions when retrieving element with non-existing positions, use safe variations of
     * elementAt():
     * - elementAtOrNull() returns null when the specified position is out of the collection bounds.
     * - elementAtOrElse() additionally takes a lambda function that maps an Int argument to an instance
     *   of the collection element type. When called with an out-of-bounds position, the elementAtOrElse()
     *   returns the result of the lambda on the given value.
     */
    println(numbers.elementAtOrNull(5))
    println(numbers.elementAtOrElse(5) { index -> "The value for index $index is undefined" })

    /*****************************
     *  Retrieving by Condition  *
     *****************************/

    /**
     * Functions first() and last() also let you search a collection for elements matching a given
     * predicate. If no elements match the predicate, both functions throw exceptions. To avoid them,
     * use firstOrNull() and lastOrNull() instead.
     */
    println(numbers.first { it.length > 3 })
    println(numbers.last { it.startsWith("f") })
    println(numbers.firstOrNull { it.length > 6 })

    /********************
     *  Random Element  *
     ********************/
    /**
     * If you need to retrieve an arbitrary element of a collection, call the random() function. You can
     * call it without arguments or with a Random object as a source of the randomness.
     */
    println(numbers.random())

    /************************
     *  Checking existence  *
     ************************/
    /**
     * To check the presence of an element or elements in a collection, use the contains() or
     * containsAll() function respectively. You can call contains() in the operator form with the 'in'
     * keyword.
     */
    println(numbers.contains("four"))
    println("zero" in numbers)
    println(numbers.containsAll(listOf("four", "two")))
    println(numbers.containsAll(listOf("one", "zero")))

    /**
     * Additionally, you can check if the collection contains any elements by calling isEmpty() or
     * isNotEmpty().
     */
    println(numbers.isEmpty())
    println(numbers.isNotEmpty())

    val emptyList = emptyList<String>()
    println(emptyList.isEmpty())
    println(emptyList.isNotEmpty())

}