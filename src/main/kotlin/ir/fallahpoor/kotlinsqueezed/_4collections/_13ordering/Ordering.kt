package ir.fallahpoor.kotlinsqueezed._4collections._13ordering

fun main() {

    /**
     * To define a natural order for a user-defined type, make the type an inheritor of Comparable.
     * This requires implementing the compareTo() function. compareTo() must take another object of
     * the same type as an argument and return an integer value showing which object is greater:
     * - Positive values show that the receiver object is greater.
     * - Negative values show that it's less than the argument.
     * - Zero shows that the objects are equal.
     */

    class Version(val major: Int, val minor: Int) : Comparable<Version> {
        override fun compareTo(other: Version): Int {
            return when {
                this.major != other.major -> {
                    this.major - other.major
                }
                this.minor != other.minor -> {
                    this.minor - other.minor
                }
                else -> 0
            }
        }
    }

    println(Version(1, 2) > Version(1, 3))
    println(Version(2, 0) > Version(1, 5))

    /**
     * Custom orders let you sort instances of any type in a way you like. Particularly, you can define
     * an order for non-comparable objects or define an order other than natural for a comparable type.
     * To define a custom order for a type, create a Comparator for it.
     */
    val lengthComparator = Comparator { str1: String, str2: String -> str1.length - str2.length }
    println(listOf("aaa", "bb", "c").sortedWith(lengthComparator))

    /**
     * A shorter way to define a Comparator is the compareBy() function from the standard library.
     * compareBy() takes a lambda function that produces a Comparable value from an instance and defines
     * the custom order as the natural order of the produced values. With compareBy(), the length
     * comparator from the example above looks like this:
     */
    println(listOf("aaa", "bb", "c").sortedWith(compareBy { it.length }))

    /**
     * The basic functions sorted() and sortedDescending() return elements of a collection sorted into
     * ascending and descending sequence according to their natural order. These functions apply to
     * collections of Comparable elements.
     */
    val numbers = listOf("one", "two", "three", "four")

    println("Sorted ascending: ${numbers.sorted()}")
    println("Sorted descending: ${numbers.sortedDescending()}")

    /**
     * For sorting in custom orders or sorting non-comparable objects, there are the functions sortedBy()
     * and sortedByDescending(). They take a selector function that maps collection elements to Comparable
     * values and sort the collection in natural order of that values.
     */
    val sortedNumbers = numbers.sortedBy { it.length }
    println("Sorted by length ascending: $sortedNumbers")
    val sortedByLast = numbers.sortedByDescending { it.last() }
    println("Sorted by the last letter descending: $sortedByLast")

    /**
     * To define a custom order for the collection sorting, you can provide your own Comparator. To do
     * this, call the sortedWith() function passing in your Comparator. With this function, sorting
     * strings by their length looks like this:
     */
    println("Sorted by length ascending: ${numbers.sortedWith(compareBy { it.length })}")

    /**
     * You can retrieve the collection in the reversed order using the reversed() function.
     */
    println(numbers.reversed())

    /**
     * Another reversing function - asReversed() - returns a reversed view of the same collection instance,
     * so it may be more lightweight and preferable than reversed() if the original list is not going to
     * change.
     */
    println(numbers.asReversed())

    /**
     * If the original list is mutable, all its changes reflect in its reversed views and vice versa.
     */
    val names = mutableListOf("Abraham", "Tanya", "Jack", "Dorothy")
    val reversedNames = names.asReversed()
    println(reversedNames)
    names.add("Jane")
    println(reversedNames)

    /**
     * Finally, there is a function that returns a new List containing the collection elements in a random
     * order - shuffled(). You can call it without arguments or with a Random object.
     */
    println(numbers.shuffled())

}