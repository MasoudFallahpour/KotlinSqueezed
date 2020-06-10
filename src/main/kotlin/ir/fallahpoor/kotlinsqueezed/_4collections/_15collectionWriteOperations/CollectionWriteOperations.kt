package ir.fallahpoor.kotlinsqueezed._4collections._15collectionWriteOperations

fun main() {

    /*********************
     *  Adding Elements  *
     *********************/

    /**
     * To add a single element to a list or a set, use the add() function. The specified object is
     * appended to the end of the collection.
     */
    val numbers = mutableListOf(1, 2, 3, 4)
    println("List of numbers: $numbers")
    numbers.add(5)
    println("After adding '5' using add(): $numbers")

    /**
     * addAll() adds every element of the argument object to a list or a set. The argument can be an
     * Iterable, a Sequence, or an Array.
     */
    numbers.addAll(arrayOf(8, 9))
    println("After adding [8, 9] using addAll(): $numbers")
    numbers.addAll(5, setOf(6, 7))
    println("After adding [6, 7] at position 5 using addAll(): $numbers")

    /**
     * You can also add elements using the in-place version of the plus operator - plusAssign (+=) When
     * applied to a mutable collection, += appends the second operand (an element or another collection)
     * to the end of the collection.
     */
    numbers += 10
    println("After adding '10' using '+=': $numbers")
    numbers += listOf(11, 12)
    println("After adding [11, 12] using '+=': $numbers")

    /***********************
     *  Removing Elements  *
     ***********************/

    /**
     * To remove an element from a mutable collection, use the remove() function. remove() accepts the
     * element value and removes one occurrence of this value.
     */
    val names = mutableListOf("Jake", "Mike", "Johny", "Silvia", "Simon", "Brad", "Jasmin", "Jake")
    println("List of names: $names")
    names.remove("Jack") // removes the first `Jack`
    println("After removing first 'Jack' using remove(): $names")

    /**
     * For removing multiple elements at once, there are the following functions:
     * - removeAll(): removes all elements that are present in the argument collection. Alternatively,
     *   you can call it with a predicate as an argument; in this case the function removes all elements
     *   for which the predicate yields true.
     * - retainAll() is the opposite of removeAll(): it removes all elements except the ones from the
     *   argument collection. When used with a predicate, it leaves only elements that match it.
     * - clear(): removes all elements from a list and leaves it empty.
     */
    names.removeAll(setOf("Mike", "Simon"))
    println("After removing [Mike, Simon] using removeAll(): $names")

    names.retainAll { it.length >= 5 }
    println(names)

    /**
     * Another way to remove elements from a collection is with the minusAssign (-=) operator – the
     * in-place version of minus. The second argument can be a single instance of the element type or
     * another collection. With a single element on the right-hand side, -= removes the first occurrence
     * of it. In turn, if it's a collection, all occurrences of its elements are removed.
     * For example, if a list contains duplicate elements, they are removed at once.
     */
    names -= "Jasmin"
    println(names)
    names -= listOf("Johny", "Silvia")
    println(names)

}