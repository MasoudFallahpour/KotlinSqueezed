package ir.fallahpoor.kotlinsqueezed._4collections._11retrievingCollectionParts

fun main() {

    /**
     * slice() returns a list of the collection elements with given indices. The indices may be passed
     * either as a range or as a collection of integer values.
     */
    val numbers = listOf("one", "two", "three", "four", "five", "six")
    println(numbers.slice(1..3))
    println(numbers.slice(0..4 step 2))
    println(numbers.slice(setOf(3, 5, 0)))

    /**
     * To get the specified number of elements starting from the first, use the take() function. For
     * getting the last elements, use takeLast(). When called with a number larger than the collection
     * size, both functions return the whole collection.
     *
     * To take all the elements except a given number of first or last elements, call the drop() and
     * dropLast() functions respectively.
     */
    println(numbers.take(3))
    println(numbers.takeLast(3))
    println(numbers.drop(1))
    println(numbers.dropLast(5))

    /**
     * You can also use predicates to define the number of elements for taking or dropping.
     * - takeWhile() is take() with a predicate: it takes the elements up to but excluding the first one
     *   not matching the predicate.
     * - takeLastWhile() is similar to takeLast(): it takes the range of elements matching the predicate
     *   from the end of the collection.
     * - dropWhile() is the opposite to takeWhile() with the same predicate: it returns the elements
     *   from the first one not matching the predicate to the end.
     * - dropLastWhile() is the opposite to takeLastWhile() with the same predicate: it returns the
     *   elements from the beginning to the last one not matching the predicate.
     */
    println(numbers.takeWhile { !it.startsWith('f') })
    println(numbers.takeLastWhile { it != "three" })
    println(numbers.dropWhile { it.length == 3 })
    println(numbers.dropLastWhile { it.contains('i') })

    /**
     * Since Kotlin 1.2, to break a collection onto parts of a given size, use the chunked() function.
     * chunked() takes a single argument – the size of the chunk – and returns a List of Lists of the
     * given size.
     */
    val oddNumbers = (1..20 step 2).toList()
    println(oddNumbers.chunked(3))

    /**
     * You can also apply a transformation for the returned chunks right away. To do this, provide the
     * transformation as a lambda function when calling chunked(). The lambda argument is a chunk of the
     * collection. When chunked() is called with a transformation, the chunks are short-living Lists that
     * should be consumed right in that lambda.
     */
    println(oddNumbers.chunked(3) { it.sum() })  // `it` is a chunk of the original collection

    /**
     * Since Kotlin 1.2, you can retrieve all possible ranges of the collection elements of a given size.
     * The function for getting them is called windowed(): it returns a list of element ranges that you
     * would see if you were looking at the collection through a sliding window of the given size. Unlike
     * chunked(), windowed() returns element ranges (windows) starting from each collection element. All
     * the windows are returned as elements of a single List.
     */
    println(oddNumbers.windowed(3))

    /**
     * windowed() provides more flexibility with optional parameters:
     * - step defines a distance between first elements of two adjacent windows. By default the value is 1,
     *   so the result contains windows starting from all elements. If you increase the step to 2, you will
     *   receive only windows starting from odd elements: first, third, an so on.
     * - partialWindows includes windows of smaller sizes that start from the elements at the end of the
     *   collection. For example, if you request windows of three elements, you can't build them for the
     *   last two elements. Enabling partialWindows in this case includes two more lists of sizes 2 and 1.
     *
     * Finally, you can apply a transformation to the returned ranges right away. To do this, provide the
     * transformation as a lambda function when calling windowed().
     */
    println(oddNumbers.windowed(3, step = 2, partialWindows = true))
    println(oddNumbers.windowed(3) { it.sum() })

    /**
     * Since Kotlin 1.2, to build two-element windows, there is a separate function - zipWithNext(). It
     * creates pairs of adjacent elements of the receiver collection. Note that zipWithNext() doesn't break
     * the collection into pairs; it creates a Pair for each element except the last one, so its result on
     * [1, 2, 3, 4] is [[1, 2], [2, 3], [3, 4]], not [[1, 2], [3, 4]]. zipWithNext() can be called with a
     * transformation function as well; it should take two elements of the receiver collection as arguments.
     */
    val names = listOf("Jake", "Jim", "Jane", "Janet", "John")
    println(names.zipWithNext())
    println(names.zipWithNext { s1, s2 -> s1.length > s2.length })

}