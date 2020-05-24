package ir.fallahpoor.kotlinsqueezed._1basics._1basicTypes

import kotlin.math.pow

fun main() {

    val numbers = arrayOf(100, 200, 300, 400) // [100, 200, 300, 400]
    val powersOfTwo: Array<Int> = Array(5) { index ->
        val two = 2.0
        two.pow(index).toInt()
    } // [1, 2, 4, 8, 16]

    val i = numbers[0] // or numbers.get(0)

    /**
     * Arrays in Kotlin are invariant. This means that Kotlin does not let us assign an
     * Array<String> to an Array<Any>
     */

    /**
     * Kotlin also has specialized classes to represent arrays of primitive types without
     * boxing overhead.
     */
    val byteArray: ByteArray = byteArrayOf(1, 2, 3)
    val shortArray: ShortArray = shortArrayOf(1, 2, 3)
    val intArray: IntArray = intArrayOf(1, 2, 3)
    val longArray: LongArray = longArrayOf(1, 2, 3)
    val doubleArray: DoubleArray = doubleArrayOf(1.1, 2.2, 3.3)
    val floatArray: FloatArray = floatArrayOf(1F, 2F, 3F)

    /**
     * Since Kotlin 1.1 we can define unsigned versions of [ByteArray], [ShortArray], [IntArray],
     * and [LongArray] too.
     *
     * NOTE: As of now (Kotlin 1.3.72) unsigned numeric types are EXPERIMENTAL.
     */
    val uByteArray: UByteArray = ubyteArrayOf(1u, 2u, 3u)
    val uShortArray: UShortArray = ushortArrayOf(1u, 2u, 3u)
    val uIntArray: UIntArray = uintArrayOf(1u, 2u, 3u)
    val uLongArray: ULongArray = ulongArrayOf(1u, 2u, 3u)

}