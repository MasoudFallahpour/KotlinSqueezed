package ir.fallahpoor.kotlinsqueezed._4collections._16listSpecificOperations

import kotlin.math.sign

fun main() {

    /**********************************
     *  Retrieving elements by index  *
     **********************************/

    /**
     * Lists support all common operations for element retrieval: elementAt(), first(), last(), etc.
     * What is specific for lists is index access to the elements. That is done with the get() function
     * with the index passed in the argument or the shorthand [] syntax.
     * If the list size is less than the specified index, an exception is thrown. There are two other
     * functions that help you avoid such exceptions:
     * - getOrElse() lets you provide the function for calculating the default value to return if the
     *   index isn't present in the collection.
     * - getOrNull() returns null as the default value.
     */
    val numbers = mutableListOf(1, 2, 9, 6, 8, 12, 22, 1, 12)
    println("Numbers: $numbers")
    println("Getting number at position 0 using get(): " + numbers.get(0))
    println("Getting number at position 0 using []:" + numbers[0])
    //numbers.get(15)                           // exception!
    println("Getting number at position 15 using getOrNull():" + numbers.getOrNull(15)) // null
    println("Getting number at position 0 using getOrElse():" + numbers.getOrElse(15) { -1 }) // -1

    /***************************
     *  Retrieving list parts  *
     ***************************/

    /**
     * In addition to common operations for retrieving collection parts, lists provide the subList()
     * function that returns a view of the specified elements range as a list. Thus, if an element of
     * the original collection changes, it also changes in the previously created sublists and vice versa.
     */
    println("Getting elements in range [3, 6): " + numbers.subList(3, 6))

    /*******************************
     *  Finding element positions  *
     *******************************/

    /**
     * In any lists, you can find the position of an element using the functions indexOf() and
     * lastIndexOf(). They return the first and the last position of an element equal to the given
     * argument in the list. If there are no such elements, both functions return -1.
     */
    println("Finding the position of element 1 using indexOf(): " + numbers.indexOf(1))
    println("Finding the last position of element 1 using lastIndexOf(): " + numbers.lastIndexOf(1))

    /**
     * There is also a pair of functions that take a predicate and search for elements matching it:
     * - indexOfFirst() returns the index of the first element matching the predicate or -1 if there are
     *   no such elements.
     * - indexOfLast() returns the index of the last element matching the predicate or -1 if there are no
     *   such elements.
     */
    println("Finding the position of first element greater than 11 using indexOfFirst(): " + numbers.indexOfFirst { it > 11 })
    println("Finding the position of last odd element indexOfLast(): " + numbers.indexOfLast { it % 2 == 1 })

    /**
     * There is one more way to search elements in lists – binary search. It works significantly faster
     * than other built-in search functions but requires the list to be sorted in ascending order according
     * to a certain ordering: natural or another one provided in the function parameter. Otherwise, the
     * result is undefined.
     * You can also specify an index range to search in: in this case, the function searches only between
     * two provided indices.
     */
    numbers.sort()
    println("Finding the position of element 6 using binarySearch(), after sorting numbers: " + numbers.binarySearch(6)) // 3

    /**
     * When list elements aren't Comparable, you should provide a Comparator to use in the binary search.
     * The list must be sorted in ascending order according to this Comparator.
     */
    data class Product(val name: String, val price: Double)

    val productList = listOf(
        Product("WebStorm", 49.0),
        Product("AppCode", 99.0),
        Product("DotTrace", 129.0),
        Product("ReSharper", 149.0)
    )

    println("Products: $productList")
    println(
        "Searching for the position of a product with price 99.0 using binarySearch() and a Comparator: " +
                productList.binarySearch(
                    Product(
                        "AppCode",
                        99.0
                    ), compareBy { it.price })
    )

    /**
     * Binary search with comparison function lets you find elements without providing explicit search
     * values. Instead, it takes a comparison function mapping elements to Int values and searches for
     * the element where the function returns zero. The list must be sorted in the ascending order
     * according to the provided function; in other words, the return values of comparison must grow
     * from one list element to the next one.
     */
    fun priceComparison(product: Product, price: Double) = sign(product.price - price).toInt()
    println(productList.binarySearch { priceComparison(it, 149.0) })

    /***************************
     *  List Write Operations  *
     ***************************/

    /**
     * To add elements to a specific position in a list, use add() and addAll() providing the position
     * for element insertion as an additional argument.
     */
    numbers.add(1, 200)
    println("Numbers after adding element 200 at position 1 using add(): $numbers")
    numbers.addAll(2, listOf(300, 400))
    println("Numbers after adding elements [300, 400] at position 2 using addAll(): $numbers")

    /**
     * Lists also offer a function to replace an element at a given position - set() and its operator
     * form []. set() doesn't change the indexes of other elements.
     */
    numbers[0] = 100
    println("Replacing 1 with 100 at position 0 using []: $numbers")

    /**
     * To remove an element at a specific position from a list, use the removeAt() function providing
     * the position as an argument.
     */
    numbers.removeAt(1)
    println("Removing element at position 1 using removeAt(): $numbers")

    /**
     * fill() simply replaces all the collection elements with the specified value.
     */
    numbers.fill(3)
    println("Replacing all elements with 3 using fill(): $numbers")

    /**
     * For mutable lists, the standard library offers extension functions that perform the ordering
     * operations in place.
     * The in-place sorting functions have similar names to the functions that apply to read-only
     * lists, but without the ed/d suffix:
     * - sort* instead of sorted* in the names of all sorting functions: sort(), sortDescending(),
     * sortBy(), and so on.
     * - shuffle() instead of shuffled().
     * - reverse() instead of reversed().
     */
    val gods = mutableListOf("Zeus", "Poseidon", "Ares", "Apollo", "Artemis", "Hades")
    println("Greek gods: $gods")

    gods.sort()
    println("Sorting gods into ascending order: $gods")
    gods.sortDescending()
    println("Sorting gods into descending order: $gods")

    gods.sortBy { it.length }
    println("Sorting gods into ascending order by length: $gods")
    gods.sortByDescending { it.last() }
    println("Sorting gods into descending order by the last letter: $gods")

    gods.sortWith(compareBy<String> { it.length }.thenBy { it })
    println("Sorting gods by Comparator: $gods")

    gods.shuffle()
    println("Shuffling gods: $gods")

    gods.reverse()
    println("Reversing gods: $gods")

}