package ir.fallahpoor.kotlinsqueezed.basics._2packagesAndImports

/**
 * By default Kotlin imports the following packages:
 *
 * kotlin.*
 * kotlin.annotation.*
 * kotlin.collections.*
 * kotlin.comparisons.* (since 1.1)
 * kotlin.io.*
 * kotlin.ranges.*
 * kotlin.sequences.*
 * kotlin.text.*
 *
 * Additional default packages are imported depending on the target platform. For instance
 * when target platform is JVM the following packages are imported too:
 * java.lang.*
 * kotlin.jvm.*
 */

import kotlin.random.Random       // Single name import
import kotlin.math.*              // Whole package import
import kotlin.math.max as maximum // Renaming imported name

fun main() {
    val r = Random(5)
    val absValue = abs(-123)
    val floor = floor(2.3)
    val max = maximum(100, 200)
}