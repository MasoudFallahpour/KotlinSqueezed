package ir.fallahpoor.kotlinsqueezed._4collections._7transformations

fun main() {

    /**************
     *  Mapping   *
     *************/

    /**
     * map() applies the given lambda function to each subsequent element and returns the list of the
     * lambda results. The order of results is the same as the original order of elements. To apply a
     * transformation that additionally uses the element index as an argument, use mapIndexed().
     */
    val numbers = setOf(1, 2, 3)
    println(numbers.map { it * 3 })
    println(numbers.mapIndexed { idx, value -> value * idx })

    /**
     * If the transformation produces null on certain elements, you can filter out the nulls from the
     * result collection by calling the mapNotNull() function instead of map(), or mapIndexedNotNull()
     * instead of mapIndexed().
     */
    println(numbers.mapNotNull { if (it == 2) null else it * 3 })
    println(numbers.mapIndexedNotNull { idx, value -> if (idx == 0) null else value * idx })

    /**
     * When transforming maps, you have two options: transform keys leaving values unchanged and vice
     * versa. To apply a given transformation to keys, use mapKeys(); in turn, mapValues() transforms
     * values. Both functions use the transformations that take a map entry as an argument, so you can
     * operate both its key and value.
     */
    val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key11" to 11)
    println(numbersMap.mapKeys { it.key.toUpperCase() })
    println(numbersMap.mapValues { it.value + it.key.length })

    /*************
     *  Zipping  *
     *************/

    /**
     * Zipping transformation is building pairs from elements with the same positions in both collections.
     * In the Kotlin standard library, this is done by the zip() extension function. The return type of
     * zip() is a List of Pair objects. If the collections have different sizes, the result of the zip()
     * is the smaller size.
     */
    val colors = listOf("red", "brown", "grey")
    val animals = listOf("fox", "bear", "wolf")
    println(colors zip animals)

    val twoAnimals = listOf("fox", "bear")
    println(colors.zip(twoAnimals))

    /**
     * You can also call zip() with a transformation function that takes two parameters: the receiver
     * element and the argument element. In this case, the result List contains the return values of
     * the transformation function called on pairs of the receiver and the argument elements with the
     * same positions.
     */
    println(colors.zip(animals) { color, animal -> "The ${animal.capitalize()} is $color" })

    /**
     * When you have a List of Pairs, you can do the reverse transformation – unzipping – that builds
     * two lists from these pairs:
     * - The first list contains the first elements of each Pair in the original list.
     * - The second list contains the second elements.
     */
    val numberPairs = listOf("one" to 1, "two" to 2, "three" to 3, "four" to 4)
    println(numberPairs.unzip())

    /*****************
     *  Association  *
     *****************/

    /**
     * Association transformations allow building maps from the collection elements and certain values
     * associated with them.
     *
     * The basic association function associateWith() creates a Map in which the elements of the original
     * collection are keys, and values are produced from them by the given transformation function. If
     * two elements are equal, only the last one remains in the map.
     */
    val names = listOf("Jake", "Mark", "Peter", "Ben")
    println(names.associateWith { it.length })

    /**
     * For building maps with collection elements as values, there is the function associateBy(). It takes
     * a function that returns a key based on an element's value. If two elements are equal, only the last
     * one remains in the map. associateBy() can also be called with a value transformation function.
     */
    println(names.associateBy { it.first().toUpperCase() })
    println(names.associateBy(keySelector = { it.first().toUpperCase() }, valueTransform = { it.length }))

    /**
     * Another way to build maps in which both keys and values are somehow produced from collection elements
     * is the function associate(). It takes a lambda function that returns a Pair: the key and the value of
     * the corresponding map entry.
     * Note that associate() produces short-living Pair objects which may affect the performance. Thus,
     * use associate() when the performance isn't critical or it's more preferable than other options.
     */
    data class FullName(val firstName: String, val lastName: String)

    fun parseFullName(fullName: String): FullName {
        val nameParts = fullName.split(" ")
        if (nameParts.size == 2) {
            return FullName(nameParts[0], nameParts[1])
        } else {
            throw Exception("Wrong name format")
        }
    }

    val fullNames = listOf("Alice Adams", "Brian Brown", "Clara Campbell")
    println(fullNames.associate { fullName -> parseFullName(fullName).let { it.lastName to it.firstName } })

    /****************
     *  Flattening  *
     ****************/

    /**
     * If you operate nested collections, you may find the standard library functions that provide flat
     * access to nested collection elements useful.
     *
     * The first function is flatten(). You can call it on a collection of collections, for example, a
     * List of Sets. The function returns a single List of all the elements of the nested collections.
     */
    val listOfSets = listOf(setOf(1, 2, 3), setOf(4, 5, 6), setOf(1, 2))
    println(listOfSets.flatten())

    /**
     * Another function – flatMap() provides a flexible way to process nested collections. It takes a
     * function that maps a collection element to another collection. As a result, flatMap() returns a
     * single list of its return values on all the elements. So, flatMap() behaves as a call to map()
     * followed by a call to flatten().
     */
    data class StringContainer(val values: List<String>)

    val containers = listOf(
        StringContainer(listOf("one", "two", "three")),
        StringContainer(listOf("four", "five", "six")),
        StringContainer(listOf("seven", "eight"))
    )
    println(containers.flatMap { it.values })

    /***************************
     *  String Representation  *
     ***************************/

    /**
     * If you need to retrieve the collection content in a readable format, use functions that transform
     * the collections to strings: joinToString() and joinTo().
     *
     * joinToString() builds a single String from the collection elements based on the provided arguments.
     * joinTo() does the same but appends the result to the given Appendable object.
     */
    println(numbers)
    println(numbers.joinToString())

    val listString = StringBuffer("The list of numbers: ")
    numbers.joinTo(listString)
    println(listString)

    /**
     * To build a custom string representation, you can specify its parameters in function arguments
     * separator, prefix, and postfix. The resulting string will start with the prefix and end with
     * the postfix. The separator will come after each element except the last.
     */
    println(numbers.joinToString(separator = " | ", prefix = "start: ", postfix = " :end"))

    /**
     * For bigger collections, you may want to specify the limit – a number of elements that will be
     * included into result. If the collection size exceeds the limit, all the other elements will be
     * replaced with a single value of the 'truncated' argument.
     */
    val longList = (1..100).toList()
    println(longList.joinToString(limit = 10, truncated = "<...>"))

    /**
     * Finally, to customize the representation of elements themselves, provide the transform function.
     */
    println(numbers.joinToString { "Element: $it" })

}