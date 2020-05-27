package ir.fallahpoor.kotlinsqueezed._4collections._3iterators

fun main() {

    /**
     * For traversing collection elements, the Kotlin standard library supports the commonly used
     * mechanism of iterators – objects that provide access to the elements sequentially without
     * exposing the underlying structure of the collection.
     *
     * Iterators can be obtained for inheritors of the Iterable<T> interface, including Set and List,
     * by calling the iterator() function. Once you obtain an iterator, it points to the first element
     * of a collection; calling the next() function returns this element and moves the iterator position
     * to the following element if it exists. Once the iterator passes through the last element, it can
     * no longer be used for retrieving elements; neither can it be reset to any previous position. To
     * iterate through the collection again, create a new iterator.
     */
    val numbers = listOf("one", "two", "three", "four")
    val numbersIterator = numbers.iterator()
    while (numbersIterator.hasNext()) {
        println(numbersIterator.next())
    }

    /**
     * Another way to go through an Iterable collection is the well-known for loop. When using for on
     * a collection, you obtain the iterator implicitly. So, the following code is equivalent to the
     * example above.
     */
    for (item in numbers) {
        println(item)
    }

    /**
     * Finally, there is a useful forEach() function that lets you automatically iterate a collection
     * and execute the given code for each element. So, the same example would look like this.
     */
    numbers.forEach {
        println(it)
    }

    /********************
     *  List Iterators  *
     ********************/

    /**
     * For lists, there is a special iterator implementation: ListIterator. It supports iterating lists
     * in both directions: forwards and backwards. Backward iteration is implemented by the functions
     * hasPrevious() and previous(). Additionally, the ListIterator provides information about the
     * element indices with the functions nextIndex() and previousIndex().
     */
    val listIterator = numbers.listIterator()
    while (listIterator.hasNext()) {
        listIterator.next()
    }
    println("Iterating backwards:")
    while (listIterator.hasPrevious()) {
        print("Index: ${listIterator.previousIndex()}")
        println(", value: ${listIterator.previous()}")
    }

    /**
     * For iterating mutable collections, there is MutableIterator that extends Iterator with the element
     * removal function remove(). So, you can remove elements from a collection while iterating it.
     */
    val mutableNumbers1 = mutableListOf("one", "two", "three", "four")
    val mutableIterator = mutableNumbers1.iterator()

    mutableIterator.next()
    mutableIterator.remove()
    println("After removal: $mutableNumbers1")

    /**
     * In addition to removing elements, the MutableListIterator can also insert and replace elements while
     * iterating the list.
     */
    val mutableNumbers2 = mutableListOf("one", "four", "four")
    val mutableListIterator = mutableNumbers2.listIterator()

    mutableListIterator.next()
    mutableListIterator.add("two")
    mutableListIterator.next()
    mutableListIterator.set("three")
    println(mutableNumbers2)

}