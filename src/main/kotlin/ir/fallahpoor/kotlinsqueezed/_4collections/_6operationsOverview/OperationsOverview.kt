package ir.fallahpoor.kotlinsqueezed._4collections._6operationsOverview

fun main() {

    /**
     * Collection operations are declared in the standard library in two ways: member functions of
     * collection interfaces and extension functions.
     *
     * Member functions define operations that are essential for a collection type. For example,
     * Collection contains the function isEmpty() for checking its emptiness; List contains get()
     * for index access to elements, and so on.
     * Other collection operations are declared as extension functions. These are filtering,
     * transformation, ordering, and other collection processing functions.
     *
     * Common operations are available for both read-only and mutable collections. Common operations
     * fall into these groups:
     * - Transformations
     * - Filtering
     * - plus and minus operators
     * - Grouping
     * - Retrieving collection parts
     * - Retrieving single elements
     * - Ordering
     * - Aggregate operations
     *
     * Above operations return their results without affecting the original collection.
     */
    val numbers1 = listOf("one", "two", "three", "four")
    numbers1.filter { it.length > 3 }  // nothing happens with `numbers`, result is lost
    println("numbers are still $numbers1")
    val longerThan3 = numbers1.filter { it.length > 3 } // result is stored in `longerThan3`
    println("numbers longer than 3 chars are $longerThan3")

    /**
     * For certain collection operations, there is an option to specify the destination object.
     * Destination is a mutable collection to which the function APPENDS its resulting items.
     * For performing operations with destinations, there are separate functions with the 'To'
     * postfix in their names, for example, filterTo() instead of filter() or associateTo()
     * instead of associate().
     */
    val numbers2 = listOf("one", "two", "three", "four")
    val filterResults = mutableListOf<String>()  //destination object
    numbers2.filterTo(filterResults) { it.length > 3 }
    numbers2.filterIndexedTo(filterResults) { index, _ -> index == 0 }
    println(filterResults) // contains results of both operations

    /**
     * For convenience, these functions return the destination collection back, so you can create
     * it right in the corresponding argument of the function call.
     */
    val result = numbers2.mapTo(HashSet()) { it.length }
    println("distinct item lengths are $result")

    /**
     * For mutable collections, there are also write operations that change the collection state.
     * For certain operations, there are pairs of functions for performing the same operation: one
     * applies the operation in-place and the other returns the result as a separate collection.
     * For example, sort() sorts a mutable collection in-place, so its state changes; sorted()
     * creates a new collection that contains the same elements in the sorted order.
     */
    val numbers3 = mutableListOf("one", "two", "three", "four")
    val sortedNumbers = numbers3.sorted()
    println(numbers3 == sortedNumbers)  // false
    numbers3.sort()
    println(numbers3 == sortedNumbers)  // true

}