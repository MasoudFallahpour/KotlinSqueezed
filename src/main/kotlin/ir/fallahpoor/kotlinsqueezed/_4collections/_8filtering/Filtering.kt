package ir.fallahpoor.kotlinsqueezed._4collections._8filtering

fun main() {

    /**
     * In Kotlin, filtering conditions are defined by predicates – lambda functions that take a collection
     * element and return a boolean value: true means that the given element matches the predicate, false
     * means the opposite.
     *
     * The basic filtering function is filter(). When called with a predicate, filter() returns the
     * collection elements that match it.
     */
    val numbers = listOf("one", "two", "three", "four")
    val longerThan3 = numbers.filter { it.length > 3 }
    println(longerThan3)

    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    val filteredMap = numbersMap.filter { (key, value) -> key.endsWith("1") && value > 10 }
    println(filteredMap)

    /**
     * The predicates in filter() can only check the values of the elements. If you want to use element
     * positions in the filter, use filterIndexed(). It takes a predicate with two arguments: the index
     * and the value of an element.
     *
     * To filter collections by negative conditions, use filterNot(). It returns a list of elements for
     * which the predicate yields false.
     */
    val filteredNot = numbers.filterNot { it.length <= 3 }
    println(filteredNot)

    /**
     * There are also functions that narrow the element type by filtering elements of a given type.
     *
     * filterIsInstance() returns collection elements of a given type. Being called on a List<Any>,
     * filterIsInstance<T>() returns a List<T>, thus allowing you to call functions of the T type on
     * its items.
     */
    val generalList1 = listOf(1, "two", 3.0, "four")
    println("All String elements in upper case:")
    generalList1.filterIsInstance<String>().forEach {
        println(it.toUpperCase())
    }

    /**
     * filterNotNull() returns all non-null elements. Being called on a List<T?>, filterNotNull()
     * returns a List<T: Any>, thus allowing you to treat the elements as non-null objects.
     */
    val generalList2 = listOf(null, "one", "two", null)
    generalList2.filterNotNull().forEach {
        println(it.length)   // length is unavailable for nullable Strings
    }

    /**
     * Another filtering function – partition() – filters a collection by a predicate and keeps the
     * elements that don't match it in a separate list. So, you have a Pair of Lists as a return
     * value: the first list containing elements that match the predicate and the second one containing
     * everything else from the original collection.
     */
    val (match, rest) = numbers.partition { it.length > 3 }
    println(match)
    println(rest)

    /**
     * Finally, there are functions that simply test a predicate against collection elements:
     * - any() returns true if at least one element matches the given predicate.
     * - none() returns true if none of the elements match the given predicate.
     * - all() returns true if all elements match the given predicate. Note that all() returns true when
     * called with any valid predicate on an empty collection. Such behavior is known in logic as vacuous
     * truth.
     */
    println(numbers.any { it.endsWith("e") })
    println(numbers.none { it.endsWith("a") })
    println(numbers.all { it.endsWith("e") })

    /**
     * any() and none() can also be used without a predicate: in this case they just check the collection
     * emptiness. any() returns true if there are elements and false if there aren't; none() does the
     * opposite.
     */
    val empty = emptyList<String>()

    println(numbers.any())
    println(empty.any())

    println(numbers.none())
    println(empty.none())

}