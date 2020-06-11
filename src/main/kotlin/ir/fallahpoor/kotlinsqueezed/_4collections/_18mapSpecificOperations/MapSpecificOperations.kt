package ir.fallahpoor.kotlinsqueezed._4collections._18mapSpecificOperations

fun main() {

    /********************************
     *  Retrieving Keys and Values  *
     ********************************/

    /**
     * For retrieving a value from a map, you must provide its key as an argument of the get() function.
     * The shorthand [key] syntax is also supported. If the given key is not found, it returns null.
     * There is also the function getValue() which has slightly different behavior: it throws an exception
     * if the key is not found in the map. Additionally, you have two more options to handle the key
     * absence:
     * - getOrElse() works the same way as for lists: the values for non-existent keys are returned from
     *   the given lambda function.
     * - getOrDefault() returns the specified default value if the key is not found.
     */
    val numbersMap = mapOf("one" to 1, "two" to 2, "three" to 3)
    println("Numbers map: $numbersMap")
    println("Getting value of key 'one' using get(): " + numbersMap.get("one"))
    println("Getting value of key 'one' using []: " + numbersMap["one"])
    println("Getting value of key 'four' using getOrDefault(): " + numbersMap.getOrDefault("four", 10))
    println("Getting value of key 'five' using []: " + numbersMap["five"])   // null
    //numbersMap.getValue("six")  // exception!

    /**
     * To perform operations on all keys or all values of a map, you can retrieve them from the properties
     * keys and values accordingly. keys is a set of all map keys and values is a collection of all map
     * values.
     */
    println("All keys: ${numbersMap.keys}")
    println("All values: ${numbersMap.values}")

    /***************
     *  Filtering  *
     ***************/

    /**
     * You can filter maps with the filter() function as well as other collections. When calling filter()
     * on a map, pass to it a predicate with a Pair as an argument. This enables you to use both the key
     * and the value in the filtering predicate.
     */
    val filteredMap = numbersMap.filter { (key, value) -> key.endsWith("e") && value >= 3 }
    println("Filtering with filter(): $filteredMap")

    /**
     * There are also two specific ways for filtering maps: by keys and by values. For each way, there is
     * a function: filterKeys() and filterValues(). Both return a new map of entries which match the given
     * predicate.
     */
    val filteredKeysMap = numbersMap.filterKeys { it.endsWith("e") }
    val filteredValuesMap = numbersMap.filterValues { it == 2 }

    println("Filtering keys using filterKeys: $filteredKeysMap")
    println("Filtering values using filterValues: $filteredValuesMap")

    /******************************
     *  plus and minus Operators  *
     ******************************/

    /**
     * Due to the key access to elements, plus (+) and minus (-) operators work for maps differently
     * than for other collections. plus returns a Map that contains elements of its both operands: a
     * Map on the left and a Pair or another Map on the right. When the right-hand side operand contains
     * entries with keys present in the left-hand side Map, the result map contains the entries from the
     * right side.
     */
    println("Adding [four, 4] using operator +: " + (numbersMap + Pair("four", 4)))
    println("Adding pairs [five, 5] and [one, 11] using operator +:" + (numbersMap + mapOf("five" to 5, "one" to 11)))

    /**************************
     *  Map Write Operations  *
     **************************/

    /**
     * To add a new key-value pair to a mutable map, use put(). When a new entry is put into a
     * LinkedHashMap (the default map implementation), it is added so that it comes last when
     * iterating the map.
     */
    val powersOfTwo = mutableMapOf(0 to 1, 1 to 2, 2 to 4, 3 to 8, 4 to 16)
    println("Powers of two map: $powersOfTwo")
    powersOfTwo.put(5, 32)
    println("Adding [5, 32] using put(): $powersOfTwo")

    /**
     * To add multiple entries at a time, use putAll(). Its argument can be a Map or a group of Pairs.
     */
    powersOfTwo.putAll(setOf(6 to 64, 7 to 128))
    println("Adding [6, 64] and [7, 128] using allAll(): $powersOfTwo")

    /**
     * You can also add new entries to maps using the shorthand operator form. There are two ways:
     * - plusAssign (+=) operator;
     * - the [] operator alias for put().
     */
    powersOfTwo[8] = 256 // calls numbersMap.put(8, 256)
    println("Adding [8, 256] using []: $powersOfTwo")
    powersOfTwo += mapOf(9 to 512, 10 to 1024)
    println("Adding [9, 512] and [10, 1024] using +=: $powersOfTwo")

    /**
     * To remove an entry from a mutable map, use the remove() function. When calling remove(), you
     * can pass either a key or a whole key-value-pair. If you specify both the key and value, the
     * element with this key will be removed only if its value matches the second argument.
     */
    powersOfTwo.remove(10)
    println("Removing entry with key 10 using remove(): $powersOfTwo")
    powersOfTwo.remove(9, -100) // no entry is removed
    println("Removing entry [9, -100] using remove(): $powersOfTwo")
    powersOfTwo.remove(9, 512)  // entry [9, 512] is removed
    println("Removing entry [9, 512] using remove(): $powersOfTwo")

    /**
     * The minusAssign (-=) operator is also available for mutable maps.
     */
    powersOfTwo -= 8 // entry [8, 128] is removed
    println("Removing entry with key 8 using -= operator: $powersOfTwo")

}