package ir.fallahpoor.kotlinsqueezed._4collections._4rangesAndProgressions

fun main() {

    /***********
     *  Range  *
     ***********/

    /**
     * A range defines a closed interval in the mathematical sense: it is defined by its two endpoint
     * values which are both included in the range.
     *
     * Kotlin lets you easily create ranges of values using the rangeTo() function (or its operator
     * form '..') from the kotlin.ranges package. Usually, rangeTo() is complemented by in or !in
     * functions.
     */
    if (3 in 1..4) {  // equivalent of 1 <= i && i <= 4
        print("Yep")
    }

    /**
     * Integral type ranges (IntRange, LongRange, CharRange) have an extra feature: they can be iterated
     * over. These ranges are also progressions of the corresponding integral types. Such ranges are
     * generally used for iteration in the 'for' loops.
     */
    for (n in 1..4) {
        print(n)
    }

    /**
     * To iterate a number range which does not include its end element, use the 'until' function.
     */
    for (i in 1 until 10) { // i in [1, 10), 10 is excluded
        print(i)
    }

    /**
     * Ranges are not just for integral type. They could be defined for any COMPARABLE type: having an
     * order, you can define whether an arbitrary instance is in the range between two given instances.
     */
    class Version(val major: Int, val minor: Int) : Comparable<Version> {
        override fun compareTo(other: Version): Int {
            return if (major != other.major) {
                major - other.major
            } else {
                minor - other.minor
            }
        }
    }

    val versionRange = Version(1, 11)..Version(1, 30)
    println(Version(0, 9) in versionRange)
    println(Version(1, 20) in versionRange)

    /******************
     *  Progressions  *
     ******************/

    /**
     * As shown in the examples above, the ranges of integral types, such as Int, Long, and Char, can be
     * treated as arithmetic progressions of them. In Kotlin, these progressions are defined by special
     * types: IntProgression, LongProgression, and CharProgression.
     *
     * Progressions have three essential properties: the first element, the last element, and a non-zero
     * step. The first element is first, subsequent elements are the previous element plus a step.
     *
     * When you create a progression implicitly by iterating a range, this progression's first and last
     * elements are the range's endpoints, and the step is 1.
     */
    for (n in 1..10) {
        print(n)
    }

    /**
     * To define a custom progression step, use the step function on a range.
     */
    for (n in 1..8 step 2) {
        print(n)
    }

    /**
     * The last element of a progression is not always the same as the range's specified end value.
     */
    for (i in 1..9 step 3) print(i) // the last element is 7

    /**
     * To create a progression for iterating in reverse order, use downTo instead of .. when defining
     * the range for it.
     */
    for (i in 4 downTo 1) {
        print(i)
    }

    /**
     * Progressions implement Iterable<N>, where N is Int, Long, or Char respectively, so you can use
     * them in various collection functions like map, filter, and other.
     */
    println((1..10).filter { it % 2 == 0 })

}