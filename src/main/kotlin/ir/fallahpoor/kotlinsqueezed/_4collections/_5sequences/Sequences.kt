package ir.fallahpoor.kotlinsqueezed._4collections._5sequences

fun main() {

    /**
     * Along with collections, the Kotlin standard library contains another container type – sequences
     * (Sequence<T>). Sequences offer the same functions as Iterable but implement another approach to
     * multi-step collection processing.
     *
     * When the processing of an Iterable includes multiple steps, they are executed EAGERLY: each
     * processing step completes and returns its result – an intermediate collection. The following step
     * executes on this collection. In turn, multi-step processing of sequences is executed LAZILY when
     * possible: actual computing happens only when the result of the whole processing chain is requested.
     *
     * So, the sequences let you avoid building results of intermediate steps, therefore improving the
     * performance of the whole collection processing chain. However, the lazy nature of sequences adds
     * some overhead which may be significant when processing smaller collections or doing simpler
     * computations.
     */

    /******************
     *  Constructing  *
     ******************/

    /**
     * To create a sequence, call the sequenceOf() function listing the elements as its arguments.
     */
    val sequence = sequenceOf("four", "three", "two", "one")

    /**
     * If you already have an Iterable object (such as a List or a Set), you can create a sequence
     * from it by calling asSequence().
     */
    val numbersList = listOf("one", "two", "three", "four")
    val numbersSequence = numbersList.asSequence()

    /**
     * One more way to create a sequence is by building it with a function that calculates its elements.
     * To build a sequence based on a function, call generateSequence() with this function as an argument.
     * Optionally, you can specify the first element as an explicit value or a result of a function call.
     * The sequence in the example below is infinite.
     */
    val oddNumbers = generateSequence(1) { it + 2 } // `it` is the previous element
    println(oddNumbers.take(5).toList())
    //println(oddNumbers.count())     // error: the sequence is infinite

    /**
     * To create a finite sequence with generateSequence(), provide a function that returns null after the
     * last element you need.
     */
    val oddNumbersLessThan10 = generateSequence(1) { if (it < 10) it + 2 else null }
    println(oddNumbersLessThan10.count())

    /**
     * Finally, there is a function that lets you produce sequence elements one by one or by chunks of
     * arbitrary sizes – the sequence() function. This function takes a lambda expression containing
     * calls of yield() and yieldAll() functions. They return an element to the sequence consumer and
     * suspend the execution of sequence() until the next element is requested by the consumer. yield()
     * takes a single element as an argument; yieldAll() can take an Iterable object, an Iterator, or
     * another Sequence. A Sequence argument of yieldAll() can be infinite. However, such a call must
     * be the last: all subsequent calls will never be executed.
     */
    val evenNumbers = sequence {
        yield(0)
        yieldAll(listOf(2, 6))
        yieldAll(generateSequence(6) { it + 2 })
    }
    println(evenNumbers.take(5).toList())

    /**************************
     *   Sequence Operations  *
     **************************/

    /**
     * The sequence operations can be classified into the following groups regarding their state
     * requirements:
     * - Stateless operations require no state and process each element independently, for example, map()
     *   or filter(). Stateless operations can also require a small constant amount of state to process an
     *   element, for example, take() or drop().
     * - Stateful operations require a significant amount of state, usually proportional to the number of
     *   elements in a sequence.
     *
     * If a sequence operation returns another sequence, which is produced lazily, it's called intermediate.
     * Otherwise, the operation is terminal. Examples of terminal operations are toList() or sum(). Sequence
     * elements can be retrieved only with terminal operations.
     */

    /*********************************
     *  Sequence Processing Example  *
     *********************************/

    /**
     * Let's take a look at the difference between Iterable and Sequence with an example.
     *
     * Assume that you have a list of words. The code below filters the words longer than three characters
     * and prints the lengths of first four such words.
     */
    val words = "The quick brown fox jumps over the lazy dog".split(" ")
    val lengthsList = words.filter { println("filter: $it"); it.length > 3 }
        .map { println("length: ${it.length}"); it.length }
        .take(4)

    println("Lengths of first 4 words longer than 3 chars:")
    println(lengthsList)

    /**
     * When you run this code, you'll see that the filter() and map() functions are executed in the same
     * order as they appear in the code. First, you see filter: for all elements, then length: for the
     * elements left after filtering, and then the output of the two last lines.
     *
     * Now let's write the same with sequences.
     */
    val wordsSequence = words.asSequence()
    val lengthsSequence = wordsSequence.filter { println("filter: $it"); it.length > 3 }
        .map { println("length: ${it.length}"); it.length }
        .take(4)

    println("Lengths of first 4 words longer than 3 chars")
    println(lengthsSequence.toList())

    /**
     * The output of above code shows that the filter() and map() functions are called only when building
     * the result list. So, you first see the line of text “Lengths of..” and then the sequence processing
     * starts. Note that for elements left after filtering, the map executes before filtering the next
     * element. When the result size reaches 4, the processing stops because it's the largest possible size
     * that take(4) can return.
     */

}