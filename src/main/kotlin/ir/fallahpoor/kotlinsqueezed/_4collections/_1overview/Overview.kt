package ir.fallahpoor.kotlinsqueezed._4collections._1overview

/**
 * A collection is a group of a variable number of items (possibly zero) of the same type that share
 * significance to the problem being solved and are operated upon commonly.
 *
 * The following collection types are relevant for Kotlin:
 * - List: an ordered collection with access to elements by indices - integer numbers that reflect
 *   their position.
 * - Set: a collection of unique elements. It reflects the mathematical abstraction of set.
 * - Map (or dictionary): a set of key-value pairs. Keys are unique, and each of them maps to exactly
 *   one value.
 */

/**********************
 *  Collection Types  *
 **********************/

/**
 * A pair of interfaces represent each collection type in Kotlin:
 * - A read-only interface that provides operations for accessing collection elements,
 * - A mutable interface that extends the corresponding read-only interface with write operations:
 *   adding, removing, and updating its elements.
 */

/**
 * The read-only collection types are covariant. This means that, if a Rectangle class inherits from
 * Shape, you can use a List<Rectangle> anywhere a List<Shape> is required. In other words, the collection
 * types have the same subtyping relationship as the element types. Maps are covariant on the value type,
 * but not on the key type.
 *
 * In turn, mutable collections aren't covariant; otherwise, this would lead to runtime failures. If
 * MutableList<Rectangle> was a subtype of MutableList<Shape>, you could insert other Shape inheritors
 * (for example, Circle) into it, thus violating its Rectangle type argument.
 */

/****************
 *  Collection  *
 ****************/

/**
 * Collection<T> is the root of the collection hierarchy. This interface represents the common behavior
 * of a read-only collection: retrieving size, checking item membership, and so on. Collection inherits
 * from the Iterable<T> interface that defines the operations for iterating elements.
 * You can use Collection as a parameter of a function that applies to different collection types.
 */

fun printAll(strings: Collection<String>) {
    for (s in strings) print("$s ")
    println()
}

fun main() {
    val stringList = listOf("one", "two", "one")
    printAll(stringList)

    val stringSet = setOf("one", "two", "three")
    printAll(stringSet)
}

/**
 * MutableCollection is a Collection with write operations, such as add and remove.
 */

/**********
 *  List  *
 **********/

/**
 * List<T> stores elements in a specified order and provides indexed access to them. Indices start
 * from zero – the index of the first element – and go to lastIndex which is the (list.size - 1).
 */

val numbers = listOf("one", "two", "three", "four")
val size = numbers.size // size = 4
val thirdElement = numbers.get(2) // thirdElement = "three"
val fourthElement = numbers[3] // fourthElement = "four"
val indexOf = numbers.indexOf("two") // indexOf = 2

/**
 * MutableList is a List with list-specific write operations, for example, to add or remove an element
 * at a specific position.
 */
val strings = mutableListOf("A", "B", "C", "D")
//numbers.add("E") ==> strings = ["A", "B", "C", "D", "E"]
//numbers.removeAt(1) ==> strings = ["A", "C", "D", "E"]
//numbers[0] = "Z" ==> ["Z", "C", "D", "E"]

/**
 * As you see, in some aspects lists are very similar to arrays. However, there is one important
 * difference: an array's size is defined upon initialization and is never changed; in turn, a list
 * doesn't have a predefined size; a list's size can be changed as a result of write operations: adding,
 * updating, or removing elements.
 */

/**
 * In Kotlin, the default implementation of List is ArrayList which you can think of as a resizable array.
 */

/*********
 *  Set  *
 *********/

/**
 * Set<T> stores unique elements; their order is generally undefined. null elements are unique as well:
 * a Set can contain only one null. Two sets are equal if they have the same size, and for each element
 * of a set there is an equal element in the other set.
 */
val numbersSet = setOf(1, 2, 3, 4)
val a = numbersSet.contains(1) // a = true
val numbersSetBackwards = setOf(4, 3, 2, 1)
val isEqual = (numbers == numbersSetBackwards) // isEqual = true

/**
 * MutableSet is a Set with write operations from MutableCollection.
 * The default implementation of Set – LinkedHashSet – preserves the order of elements insertion. Hence,
 * the functions that rely on the order, such as first() or last(), return predictable results on such
 * sets.
 */

/**
 * Map<K, V> is not an inheritor of the Collection interface; however, it's a Kotlin collection type
 * as well. A Map stores key-value pairs (or entries); keys are unique, but different keys can be paired
 * with equal values. The Map interface provides specific functions, such as access to value by key,
 * searching keys and values, and so on.
 */
val numbersMap = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)

val mapKeys: Set<String> = numbersMap.keys // A Set containing ["key1", "key2", "key3", "key4"]
val mapValues: Collection<Int> = numbersMap.values // A Collection containing [1, 2, 3, 4]
val valueOfKey2 = numbersMap["key2"] // valueOfKey2 = 2
val isInMap = (1 in numbersMap.values) // isInMap = true

/**
 * MutableMap is a Map with map write operations, for example, you can add a new key-value pair or update
 * the value associated with the given key.
 */
val map = mutableMapOf("one" to 1, "two" to 2)
val b = map.put("three", 3)

/**
 * The default implementation of Map – LinkedHashMap – preserves the order of elements insertion when
 * iterating the map. In turn, an alternative implementation – HashMap – says nothing about the elements
 * order.
 */