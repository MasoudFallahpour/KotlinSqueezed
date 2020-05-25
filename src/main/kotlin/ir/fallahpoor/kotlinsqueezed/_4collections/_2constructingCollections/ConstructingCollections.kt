package ir.fallahpoor.kotlinsqueezed._4collections._2constructingCollections

import ir.fallahpoor.kotlinsqueezed.Person
import java.util.*
import kotlin.collections.HashSet

/********************************
 *  Constructing from elements  *
 *******************************/

/**
 * The most common way to create a collection is with the standard library functions listOf<T>(),
 * setOf<T>(), mapOf<T>(), mutableListOf<T>(), mutableSetOf<T>(), and mutableMapOf<T>().
 */
val numbersSet = setOf("one", "two", "three", "four")
val emptySet = mutableSetOf<String>()
val numbersMap1 = mapOf("key1" to 1, "key2" to 2, "key3" to 3, "key4" to 1)

/**
 * Note that the 'to' function creates a short-living Pair object, so it's recommended that you use
 * it only if performance isn't critical. To avoid excessive memory usage, use alternative ways. For
 * example, you can create a mutable map and populate it using the write operations. The apply()
 * function can help to keep the initialization fluent here.
 */
val numbersMap2 = mutableMapOf<String, String>().apply { this["one"] = "1"; this["two"] = "2" }

/***********************
 *  Empty Collections  *
 ***********************/

/**
 * There are also functions for creating collections without any elements: emptyList(), emptySet(),
 * and emptyMap(). When creating empty collections, you should specify the type of elements that the
 * collection will hold.
 */
val emptyList = emptyList<String>()

/*************************************
 *  Initializer Functions for Lists  *
 *************************************/

/**
 * For lists, there is a constructor that takes the list size and the initializer function that defines
 * the element value based on its index.
 */
val doubled = List(3) { it * 2 } // doubled = [0, 2, 4]

/********************************
 *  Concrete type constructors  *
 ********************************/

/**
 * To create a concrete type collection, such as an ArrayList or LinkedList, you can use the available
 * constructors for these types. Similar constructors are available for implementations of Set and Map.
 */
val linkedList = LinkedList<String>(listOf("one", "two", "three"))
val presizedSet = HashSet<Int>(32)

/*************
 *  Copying  *
 *************/

/**
 * To create a collection with the same elements as an existing collection, you can use copying operations.
 * Collection copying operations from the standard library create shallow copy collections with references
 * to the same elements. Thus, a change made to a collection element reflects in all its copies.
 */
data class Person(var name: String, var age: Int)

val p1 = Person("Masood", 31)
val p2 = Person("Reza", 25)

fun main() {
    val list = mutableListOf(p1, p2)    // list = [(Masood, 31), (Reza, 25)]
    val listCopy = list.toMutableList() // copyList = [(Masood, 31), (Reza, 25)]

    listCopy[0].name = "Hasan"
    println(list)     // list = [(Hasan, 31), (Reza, 25)]
    println(listCopy) // copyList = [(Hasan, 31), (Reza, 25)]

    list[1].name = "Kambiz"
    println(list)     // list = [(Hasan, 31), (Kambiz, 25)]
    println(listCopy) // listCopy = [(Hasan, 31), (Kambiz, 25)]
}

/**
 * Collection copying functions, such as toList(), toMutableList(), toSet() and others, create a snapshot
 * of a collection at a specific moment. Their result is a new collection of the same elements. If you
 * ADD or REMOVE elements from the original collection, this won't affect the copies. Copies may be
 * changed independently of the source as well.
 */
val sourceList = mutableListOf(1, 2, 3)
val copyList = sourceList.toMutableList()
val a = sourceList.add(4) // sourceList = [1, 2, 3, 4]
val copyListSize = copyList.size // copyListSize = 3

/**
 * These functions can also be used for converting collections to other types, for example, build a set
 * from a list or vice versa.
 */
val sourceList2 = mutableListOf(1, 2, 3)
val copySet = sourceList2.toMutableSet()

/**
 * Collection initialization can be used for restricting mutability. For example, if you create a List
 * reference to a MutableList, the compiler will produce errors if you try to modify the collection
 * through this reference.
 */
val sourceList3 = mutableListOf(1, 2, 3)
val referenceList: List<Int> = sourceList3

//referenceList.add(4) //compilation error
val b = sourceList3.add(4)
// println(referenceList) // sourceList = [1, 2, 3, 4]

/**
 * Collections can be created in result of various operations on other collections. For example,
 * filtering a list creates a new list of elements that match the filter.
 */
val strings = listOf("one", "two", "three", "four")
val longerThan3 = strings.filter { it.length > 3 }