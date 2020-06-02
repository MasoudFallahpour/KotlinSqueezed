package ir.fallahpoor.kotlinsqueezed._4collections._14aggregateOperations

fun main() {

    /**
     * Kotlin collections contain functions for commonly used aggregate operations – operations that
     * return a single value based on the collection content. Most of them are well known and work
     * the same way as they do in other languages.
     */
    val numbers = listOf(1, 2, 3, 4)

    println("count(): $numbers contains ${numbers.count()} elements")
    println("max(): Maximum value of $numbers is ${numbers.max()}")
    println("min(): Minimum value of $numbers is ${numbers.min()}")
    println("average(): Average value of $numbers is ${numbers.average()}")
    println("sum(): Sum of $numbers is ${numbers.sum()}")

    /**
     * There are also functions for retrieving the smallest and the largest elements by certain selector
     * function or custom Comparator:
     * - maxBy()/minBy() take a selector function and return the element for which it returns the largest
     *   or the smallest value.
     * - maxWith()/minWith() take a Comparator object and return the largest or smallest element according
     *   to that Comparator.
     */
    val min3Remainder = numbers.minBy { it % 3 }
    println("minBy(): $min3Remainder")

    val strings = listOf("one", "two", "three", "four")
    val longestString = strings.maxWith(compareBy { it.length })
    println("maxWith(): Longest string in $strings is $longestString")

    /**
     * Additionally, there are advanced summation functions that take a function and return the sum of
     * its return values on all elements:
     * - sumBy() applies functions that return Int values on collection elements.
     * - sumByDouble() works with functions that return Double.
     */
    println("sumBy(): " + numbers.sumBy { it * 2 })
    println("sumBuDouble(): " + numbers.sumByDouble { it.toDouble() / 2 })

    /*********************
     *  Fold and Reduce  *
     *********************/

    /**
     * For more specific cases, there are the functions reduce() and fold() that apply the provided
     * operation to the collection elements sequentially and return the accumulated result. The
     * operation takes two arguments: the previously accumulated value and the collection element.
     *
     * The difference between the two functions is that fold() takes an initial value and uses it as
     * the accumulated value on the first step, whereas the first step of reduce() uses the first and
     * the second elements as operation arguments on the first step.
     */
    val sum = numbers.reduce { sum, element -> sum + element }
    println("reduce(): $sum")
    val sumDoubled = numbers.fold(0) { sum, element -> sum + element * 2 }
    println("fold(): $sumDoubled")
    //val sumDoubledReduce = numbers.reduce { sum, element -> sum + element * 2 } //incorrect: the first element isn't doubled in the result
    //println(sumDoubledReduce)

    /**
     * To apply a function to elements in the reverse order, use functions reduceRight() and foldRight().
     * They work in a way similar to fold() and reduce() but start from the last element and then continue
     * to previous. Note that when folding or reducing right, the operation arguments change their order:
     * first goes the element, and then the accumulated value.
     */
    val sumDoubledRight = numbers.foldRight(0) { element, sum -> sum + element * 2 }
    println("foldRight(): $sumDoubledRight")

    /**
     * You can also apply operations that take element indices as parameters. For this purpose, use
     * functions reduceIndexed() and foldIndexed() passing element index as the first argument of the
     * operation.
     *
     * Finally, there are functions that apply such operations to collection elements from right to
     * left: reduceRightIndexed() and foldRightIndexed().
     */
    val sumEven = numbers.foldIndexed(0) { idx, sum, element -> if (idx % 2 == 0) sum + element else sum }
    println("foldIndexed(): $sumEven")

    val sumEvenRight = numbers.foldRightIndexed(0) { idx, element, sum -> if (idx % 2 == 0) sum + element else sum }
    println("foldRightIndexed(): $sumEvenRight")

}